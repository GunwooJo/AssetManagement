package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
public class StockAccount extends Account{

    @Column(name = "deposit_received", precision=16, scale=2, nullable = false)
    private BigDecimal depositReceived;
}
