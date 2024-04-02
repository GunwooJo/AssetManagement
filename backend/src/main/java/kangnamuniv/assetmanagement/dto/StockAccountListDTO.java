package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockAccountListDTO {

    @NotBlank
    private String organization;    // 기관코드
}
