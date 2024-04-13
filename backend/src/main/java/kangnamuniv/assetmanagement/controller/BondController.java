package kangnamuniv.assetmanagement.controller;

import jakarta.validation.Valid;
import kangnamuniv.assetmanagement.dto.BondCreateDTO;
import kangnamuniv.assetmanagement.dto.ResponseDTO;
import kangnamuniv.assetmanagement.service.BondService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BondController {

    private final JwtUtil jwtUtil;
    private final BondService bondService;

    @PostMapping("/bond/create")
    public ResponseEntity<ResponseDTO> create(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid BondCreateDTO bondCreateDTO) {

        if(!jwtUtil.isTokenValid(token)) {
            ResponseDTO errorResponse = new ResponseDTO();
            errorResponse.setError("토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        String bondName = bondCreateDTO.getBondName();
        String accountNumber = bondCreateDTO.getAccountNumber();
        String valuationAmt = bondCreateDTO.getValuationAmt();
        String accountCurrency = bondCreateDTO.getAccountCurrency();

        try {
            bondService.createAndSaveBond(bondName, accountNumber, valuationAmt, accountCurrency);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("채권 생성 후 저장 성공", null, null));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(null, null, "서버에러: " + e));
        }
    }
}
