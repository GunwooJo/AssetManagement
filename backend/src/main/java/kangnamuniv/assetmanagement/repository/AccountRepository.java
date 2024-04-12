package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.*;
import kangnamuniv.assetmanagement.util.CommonConstant;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public void saveBankAccount(Member member, String accountNumber, String organization, String businessType, BigDecimal balance, String accountName) throws Exception {

        try {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(accountNumber);
            bankAccount.setOrganization(organization);
            bankAccount.setBusinessType(businessType);
            bankAccount.setBalance(balance);
            bankAccount.setMember(member);
            bankAccount.setAccountName(accountName);

            em.persist(bankAccount);

        } catch (Exception e) {
            log.error("BankAccount 저장 중 에러: " + e.getMessage());
            throw new Exception(e);
        }

    }

    public StockAccount saveStockAccount(Member member, String accountNumber, String organization, String businessType, BigDecimal depositReceived, String accountName) {

        StockAccount stockAccount = new StockAccount();
        stockAccount.setMember(member);
        stockAccount.setAccountNumber(accountNumber);
        stockAccount.setOrganization(organization);
        stockAccount.setBusinessType(businessType);
        stockAccount.setDepositReceived(depositReceived);
        stockAccount.setAccountName(accountName);

        em.persist(stockAccount);
        return stockAccount;
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

    // 은행 계좌 업데이트(잔액)
    public void updateBankAccountByAccountNumber(String balance, String accountNumber, AccountCurrency accountCurrency) {

        BankAccount foundBankAccount = em.createQuery("select ba from BankAccount ba where ba.accountNumber = :accountNumber", BankAccount.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();

        foundBankAccount.setBalance(new BigDecimal(balance));
    }

    // db에 저장된 은행 계좌의 organization들 찾기.
    public Set<String> findBankOrganizationSet(String token) {
        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        Member foundMember = memberRepository.findByLoginId(loginIdFromToken);
        List<Account> foundAccounts = foundMember.getAccounts();

        Set<String> organizations = new HashSet<>();
        for (Account foundAccount : foundAccounts) {
            organizations.add(foundAccount.getOrganization());
        }
        return organizations;
    }

    private BigDecimal exchangeMoney(AccountCurrency accountCurrency, BigDecimal money) {

        if(Objects.equals(money, new BigDecimal(0))) {
            return new BigDecimal(0);
        }

        BigDecimal exChangeComplete = null;

        if(accountCurrency == AccountCurrency.USD) {
            exChangeComplete = money.multiply(CommonConstant.wonDollar);
        } else if (accountCurrency == AccountCurrency.EUR) {
            exChangeComplete = money.multiply(CommonConstant.wonEuro);
        } else if (accountCurrency == AccountCurrency.JPY) {
            exChangeComplete = money.multiply(CommonConstant.wonJpy);
        } else if (accountCurrency == AccountCurrency.KRW) {
            exChangeComplete = money;
        }

        return exChangeComplete;
    }

    public BankAccount findBankAccountByAccountNum(String accountNumber) {
        return em.createQuery("select ba from BankAccount ba where ba.accountNumber = :accountNumber", BankAccount.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();
    }
}
