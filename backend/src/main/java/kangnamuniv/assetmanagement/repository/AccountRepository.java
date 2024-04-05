package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class AccountRepository {

    private final EntityManager em;

    public void saveBankAccount(Member member, String accountNumber, String organization, String businessType, AccountCurrency accountCurrency, BigDecimal balance) {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setOrganization(organization);
        bankAccount.setBusinessType(businessType);
        bankAccount.setAccountCurrency(accountCurrency);
        bankAccount.setBalance(balance);
        bankAccount.setMember(member);

        em.persist(bankAccount);
    }

    public void saveStockAccount(Member member, String accountNumber, String organization, String businessType, BigDecimal depositReceived) {

        StockAccount stockAccount = new StockAccount();
        stockAccount.setMember(member);
        stockAccount.setAccountNumber(accountNumber);
        stockAccount.setOrganization(organization);
        stockAccount.setBusinessType(businessType);
        stockAccount.setDepositReceived(depositReceived);

        em.persist(stockAccount);
    }

    public List<StockAccount> findStockAccountListByLoginId(String login_id) {

        return em.createQuery("select sc from StockAccount sc where sc.member.login_id = :login_id", StockAccount.class)
                .setParameter("login_id", login_id)
                .getResultList();
    }

    //보유 주식을 stock 테이블에 저장
    public Stock saveStock(StockAccount stockAccount, String itemName, String valutaionPl, String valuationAmt, Long quantity, String purchaseAmount, String earningsRate, AccountCurrency accountCurrency) {
        Stock stock = Stock.builder()
                .itemName(itemName)
                .valuationPl(new BigDecimal(valutaionPl))
                .valuationAmt(new BigDecimal(valuationAmt))
                .quantity(quantity)
                .purchaseAmount(new BigDecimal(purchaseAmount))
                .earningsRate(new BigDecimal(earningsRate))
                .accountCurrency(accountCurrency)
                .build();

        stock.setStockAccount(stockAccount);
        em.persist(stock);
        return stock;
    }
}
