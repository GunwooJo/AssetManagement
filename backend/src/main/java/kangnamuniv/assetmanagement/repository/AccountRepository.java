package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.Account;
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

    public void saveAccount(Member member, String accountNumber, String organization, String businessType, BigDecimal balance) {
        Account account = new Account();
        account.setMember(member);
        account.setAccountNumber(accountNumber);
        account.setOrganization(organization);
        account.setBusinessType(businessType);
        account.setBalance(balance);
        em.persist(account);
    }
}
