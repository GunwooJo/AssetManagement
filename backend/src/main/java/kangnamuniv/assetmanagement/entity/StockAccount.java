package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("ST")
public class StockAccount extends Account{

    @Column(name = "product_type_cd", length = 10, nullable = false)
    private String productTypeCd;

    @Column(name = "deposit_received", precision=16, scale=2, nullable = false)
    private BigDecimal depositReceived;
}
