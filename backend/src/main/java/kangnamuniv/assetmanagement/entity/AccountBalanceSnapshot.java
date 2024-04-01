package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AccountBalanceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_snapshot_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(precision=16, scale=2, nullable = false)
    private BigDecimal balance;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;
}
