package kangnamuniv.assetmanagement.controller;

import kangnamuniv.assetmanagement.dto.MemberRegisterDTO;
import kangnamuniv.assetmanagement.dto.TokenResponse;
import kangnamuniv.assetmanagement.dto.MemberLoginDTO;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.service.MemberService;
import kangnamuniv.assetmanagement.dto.MemberRequestDTO;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/connected-id/generate")
    public ResponseEntity<String> generateConnectedId(@RequestBody MemberRequestDTO memberRequestDTO) {

        String businessType = memberRequestDTO.getBusinessType();
        String loginType = memberRequestDTO.getLoginType();
        String organization = memberRequestDTO.getOrganization();
        String id = memberRequestDTO.getId();
        String password = memberRequestDTO.getPassword();
        String birthday = memberRequestDTO.getBirthday();

        String connectedId = memberService.generateConnectedId(businessType, loginType, organization, id, password, birthday);
        return ResponseEntity.status(HttpStatus.CREATED).body(connectedId);
    }

    @PostMapping("/member/register")
    public ResponseEntity<String> register(@RequestBody MemberRegisterDTO memberRegisterDTO) {

        Member member = MemberRegisterDTO.toMember(memberRegisterDTO);
        try {
            memberService.registerMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했어요!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디에요. 다른 아이디를 입력해주세요!");
        }
    }

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDTO memberLoginDTO) {

        Member member = MemberLoginDTO.toMember(memberLoginDTO);
        boolean isPasswordValid = memberService.passwordValidation(member);

        if(isPasswordValid) {
            String token = JwtUtil.generateToken(memberLoginDTO.getLogin_id());
            TokenResponse tokenResponse = new TokenResponse(token);
            return ResponseEntity.ok(tokenResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디나 비밀번호를 다시 확인해주세요.");
        }

    }
}
