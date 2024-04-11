package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "account_number", length = 30, nullable = false)
    private String accountNumber;

    @Column(length = 10, nullable = false)
    private String organization;

    @Column(name = "business_type", length = 20, nullable = false)
    private String businessType;

    @Column(name = "account_name", length = 20, nullable = false)
    private String accountName;
}
