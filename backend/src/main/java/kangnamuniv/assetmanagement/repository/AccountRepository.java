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

    // 증권사 계좌 업데이트(예수금)
    public void updateStockAccountByAccountNumber(String depositReceived, String accountNumber) {

        StockAccount foundStockAccount = em.createQuery("select sc from StockAccount sc where sc.accountNumber = :accountNumber", StockAccount.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();

        foundStockAccount.setDepositReceived(new BigDecimal(depositReceived));
    }
}
