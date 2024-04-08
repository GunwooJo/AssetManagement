package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class StockAccount extends Account{

    @Setter
    @Column(name = "deposit_received", precision=16, scale=2, nullable = false)
    private BigDecimal depositReceived;

    @OneToMany(mappedBy = "stockAccount")
    private List<Stock> stockList = new ArrayList<>();

}
