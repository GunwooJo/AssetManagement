package kangnamuniv.assetmanagement.util;

import kangnamuniv.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetDataScheduler {

    private final AssetService assetService;

    @Scheduled(cron = "0 0 4 L * ?")
    public void saveAssetData() {
        System.out.println("Saving asset data...");

    }
}
