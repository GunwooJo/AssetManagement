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

    public void saveConnectedId(String login_id, String connected_id) {
        memberRepository.saveConnectedId(login_id, connected_id);
    }
}
