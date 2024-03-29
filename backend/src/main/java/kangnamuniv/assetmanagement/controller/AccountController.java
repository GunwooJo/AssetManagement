package kangnamuniv.assetmanagement.controller;

import kangnamuniv.assetmanagement.dto.AccountRequestDTO;
import kangnamuniv.assetmanagement.service.AccountService;
import kangnamuniv.assetmanagement.service.MemberService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    //계정(기관) 등록
    @PostMapping("/account/register")
    public ResponseEntity<String> register(@RequestHeader(value = "Authorization") String token, @RequestBody List<AccountRequestDTO> accountRequestDTOS) {

        // Assuming the token is a Bearer token, we remove the "Bearer " part.
        String actualToken = token.substring(7);

        // Validate the token first
        if (actualToken == null || !jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        for (AccountRequestDTO accountRequestDTO : accountRequestDTOS) {

            ResponseEntity<String> response = processAccountRegistration(actualToken, accountRequestDTO);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return response;
            }
        }

        // If all registrations were successful, return a generic success message.
        return ResponseEntity.status(HttpStatus.CREATED).body("모든 계정 등록이 완료되었습니다.");
    }


    private ResponseEntity<String> processAccountRegistration(String actualToken, AccountRequestDTO accountRequestDTO) {
        try {
            accountService.addAccount(accountRequestDTO.getBusinessType(), accountRequestDTO.getLoginType(), accountRequestDTO.getOrganization(), accountRequestDTO.getId(), accountRequestDTO.getPassword(), accountRequestDTO.getBirthday(), accountRequestDTO.getClientType(), actualToken);

            return ResponseEntity.status(HttpStatus.CREATED).body("계정 등록이 완료되었습니다.");

        } catch (Exception e) {
            log.error("계정 등록 중 에러 발생: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/account/list")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String token) {

        // Assuming the token is a Bearer token, we remove the "Bearer " part.
        String actualToken = token.substring(7);

        // Validate the token first
        if (actualToken == null || !jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(actualToken);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        JSONArray foundAccounts = null;
        try {
            foundAccounts = accountService.findAccountsByConnectedId(foundConnectedId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(foundAccounts);
    }
}
