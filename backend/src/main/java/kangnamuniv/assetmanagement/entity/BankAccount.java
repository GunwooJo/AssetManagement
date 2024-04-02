package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
public class BankAccount extends Account{

    @Column(precision=16, scale=2, nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency accountCurrency; //통화코드 KRW: 한국원화, JPY: 일본 엔, USD: 미국 달러, EUR: 유로
}
