package kangnamuniv.assetmanagement.controller;

import kangnamuniv.assetmanagement.service.MemberService;
import kangnamuniv.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

        String connectedId = memberService.generateConnectedId(businessType, loginType, organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(connectedId);
    }
}
