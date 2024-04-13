package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Bond;
import kangnamuniv.assetmanagement.entity.StockAccount;
import kangnamuniv.assetmanagement.repository.AccountRepository;
import kangnamuniv.assetmanagement.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BondService {

    private final BondRepository bondRepository;
    private final AccountRepository accountRepository;

    public Bond createAndSaveBond(String bondName, String accountNumber, String valuationAmt, String accountCurrency) {

        if (StringUtils.isBlank(bondName) || StringUtils.isBlank(accountNumber) || StringUtils.isBlank(valuationAmt) || StringUtils.isBlank(accountCurrency)) { //null, 공백, 빈값 체크.

            log.error("인풋 파라미터가 잘못됨.");
            throw new IllegalArgumentException("인풋 파라미터가 잘못됨.");
        }

        StockAccount foundStockAccount = accountRepository.findStockAccountByAccountNum(accountNumber);

        return bondRepository.createAndSaveBond(bondName, foundStockAccount, valuationAmt, AccountCurrency.valueOf(accountCurrency));

    }
}