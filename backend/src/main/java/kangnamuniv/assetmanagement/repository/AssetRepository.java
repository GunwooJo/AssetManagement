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
import java.util.List;

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

    public List<Asset> findAssetsByMemberAndDates(Member member , LocalDate startDate, LocalDate endDate) {

        if (member == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Member, start date, and end date must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must not be after end date");
        }

        try {
            return em.createQuery("select a from Asset a where a.member = :member and a.createdAt between :startDate and :endDate", Asset.class)
                    .setParameter("member", member)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();

        } catch (Exception e) {
            log.error("Asset 가져오기 실패: ", e);
            throw new RuntimeException(e);
        }

    }

}
