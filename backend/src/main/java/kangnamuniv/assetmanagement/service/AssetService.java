package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.entity.Account;
import kangnamuniv.assetmanagement.entity.BankAccount;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.entity.StockAccount;
import kangnamuniv.assetmanagement.repository.AccountRepository;
import kangnamuniv.assetmanagement.repository.AssetRepository;
import kangnamuniv.assetmanagement.repository.BondRepository;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

    private final MemberRepository memberRepository;
    private final AccountService accountService;
    private final AssetRepository assetRepository;
    private final StockService stockService;

    public void updateAllBankAccount(List<Member> members) {

        for (Member member : members) {

            try {
                accountService.updateBankAccount(member);
            } catch (Exception e) {
                log.error("은행 계좌 업데이트 중 오류 발생: ", e);
            }

        }
    }

    public void updateAllStockAccountAndStock(List<Member> members) {

        for (Member member : members) {
            try {
                stockService.updateStockAccountAndStockInfo(member);
            } catch (IOException | ParseException | InterruptedException e) {
                log.error("주식계좌와 주식 정보 업데이트 오류: ", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void saveAsset(BigDecimal cash, BigDecimal stock_valuation, BigDecimal bond_valuation, BigDecimal property_valuation) {
        assetRepository.save(cash, stock_valuation, bond_valuation, property_valuation);
    }
}
