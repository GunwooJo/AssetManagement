package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.Asset;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssetRepository {

    private final EntityManager em;

    public void save(Member member, BigDecimal cash, BigDecimal stock_valuation, BigDecimal bond_valuation, BigDecimal property_valuation) {

        try {
            Asset asset = Asset.builder()
                    .member(member)
                    .cash(cash)
                    .stockValuation(stock_valuation)
                    .bondValuation(bond_valuation)
                    .propertyValuation(property_valuation)
                    .createdAt(LocalDate.now())
                    .build();

            em.persist(asset);

        } catch (Exception e) {
            log.error("Asset 저장 실패: " , e);
            throw new RuntimeException(e);
        }

    }
}
