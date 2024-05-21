package kangnamuniv.assetmanagement.controller;

import kangnamuniv.assetmanagement.dto.ResponseDTO;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.service.AssetService;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AssetController {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final AssetService assetService;

    //로그인한 유저의 보유계좌 업데이트 후 asset 정보 생성
    @PostMapping("/asset")
    public ResponseEntity<ResponseDTO> list(@RequestHeader(value = "Authorization") String token) {

        if (!jwtUtil.isTokenValid(token)) {
            ResponseDTO errorResponse = new ResponseDTO();
            errorResponse.setError("토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        Member foundMember = memberRepository.findByLoginId(loginIdFromToken);

        if (foundMember.getConnected_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(null, null, "기관 등록을 통해 connectedId를 먼저 발급받아야 합니다."));
        }

        List<Member> memberList = new ArrayList<>();
        memberList.add(foundMember);

        long s1 = System.currentTimeMillis();
        assetService.updateAllBankAccount(memberList);
        long e1 = System.currentTimeMillis();
        log.info("updateAllBankAccount 실행시간: {}ms", e1-s1);

        long s2 = System.currentTimeMillis();
        assetService.updateAllStockAccountAndStock(memberList);
        long e2 = System.currentTimeMillis();
        log.info("updateAllStockAccountAndStock 실행시간: {}ms", e2-s2);

        long s3 = System.currentTimeMillis();
        assetService.saveAllMemberAsset(memberList);
        long e3 = System.currentTimeMillis();
        log.info("saveAllMemberAsset 실행시간: {}ms", e3-s3);

        return ResponseEntity.ok(new ResponseDTO("모든 자산에 대한 업데이트 후 자산 구성정보를 저장했습니다.", null, null));
    }
}
