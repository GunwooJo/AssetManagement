package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Bond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bond_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private StockAccount stockAccount;

    @Column(name = "valuation_amt", precision=16, scale=2, nullable = false)
    private BigDecimal valuationAmt; //평가금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency accountCurrency;
}
