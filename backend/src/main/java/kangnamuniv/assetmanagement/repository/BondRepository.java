package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kangnamuniv.assetmanagement.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BondRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public Bond createAndSaveBond(String bondName, StockAccount stockAccount, String valuationAmt, AccountCurrency accountCurrency) {

        if(StringUtils.isBlank(bondName) || stockAccount == null || StringUtils.isBlank(valuationAmt) || accountCurrency == null) {
            log.error("채권 생성 후 저장을 위한 파라미터가 잘못됨.");
            throw new IllegalArgumentException("채권 생성 후 저장을 위한 파라미터가 잘못됨.");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(valuationAmt);
        } catch (NumberFormatException e) {
            log.error("평가금액 형식 잘못됨: {}", valuationAmt, e);
            throw new IllegalArgumentException("평가금액에 숫자가 전달되지 않음.");
        }

        Bond newBond = Bond.builder()
                .bondName(bondName)
                .stockAccount(stockAccount)
                .valuationAmt(amount)
                .accountCurrency(accountCurrency)
                .build();

        try {
            em.persist(newBond);
        } catch (Exception e) {
            log.error("채권 저장 에러: {}", newBond, e);
            throw new RuntimeException("채권 저장 실패: ", e);
        }

        return newBond;
    }

    public Bond findBondById(Long id) {

        if(id == null) {
            throw new IllegalArgumentException("입력 파라미터 오류.");
        }

        try {
            Bond foundBond = em.find(Bond.class, id);
            log.debug("조회된 채권: {}", foundBond);
            return foundBond;

        } catch (Exception e) {
            log.error("채권 조회 중 에러 발생: ", e);
            throw new RuntimeException("채권 조회 중 에러 발생.");
        }
    }

    public Bond updateBond(Long bondId, String bondName, StockAccount stockAccount, String valuationAmt, AccountCurrency accountCurrency) {

        if(StringUtils.isBlank(bondName) || stockAccount == null || StringUtils.isBlank(valuationAmt) || accountCurrency == null) {
            log.error("입력 파라미터가 잘못됨.");
            throw new IllegalArgumentException("입력 파라미터가 잘못됨.");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(valuationAmt);
        } catch (NumberFormatException e) {
            log.error("평가금액 형식 잘못됨: {}", valuationAmt, e);
            throw new IllegalArgumentException("평가금액에 숫자가 전달되지 않음.");
        }

        Bond foundBond = findBondById(bondId);
        foundBond.setBondName(bondName);
        foundBond.setStockAccount(stockAccount);
        foundBond.setValuationAmt(amount);
        foundBond.setAccountCurrency(accountCurrency);

        return foundBond;
    }

    public List<Bond> findBondListByLoginId(String login_id) {

        List<Bond> totalBondList = new ArrayList<>();

        Member foundMember = memberRepository.findByLoginId(login_id);
        if(foundMember == null) {
            log.info("해당 로그인 아이디를 가진 회원이 없습니다.");
            return totalBondList;
        }

        List<Account> foundAccounts = foundMember.getAccounts();
        for (Account foundAccount : foundAccounts) {

            if(foundAccount instanceof StockAccount) {
                List<Bond> foundBondList = ((StockAccount) foundAccount).getBondList();

                totalBondList.addAll(foundBondList);

            }
        }
        return totalBondList;

    }

    public void deleteBondById(Long id) {

        if(id == null) {
            throw new IllegalArgumentException("채권의 id값은 null이 될 수 없음.");
        }

        Bond foundBond = em.find(Bond.class, id);
        System.out.println("foundBond = " + foundBond);
        log.debug("조회된 채권: {}", foundBond);

        if (foundBond == null) {
            log.info("해당 아이디를 가진 채권 없음.");
            throw new RuntimeException("해당 아이디를 가진 채권 없음.");
        }

        em.remove(foundBond);
        log.debug("삭제된 채권: {}", foundBond);
    }
}
