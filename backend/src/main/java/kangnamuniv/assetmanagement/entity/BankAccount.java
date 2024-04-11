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

}
