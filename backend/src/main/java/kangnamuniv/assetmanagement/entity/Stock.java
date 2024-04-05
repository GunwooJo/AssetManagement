package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private StockAccount stockAccount;

    @Column(name = "item_name", length = 30, nullable = false)
    private String itemName;    //상품/종목명

    @Column(name = "valuation_pl", precision=16, scale=2, nullable = false)
    private BigDecimal valuationPl; //평가손익

    @Column(name = "valuation_amt", precision=16, scale=2, nullable = false)
    private BigDecimal valuationAmt; //평가금액

    private Long quantity;

    @Column(name = "purchase_amount", precision=16, scale=2, nullable = false)
    private BigDecimal purchaseAmount; //매입금액

    @Column(name = "earnings_rate", precision=16, scale=2, nullable = false)
    private BigDecimal earningsRate; //수익률(%)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency accountCurrency;
}
