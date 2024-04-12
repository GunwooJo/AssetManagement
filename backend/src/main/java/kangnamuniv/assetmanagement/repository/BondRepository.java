package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Bond;
import kangnamuniv.assetmanagement.entity.StockAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        BigDecimal amout = new BigDecimal(valuationAmt);

        Bond newBond = Bond.builder()
                .bondName(bondName)
                .stockAccount(stockAccount)
                .valuationAmt(amout)
                .accountCurrency(accountCurrency)
                .build();

        try {
            em.persist(newBond);
        } catch (PersistenceException e) {
            log.error("채권 저장 에러: ", e.getMessage());
            throw new RuntimeException("채권 저장 실패.", e);
        }

        return newBond;
    }
}
