package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    private String organization;

    @Column(name = "account_type")
    private String accountType;

    @Column(precision=17, scale=2)
    private BigDecimal balance;
}
