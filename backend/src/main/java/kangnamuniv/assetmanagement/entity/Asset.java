package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal cash;

    @Column(name = "stock_valuation", precision=16, scale=2, nullable = false)
    private BigDecimal stockValuation;

    @Column(name = "bond_valuation", precision=16, scale=2, nullable = false)
    private BigDecimal bondValuation;

    @Column(name = "property_valuation", precision=16, scale=2, nullable = false)
    private BigDecimal propertyValuation;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
}
