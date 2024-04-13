package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Bond;
import kangnamuniv.assetmanagement.entity.StockAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BondRepository {

    private final EntityManager em;

    public Bond createAndSaveBond(String bondName, StockAccount stockAccount, String valuationAmt, AccountCurrency accountCurrency) {

        if(StringUtils.isBlank(bondName) || stockAccount == null || StringUtils.isBlank(valuationAmt) || accountCurrency == null) {
            log.error("채권 생성 후 저장을 위한 파라미터가 잘못됨.");
            throw new IllegalArgumentException("채권 생성 후 저장을 위한 파라미터가 잘못됨.");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(valuationAmt);
        } catch (NumberFormatException e) {
            log.error("평가금액 형식 잘못됨: {}", valuationAmt, e);
            throw new IllegalArgumentException("평가금액에 숫자가 전달되지 않음.");
        }

        Bond newBond = Bond.builder()
                .bondName(bondName)
                .stockAccount(stockAccount)
                .valuationAmt(amount)
                .accountCurrency(accountCurrency)
                .build();

        try {
            em.persist(newBond);
        } catch (Exception e) {
            log.error("채권 저장 에러: {}", newBond, e);
            throw new RuntimeException("채권 저장 실패: ", e);
        }

        return newBond;
    }
}
