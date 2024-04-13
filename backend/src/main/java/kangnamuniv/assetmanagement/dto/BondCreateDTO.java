package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BondCreateDTO {

    @NotBlank
    String bondName;

    @NotBlank
    String accountNumber;

    @NotBlank
    String valuationAmt;

    @NotBlank
    String accountCurrency;
}
