package kangnamuniv.assetmanagement.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AssetDTO {

    private BigDecimal cash;

    private BigDecimal stockValuation;

    private BigDecimal bondValuation;

    private BigDecimal propertyValuation;

    private LocalDate createdAt;
}
