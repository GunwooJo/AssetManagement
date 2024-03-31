package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StockAccountListDTO {

    @NotBlank
    private String organization;    // 기관코드
}
