package kangnamuniv.assetmanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class BankAccountUpdate {

    private String accountNum;
    private String accountBalance;
    private String resAccountCurrency;
}
