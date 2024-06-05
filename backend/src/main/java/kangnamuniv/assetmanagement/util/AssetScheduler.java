package kangnamuniv.assetmanagement.util;

import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetScheduler {

    private final MemberRepository memberRepository;
    private final AssetService assetService;

    @Scheduled(cron = "0 0 4 L * ?")    //초 분 시 일 월 요일. 매월말 오전 4시.
    public void updateAndSaveAssetOfAllMember() {

        final int MAX_ATTEMPTS = 2;
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < MAX_ATTEMPTS) {
            try {
                List<Member> membersHaveConnectedId = memberRepository.findAllMemberHaveConnectedId();
                assetService.updateAllBankAccount(membersHaveConnectedId);
                assetService.updateAllStockAccountAndStock(membersHaveConnectedId);
                assetService.saveAllMemberAsset(membersHaveConnectedId);
                success = true;
            } catch (Exception e) {
                attempts++;
                log.warn("작업 실패, 재시도 중... 시도 횟수: {}", attempts);

                // 실패 시 잠시 기다린 후 재시도
                try {
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
}
