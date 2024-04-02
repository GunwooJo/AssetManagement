package kangnamuniv.assetmanagement.controller;

import jakarta.validation.Valid;
import kangnamuniv.assetmanagement.dto.MemberRegisterDTO;
import kangnamuniv.assetmanagement.dto.TokenResponse;
import kangnamuniv.assetmanagement.dto.MemberLoginDTO;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.service.MemberService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/member/register")
    public ResponseEntity<JSONObject> register(@Valid @RequestBody MemberRegisterDTO memberRegisterDTO) {

        Member member = MemberRegisterDTO.toMember(memberRegisterDTO);
        try {
            memberService.registerMember(member);
            JSONObject successRes = new JSONObject();
            successRes.put("message", "회원가입에 성공했어요!");
            return ResponseEntity.status(HttpStatus.CREATED).body(successRes);
        } catch (Exception e) {
            log.error(e.getMessage());
            JSONObject errorRes = new JSONObject();
            errorRes.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
    }

    @PostMapping("/member/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody MemberLoginDTO memberLoginDTO) {

        Member member = MemberLoginDTO.toMember(memberLoginDTO);
        boolean isPasswordValid = memberService.passwordValidation(member);
        TokenResponse tokenResponse = new TokenResponse();

        if(isPasswordValid) {
            String token = jwtUtil.generateToken(memberLoginDTO.getLogin_id());
            tokenResponse.setToken(token);
            return ResponseEntity.ok(tokenResponse);
        }
        else {
            tokenResponse.setError("아이디나 비밀번호를 다시 확인해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tokenResponse);
        }

    }
}
