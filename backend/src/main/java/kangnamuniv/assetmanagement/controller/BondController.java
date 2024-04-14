package kangnamuniv.assetmanagement.controller;

import jakarta.validation.Valid;
import kangnamuniv.assetmanagement.dto.BondCreateDTO;
import kangnamuniv.assetmanagement.dto.BondListResponseDTO;
import kangnamuniv.assetmanagement.dto.BondUpdateDTO;
import kangnamuniv.assetmanagement.dto.ResponseDTO;
import kangnamuniv.assetmanagement.entity.Bond;
import kangnamuniv.assetmanagement.service.BondService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        String accountCurrency = bondCreateDTO.getAccountCurrency();

        if(!Objects.equals(accountCurrency, "USD") && !Objects.equals(accountCurrency, "KRW") && !Objects.equals(accountCurrency, "EUR") && !Objects.equals(accountCurrency, "JPY")) {

            log.warn("통화코드는 KRW, USD, EUR, JPY중 하나여야 합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(null, null, "통화코드는 KRW, USD, EUR, JPY중 하나여야 합니다."));
        }

        String bondName = bondCreateDTO.getBondName();
        String accountNumber = bondCreateDTO.getAccountNumber();
        String valuationAmt = bondCreateDTO.getValuationAmt();

        try {
            bondService.createAndSaveBond(bondName, accountNumber, valuationAmt, accountCurrency);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("채권 생성 후 저장 성공", null, null));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PutMapping("/bond/update")
    public ResponseEntity<ResponseDTO> update(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody @Valid BondUpdateDTO bondUpdateDTO) {

        if(!jwtUtil.isTokenValid(token)) {
            ResponseDTO errorResponse = new ResponseDTO();
            errorResponse.setError("토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        String accountCurrency = bondUpdateDTO.getAccountCurrency();

        if(!Objects.equals(accountCurrency, "USD") && !Objects.equals(accountCurrency, "KRW") && !Objects.equals(accountCurrency, "EUR") && !Objects.equals(accountCurrency, "JPY")) {

            log.warn("통화코드는 KRW, USD, EUR, JPY중 하나여야 합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(null, null, "통화코드는 KRW, USD, EUR, JPY중 하나여야 합니다."));
        }

        Long id = bondUpdateDTO.getId();
        String bondName = bondUpdateDTO.getBondName();
        String accountNumber = bondUpdateDTO.getAccountNumber();
        String valuationAmt = bondUpdateDTO.getValuationAmt();

        try {
            bondService.updateBond(id, bondName, accountNumber, valuationAmt, accountCurrency);
            return ResponseEntity.ok(new ResponseDTO("수정 성공", null, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @GetMapping("/bond/list")
    public ResponseEntity<ResponseDTO> list(@RequestHeader(value = "Authorization") String token) {

        if(!jwtUtil.isTokenValid(token)) {
            ResponseDTO errorResponse = new ResponseDTO();
            errorResponse.setError("토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);

        List<Bond> foundBondList = bondService.findBondListByLoginId(loginIdFromToken);
        if(foundBondList.isEmpty()) {
            return ResponseEntity.ok(new ResponseDTO("보유한 채권이 없습니다.", null, null));
        }

        List<BondListResponseDTO> bondListDTO = foundBondList.stream()
                .map(bond -> BondListResponseDTO.builder()
                        .accountCurrency(bond.getAccountCurrency())
                        .bondName(bond.getBondName())
                        .valuationAmt(bond.getValuationAmt())
                        .id(bond.getId()).build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDTO("조회 성공", bondListDTO, null));
    }
}
