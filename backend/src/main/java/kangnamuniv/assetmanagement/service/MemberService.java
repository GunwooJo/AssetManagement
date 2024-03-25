package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.util.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //계정을 등록하여 connectedId 발급.
    public String addAccount(String businessType, String loginType, String organization, String id, String password, String birthday) {
        String urlPath = "https://development.codef.io/v1/account/create";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	"P");
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);
        accountMap.put("birthday", birthday);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);

        // CODEF API 호출
        String result = null;

        try {
            result = ApiRequest.request(urlPath, bodyMap);
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }

    //계정 최초 등록 후 발급된 connectedId로 기관을 추가로 등록하고 싶을 때 사용.
    public String addAccount(String businessType, String loginType, String organization, String id, String password, String birthday, String connectedId) {
        String urlPath = "https://development.codef.io/v1/account/create";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	"P");
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);
        accountMap.put("birthday", birthday);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);
        bodyMap.put(connectedId, connectedId);

        // CODEF API 호출
        String result = null;

        try {
            result = ApiRequest.request(urlPath, bodyMap);
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }

    public void registerMember(Member member) throws Exception {

        String loginId = member.getLogin_id();
        List<Member> foundMembers = memberRepository.findByLoginId(loginId);

        if(!foundMembers.isEmpty()) {
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        else {
            String hashedPassword = PasswordUtil.hashPassword(member.getPassword());
            member.setPassword(hashedPassword);
            memberRepository.save(member);
        }
    }

    public boolean passwordValidation(Member member) {
        List<Member> foundMembers = memberRepository.findByLoginId(member.getLogin_id());
        Member foundMember = foundMembers.get(0);
        boolean matches = PasswordUtil.matches(member.getPassword(), foundMember.getPassword());
        if(matches) return true;
        else return false;
    }
}
