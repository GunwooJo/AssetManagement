package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BondCreateDTO {

    @NotBlank
    private String bondName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String valuationAmt;

    @NotBlank
    private String accountCurrency;
}
