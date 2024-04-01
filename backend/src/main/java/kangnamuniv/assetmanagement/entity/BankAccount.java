package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("BK")
public class BankAccount extends Account{

    @Column(precision=16, scale=2, nullable = false)
    private BigDecimal balance;
}
