package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "business_type")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(length = 10)
    private String organization;

    @Enumerated(EnumType.STRING)
    private AccountCurrency accountCurrency; //통화코드 KRW: 한국원화, JPY: 일본 엔, USD: 미국 달러, EUR: 유로
}
