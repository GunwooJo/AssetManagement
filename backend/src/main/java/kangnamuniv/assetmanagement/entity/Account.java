package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "account_number")
    private String accountNumber;

    private String organization;

    @Column(name = "business_type")
    private String businessType;

    @Column(precision=16, scale=2)
    private BigDecimal balance;
}
