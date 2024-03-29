package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.util.ApiRequest;
import kangnamuniv.assetmanagement.util.CommonConstant;
import kangnamuniv.assetmanagement.util.JwtUtil;
import kangnamuniv.assetmanagement.util.RSAUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    //계정을 등록하여 connectedId 발급.
    //토큰으로 사용자 정보를 얻고 connectedId가 저장돼있으면 계정추가요청, 저장안돼있으면 계정등록요청 날리도록 수정하자.
    public void addAccount(String businessType, String loginType, String organization, String id, String password, String birthday, String clientType, String actualToken) throws Exception{

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(actualToken);
        String urlPath = "https://development.codef.io/v1/account/create";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	clientType);
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);
        accountMap.put("birthday", birthday);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);
        //유저가 connectedId를 기존에 가지고 있었으면 해당 connectedId로 계정 추가.
        if(memberService.isConnectedIdExist(loginIdFromToken)) {
            urlPath = "https://development.codef.io/v1/account/add";
            System.out.println("connectedId 발견!!");
            String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);
            System.out.println("foundConnectedId = " + foundConnectedId);
            bodyMap.put("connectedId", foundConnectedId);
        }

        // CODEF API 호출
        String connectedId = null;
        JSONParser parser = new JSONParser();

        try {
            String response = ApiRequest.request(urlPath, bodyMap);
            JSONObject jsonResponse = (JSONObject) parser.parse(response);
            System.out.println("응답(json) = " + jsonResponse);
            //추후 수정 필요: 에러메시지의 유무에 따라 동작하도록.
            if(jsonResponse.containsKey("data")) {

                JSONObject data = (JSONObject)jsonResponse.get("data");
                connectedId = data.get("connectedId").toString();
                //유저가 connectedId를 갖고있지 않다면 발급된 connectedId 저장
                if(!memberService.isConnectedIdExist(loginIdFromToken)) {
                    memberService.saveConnectedId(loginIdFromToken, connectedId);
                }

            } else {
                throw new Exception("connectedId 속성을 찾을 수 없음.");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    //특정 connectedId로 등록한 계정(기관)들 확인
    public JSONArray findAccountsByConnectedId(String connectedId) throws Exception{
        String urlPath = "https://development.codef.io/v1/account/list";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        bodyMap.put("connectedId", connectedId);

        JSONParser parser = new JSONParser();

        try {
            String response = ApiRequest.request(urlPath, bodyMap);
            JSONObject jsonResponse = (JSONObject) parser.parse(response);
            JSONObject resResult = (JSONObject) jsonResponse.get("result");
            String resMessage = resResult.get("message").toString();

            //다른 resMessage는 무엇이 나올 수 있는지 알아봐야함.
            if(resMessage.equals("성공")) {

                JSONObject resData = (JSONObject) jsonResponse.get("data");
                JSONArray accountList = (JSONArray) resData.get("accountList");

                return accountList;
            } else {
                throw new Exception("connectedId로 등록된 계정들 조회 실패");
            }

        } catch (IOException | InterruptedException | ParseException e) {
            log.error("CODEF 서버로 요청 실패 " + e);
            throw new Exception("Request to CODEF server failed: " + e.getMessage(), e);
        }
    }
}
