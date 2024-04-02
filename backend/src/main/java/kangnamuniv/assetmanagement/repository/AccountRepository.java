package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.Account;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.BankAccount;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
}
