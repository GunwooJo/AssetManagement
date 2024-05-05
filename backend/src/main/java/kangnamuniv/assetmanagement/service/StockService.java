package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.entity.StockAccount;
import kangnamuniv.assetmanagement.repository.AccountRepository;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.repository.StockRepository;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    //stock 테이블의 주식 정보 업데이트(추가, 수정), stock_account 테이블의 예수금 업데이트
    public void updateStockAccountAndStockInfo(String token) throws IOException, ParseException, InterruptedException {
        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);

        List<StockAccount> stockAccountList = accountRepository.findStockAccountListByLoginId(loginIdFromToken);

        //증권사 계좌가 1개인 경우
        if(stockAccountList.size() == 1) {
            StockAccount stockAccount = stockAccountList.get(0);
            JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), token);
            JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
            JSONArray resItemList = (JSONArray) resData.get("resItemList");
            String resDepositReceived = resData.get("resDepositReceived").toString();
            String resAccount = resData.get("resAccount").toString();

            accountRepository.updateStockAccountByAccountNumber(resDepositReceived, resAccount);    //계좌 예수금 업데이트

            for (Object itemObj : resItemList) {
                JSONObject item = (JSONObject) itemObj;
                String resItemName = item.get("resItemName").toString();
                String resValuationPL = item.get("resValuationPL").toString();
                String resValuationAmt = item.get("resValuationAmt").toString();
                String resQuantity = item.get("resQuantity").toString();
                String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                String resEarningsRate = item.get("resEarningsRate").toString();
                String resAccountCurrency = item.get("resAccountCurrency").toString();

                stockRepository.addOrUpdateStock(stockAccount, resItemName, resValuationPL, resValuationAmt, Long.valueOf(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
            }
        } else if (stockAccountList.size() > 1) {   //주식 계좌가 1개 이상인 경우(테스트 필요)

            for (StockAccount stockAccount : stockAccountList) {
                JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), token);
                JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
                JSONArray resItemList = (JSONArray) resData.get("resItemList");

                for (Object itemObj : resItemList) {
                    JSONObject item = (JSONObject) itemObj;
                    String resItemName = item.get("resItemName").toString();
                    String resValuationPL = item.get("resValuationPL").toString();
                    String resValuationAmt = item.get("resValuationAmt").toString();
                    String resQuantity = item.get("resQuantity").toString();
                    String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                    String resEarningsRate = item.get("resEarningsRate").toString();
                    String resAccountCurrency = item.get("resAccountCurrency").toString();

                    stockRepository.addOrUpdateStock(stockAccount, resItemName, resValuationPL, resValuationAmt, Long.valueOf(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
                }
            }
        }

    }

    public void updateStockAccountAndStockInfo(Member member) throws IOException, ParseException, InterruptedException {
        String loginIdFromToken = member.getLogin_id();

        List<StockAccount> stockAccountList = accountRepository.findStockAccountListByLoginId(loginIdFromToken);

        //증권사 계좌가 1개인 경우
        if(stockAccountList.size() == 1) {
            StockAccount stockAccount = stockAccountList.get(0);
            JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), member);
            JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
            JSONArray resItemList = (JSONArray) resData.get("resItemList");
            String resDepositReceived = resData.get("resDepositReceived").toString();
            String resAccount = resData.get("resAccount").toString();

            accountRepository.updateStockAccountByAccountNumber(resDepositReceived, resAccount);    //계좌 예수금 업데이트

            for (Object itemObj : resItemList) {
                JSONObject item = (JSONObject) itemObj;
                String resItemName = item.get("resItemName").toString();
                String resValuationPL = item.get("resValuationPL").toString();
                String resValuationAmt = item.get("resValuationAmt").toString();
                String resQuantity = item.get("resQuantity").toString();
                String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                String resEarningsRate = item.get("resEarningsRate").toString();
                String resAccountCurrency = item.get("resAccountCurrency").toString();

                stockRepository.addOrUpdateStock(stockAccount, resItemName, resValuationPL, resValuationAmt, Long.valueOf(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
            }
        } else if (stockAccountList.size() > 1) {   //주식 계좌가 1개 이상인 경우(테스트 필요)

            for (StockAccount stockAccount : stockAccountList) {
                JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), member);
                JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
                JSONArray resItemList = (JSONArray) resData.get("resItemList");

                for (Object itemObj : resItemList) {
                    JSONObject item = (JSONObject) itemObj;
                    String resItemName = item.get("resItemName").toString();
                    String resValuationPL = item.get("resValuationPL").toString();
                    String resValuationAmt = item.get("resValuationAmt").toString();
                    String resQuantity = item.get("resQuantity").toString();
                    String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                    String resEarningsRate = item.get("resEarningsRate").toString();
                    String resAccountCurrency = item.get("resAccountCurrency").toString();

                    stockRepository.addOrUpdateStock(stockAccount, resItemName, resValuationPL, resValuationAmt, Long.valueOf(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
                }
            }
        }

    }
}
