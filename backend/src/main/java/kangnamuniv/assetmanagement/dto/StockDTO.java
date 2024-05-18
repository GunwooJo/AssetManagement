package kangnamuniv.assetmanagement.dto;

import jakarta.persistence.*;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private String itemName;    //상품/종목명

    private BigDecimal valuationPl; //평가손익

    private BigDecimal valuationAmt; //평가금액

    private Long quantity;

    private BigDecimal purchaseAmount; //매입금액

    private BigDecimal earningsRate; //수익률(%)

    @Enumerated(EnumType.STRING)
    private AccountCurrency accountCurrency;
}
