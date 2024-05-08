package kangnamuniv.assetmanagement.dto;

import kangnamuniv.assetmanagement.entity.AccountCurrency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Builder
public class BondListResponseDTO {

    private Long id;

    private String bondName;    //채권이름

    private BigDecimal valuationAmt; //평가금액

    private AccountCurrency accountCurrency;

}
