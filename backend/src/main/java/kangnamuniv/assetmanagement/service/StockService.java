package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.dto.StockAccountUpdate;
import kangnamuniv.assetmanagement.dto.StockDTO;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.entity.StockAccount;
import kangnamuniv.assetmanagement.repository.AccountRepository;
import kangnamuniv.assetmanagement.repository.StockRepository;
import kangnamuniv.assetmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        String loginId = member.getLogin_id();

        long s1 = System.currentTimeMillis();
        List<StockAccount> stockAccountList = accountRepository.findStockAccountListByLoginId(loginId);
        long e1 = System.currentTimeMillis();
        long d1 = e1 - s1;
        System.out.println("findStockAccountListByLoginId 실행시간: " + d1 + "ms");

        //증권사 계좌가 1개인 경우
        if(stockAccountList.size() == 1) {
            StockAccount stockAccount = stockAccountList.get(0);
            long s7 = System.currentTimeMillis();

            JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), member);
            long e7 = System.currentTimeMillis();
            long d7 = e7 - s7;
            System.out.println("증권사 계좌가 1개인 경우 getTotalStockAccountList 실행시간: " + d7 + "ms");


            JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
            JSONArray resItemList = (JSONArray) resData.get("resItemList");
            String resDepositReceived = resData.get("resDepositReceived").toString();
            String resAccount = resData.get("resAccount").toString();

            accountRepository.updateStockAccountByAccountNumber(resDepositReceived, resAccount);    //계좌 예수금 업데이트

            List<StockDTO> stockDTOS = new ArrayList<>();

            for (Object itemObj : resItemList) {
                JSONObject item = (JSONObject) itemObj;
                String resItemName = item.get("resItemName").toString();
                String resValuationPL = item.get("resValuationPL").toString();
                String resValuationAmt = item.get("resValuationAmt").toString();
                String resQuantity = item.get("resQuantity").toString();
                String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                String resEarningsRate = item.get("resEarningsRate").toString();
                String resAccountCurrency = item.get("resAccountCurrency").toString();

                StockDTO stockDTO = StockDTO.builder()
                        .accountCurrency(AccountCurrency.valueOf(resAccountCurrency))
                        .earningsRate(new BigDecimal(resEarningsRate))
                        .valuationAmt(new BigDecimal(resValuationAmt))
                        .purchaseAmount(new BigDecimal(resPurchaseAmount))
                        .quantity(Long.parseLong(resQuantity))
                        .itemName(resItemName)
                        .valuationPl(new BigDecimal(resValuationPL))
                        .build();

                stockDTOS.add(stockDTO);

                stockRepository.updateStock(stockAccount, stockDTOS);
            }
        } else if (stockAccountList.size() > 1) {   //주식 계좌가 1개 이상인 경우(테스트 필요)

            List<StockAccountUpdate> stockAccountUpdateDTOs = new ArrayList<>();
            int i=0;
            for (StockAccount stockAccount : stockAccountList) {
                i++;
                System.out.println("계좌이름 = " + stockAccount.getAccountName());
                System.out.println("---증권사 종합자산 조회 시작---" + i);
                long s2 = System.currentTimeMillis();
                JSONObject resTotalStockAccount = accountService.getTotalStockAccountList(stockAccount.getOrganization(), stockAccount.getAccountNumber(), member);
                long e2 = System.currentTimeMillis();
                long d2 = e2 - s2;
                System.out.println("getTotalStockAccountList" + i + " 실행시간: " + d2 + "ms");
                System.out.println("---증권사 종합자산 조회 끝---" + i);

                JSONObject resData = (JSONObject) resTotalStockAccount.get("data");
                JSONArray resItemList = (JSONArray) resData.get("resItemList");
                String resDepositReceived = resData.get("resDepositReceived").toString();
                String resAccount = resData.get("resAccount").toString();

                stockAccountUpdateDTOs.add(new StockAccountUpdate(resDepositReceived, resAccount));

                List<StockDTO> stockDTOS = new ArrayList<>();

                for (Object itemObj : resItemList) {
                    JSONObject item = (JSONObject) itemObj;
                    String resItemName = item.get("resItemName").toString();
                    String resValuationPL = item.get("resValuationPL").toString();
                    String resValuationAmt = item.get("resValuationAmt").toString();
                    String resQuantity = item.get("resQuantity").toString();
                    String resPurchaseAmount = item.get("resPurchaseAmount").toString();
                    String resEarningsRate = item.get("resEarningsRate").toString();
                    String resAccountCurrency = item.get("resAccountCurrency").toString();

                    StockDTO stockDTO = StockDTO.builder()
                            .accountCurrency(AccountCurrency.valueOf(resAccountCurrency))
                            .earningsRate(new BigDecimal(resEarningsRate))
                            .valuationAmt(new BigDecimal(resValuationAmt))
                            .purchaseAmount(new BigDecimal(resPurchaseAmount))
                            .quantity(Long.parseLong(resQuantity))
                            .itemName(resItemName)
                            .valuationPl(new BigDecimal(resValuationPL))
                            .build();

                    stockDTOS.add(stockDTO);

                }
                System.out.println("---주식 정보 업데이트 시작---");
                long s3 = System.currentTimeMillis();

                stockRepository.updateStock(stockAccount, stockDTOS);
                long e3 = System.currentTimeMillis();
                long d3 = e3 - s3;
                System.out.println("updateStock 실행시간: " + d3 + "ms");


                System.out.println("---주식 정보 업데이트 끝---");
            }
            System.out.println("---계좌 정보 업데이트 시작---");
            long s4 = System.currentTimeMillis();
            accountRepository.updateStockAccountByAccountNumber(stockAccountUpdateDTOs);
            long e4 = System.currentTimeMillis();
            long d4 = e4 - s4;
            System.out.println("updateStockAccountByAccountNumber 실행시간: " + d4 + "ms");
            System.out.println("---계좌 정보 업데이트 끝---");
        }

    }
}
