package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.entity.*;
import kangnamuniv.assetmanagement.repository.AssetRepository;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

    private final AccountService accountService;
    private final AssetRepository assetRepository;
    private final StockService stockService;
    private final MemberRepository memberRepository;
    private final BondService bondService;

    public void updateAllBankAccount(List<Member> members) {

        for (Member member : members) {

            try {
                accountService.updateBankAccount(member);
            } catch (Exception e) {
                log.error("은행 계좌 업데이트 중 오류 발생: ", e);
                throw new RuntimeException(e);
            }

        }
    }

    public void updateAllStockAccountAndStock(List<Member> members) {

        for (Member member : members) {
            try {
                stockService.updateStockAccountAndStockInfo(member);
            } catch (IOException | ParseException | InterruptedException e) {
                log.error("주식계좌와 주식 정보 업데이트 오류: ", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void saveAsset(Member member, BigDecimal cash, BigDecimal stock_valuation, BigDecimal bond_valuation, BigDecimal property_valuation) {
        assetRepository.save(member, cash, stock_valuation, bond_valuation, property_valuation);
    }

    //특정 멤버의 모든 현금 조회
    public BigDecimal getMemberCash(Member member) {

        BigDecimal total = new BigDecimal(0);

        List<Account> accounts = member.getAccounts();

        for (Account account : accounts) {

            if (account instanceof BankAccount) {
                BigDecimal balance = ((BankAccount) account).getBalance();
                total = total.add(balance);

            } else if (account instanceof StockAccount) {
                BigDecimal depositReceived = ((StockAccount) account).getDepositReceived();
                total = total.add(depositReceived);
            }
        }

        return total;
    }

    //특정 멤버의 주식 전체 평가금액 조회
    public BigDecimal getMemberStockValuation(Member member) {

        BigDecimal total = new BigDecimal(0);

        List<Account> accounts = member.getAccounts();

        for (Account account : accounts) {

            if (account instanceof StockAccount) {
                List<Stock> stockList = ((StockAccount) account).getStockList();

                for (Stock stock : stockList) {

                    if(stock.getAccountCurrency() == AccountCurrency.KRW) {
                        total = total.add(stock.getValuationAmt());
                    } else if (stock.getAccountCurrency() == AccountCurrency.USD) {
                        total = total.add(stock.getValuationAmt().multiply(CommonConstant.wonDollar));
                    } else if (stock.getAccountCurrency() == AccountCurrency.JPY) {
                        total = total.add(stock.getValuationAmt().multiply(CommonConstant.wonJpy));
                    } else if (stock.getAccountCurrency() == AccountCurrency.EUR) {
                        total = total.add(stock.getValuationAmt().multiply(CommonConstant.wonEuro));
                    }
                }
            }
        }

        return total;
    }

    //추후 부동산 평가금액 입력 필요.
    public void saveAllMemberAsset(List<Member> members) {
        try {
            for (Member member : members) {
                saveAsset(member, getMemberCash(member), getMemberStockValuation(member), bondService.getBondValuationByMember(member), new BigDecimal(0));
            }
        } catch (Exception e) {
            log.error("asset 저장 중 에러 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
