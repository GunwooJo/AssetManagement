package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BondUpdateDTO {

    @NotNull(message = "id값 누락.")
    @Min(value = 1, message = "id는 1보다 큰 정수이어야 합니다.")
    private Long id;

    @NotBlank
    private String bondName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String valuationAmt;

    @NotBlank
    private String accountCurrency;
}
