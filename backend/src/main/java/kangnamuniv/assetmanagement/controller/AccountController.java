package kangnamuniv.assetmanagement.controller;

import jakarta.validation.Valid;
import kangnamuniv.assetmanagement.dto.AccountCheckDTO;
import kangnamuniv.assetmanagement.dto.AccountRequestDTO;
import kangnamuniv.assetmanagement.dto.StockAccountListDTO;
import kangnamuniv.assetmanagement.dto.TransactionCheckDTO;
import kangnamuniv.assetmanagement.service.AccountService;
import kangnamuniv.assetmanagement.service.MemberService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    public ResponseEntity<String> register(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody List<AccountRequestDTO> accountRequestDTOS) {

        // Assuming the token is a Bearer token, we remove the "Bearer " part.
        String actualToken = token.substring(7);

        // Validate the token first
        if (!jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        for (AccountRequestDTO accountRequestDTO : accountRequestDTOS) {

            ResponseEntity<String> response = processAccountRegistration(token, accountRequestDTO);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return response;
            }
        }

        // If all registrations were successful, return a generic success message.
        return ResponseEntity.status(HttpStatus.CREATED).body("모든 계정 등록이 완료되었습니다.");
    }


    private ResponseEntity<String> processAccountRegistration(String token, AccountRequestDTO accountRequestDTO) {
        try {
            accountService.addAccount(accountRequestDTO.getBusinessType(), accountRequestDTO.getLoginType(), accountRequestDTO.getOrganization(), accountRequestDTO.getId(), accountRequestDTO.getPassword(), accountRequestDTO.getBirthday(), accountRequestDTO.getClientType(), token);

            accountService.saveAccountToDB(accountRequestDTO, token);

            return ResponseEntity.status(HttpStatus.CREATED).body("계정 등록이 완료되었습니다.");

        } catch (Exception e) {
            log.error("계정 등록 중 에러 발생: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // connectedId로 등록된 기관(계정) 조회하기
    @GetMapping("/account/list")
    public ResponseEntity<JSONObject> list(@RequestHeader("Authorization") String token) {

        // Assuming the token is a Bearer token, we remove the "Bearer " part.
        String actualToken = token.substring(7);

        // Validate the token first
        if(!jwtUtil.isTokenValid(token)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        try {
            JSONObject foundAccounts = accountService.findAccountsByConnectedId(foundConnectedId);
            return ResponseEntity.status(HttpStatus.OK).body(foundAccounts);
        } catch (Exception e) {
            log.error(e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    // 은행 보유계좌 조회
    @PostMapping("/account/own/list")
    public ResponseEntity<JSONObject> ownList(@RequestHeader("Authorization") String token,
                                              @Valid @RequestBody AccountCheckDTO accountCheckDTO) {
        if(!jwtUtil.isTokenValid(token)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        try {
            JSONObject ownAccountList = accountService.findOwnAccountList(accountCheckDTO.getOrganization(), accountCheckDTO.getBirthday(), token);
            return ResponseEntity.status(HttpStatus.OK).body(ownAccountList);
        } catch (Exception e) {
            log.error(e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    //은행 입출금내역 조회
    @PostMapping("/account/transaction-list")
    public ResponseEntity<JSONObject> ownList(@RequestHeader("Authorization") String token,
                                              @Valid @RequestBody TransactionCheckDTO transactionCheckDTO) {
        if(!jwtUtil.isTokenValid(token)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        try {
            JSONObject transactionList = accountService.getTransactionList(transactionCheckDTO, token);
            return ResponseEntity.status(HttpStatus.OK).body(transactionList);
        } catch (Exception e) {
            log.error(e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    //증권사 전계좌 조회
    @PostMapping("/stock/account/list")
    public ResponseEntity<JSONObject> stockAccountList(@RequestHeader("Authorization") String token,
                                                       @Valid @RequestBody StockAccountListDTO stockAccountListDTO) {
        if(!jwtUtil.isTokenValid(token)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        try {
            JSONObject accountList = accountService.getStockAccountList(stockAccountListDTO, token);
            return ResponseEntity.status(HttpStatus.OK).body(accountList);
        } catch (Exception e) {
            log.error(e.getMessage());
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
