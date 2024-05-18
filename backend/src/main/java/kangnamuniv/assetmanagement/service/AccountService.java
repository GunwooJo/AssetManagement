package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.dto.BankAccountUpdate;
import kangnamuniv.assetmanagement.dto.StockAccountListDTO;
import kangnamuniv.assetmanagement.dto.TransactionCheckDTO;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Member;
import kangnamuniv.assetmanagement.entity.StockAccount;
import kangnamuniv.assetmanagement.repository.AccountRepository;
import kangnamuniv.assetmanagement.repository.MemberRepository;
import kangnamuniv.assetmanagement.repository.StockRepository;
import kangnamuniv.assetmanagement.util.ApiRequest;
import kangnamuniv.assetmanagement.util.CommonConstant;
import kangnamuniv.assetmanagement.util.JwtUtil;
import kangnamuniv.assetmanagement.util.RSAUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;

    //계정을 등록하여 connectedId 발급.
    public void addAccount(String businessType, String loginType, String organization, String id, String password, String clientType, String token) throws Exception{

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String urlPath = "https://development.codef.io/v1/account/create";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	clientType);
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);
        //유저가 connectedId를 기존에 가지고 있었으면 해당 connectedId로 계정 추가.
        if(memberService.isConnectedIdExist(loginIdFromToken)) {
            urlPath = "https://development.codef.io/v1/account/add";
            String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);
            System.out.println("foundConnectedId = " + foundConnectedId);
            bodyMap.put("connectedId", foundConnectedId);
        }

        try {
            // CODEF API 호출
            JSONObject jsonResponse = ApiRequest.request2(urlPath, bodyMap);
            JSONObject result = (JSONObject) jsonResponse.get("result");
            String resCode = result.get("code").toString();
            JSONObject data = (JSONObject)jsonResponse.get("data");

            if(Objects.equals(resCode, "CF-00000") && jsonResponse.containsKey("data")) {
                String connectedId = data.get("connectedId").toString();
                //유저가 connectedId를 갖고있지 않다면 발급된 connectedId 저장
                if (!memberService.isConnectedIdExist(loginIdFromToken)) {
                    memberService.saveConnectedId(loginIdFromToken, connectedId);
                }
            } else {
                JSONArray jsonErrorList = (JSONArray) data.get("errorList");
                JSONObject jsonErrorObj = (JSONObject) jsonErrorList.get(0);
                String errorMsg = jsonErrorObj.get("message").toString();
                throw new Exception(errorMsg);
            }

            saveAccountToDB(businessType, loginType, organization, id, password, clientType, token);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    //특정 connectedId로 등록한 계정(기관)들 확인
    public JSONObject findAccountsByConnectedId(String connectedId) throws Exception{
        String urlPath = "https://development.codef.io/v1/account/list";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        bodyMap.put("connectedId", connectedId);

        try {
            return ApiRequest.request2(urlPath, bodyMap);

        } catch (IOException | InterruptedException | ParseException e) {
            log.error(e.getMessage());
            throw new Exception("Request to CODEF server failed: " + e.getMessage(), e);
        }
    }

    //은행 보유계좌 조회
    public JSONObject findOwnAccountList(String organization, String token) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/bank/p/account/account-list";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);
        Member foundMember = memberRepository.findByLoginId(loginIdFromToken);

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", organization);

        LocalDate birthdate = foundMember.getBirthdate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");  // '-' 제거
        String formattedBirthdate = birthdate.format(formatter);

        bodyMap.put("birthDate", formattedBirthdate);

        return ApiRequest.request2(urlPath, bodyMap);
    }

    //은행 보유계좌 조회
    public JSONObject findOwnAccountList(String organization, Member member) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/bank/p/account/account-list";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String foundConnectedId = member.getConnected_id();

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", organization);

        LocalDate birthdate = member.getBirthdate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");  // '-' 제거
        String formattedBirthdate = birthdate.format(formatter);

        bodyMap.put("birthDate", formattedBirthdate);

        return ApiRequest.request2(urlPath, bodyMap);
    }

    public JSONObject getTransactionList(TransactionCheckDTO transactionCheckDTO, String token) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/bank/p/account/transaction-list";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", transactionCheckDTO.getOrganization());
        bodyMap.put("birthDate", transactionCheckDTO.getBirthday());
        bodyMap.put("account", transactionCheckDTO.getAccount());
        bodyMap.put("startDate", transactionCheckDTO.getStartDate());
        bodyMap.put("endDate", transactionCheckDTO.getEndDate());
        bodyMap.put("orderBy", transactionCheckDTO.getOrderBy());

        if(transactionCheckDTO.getAccountPassword() != null) {
            try {
                bodyMap.put("accountPassword", RSAUtil.encryptRSA(transactionCheckDTO.getAccountPassword(), CommonConstant.PUBLIC_KEY));
            } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                    IllegalBlockSizeException | BadPaddingException e) {
                log.error(e.getMessage());
                throw new Error("계좌 비밀번호 RSA암호화 실패: " + e);
            }
        }

        if(transactionCheckDTO.getWithdrawAccountNo() != null) {
            bodyMap.put("withdrawAccountNo", transactionCheckDTO.getWithdrawAccountNo());
        }

        if (transactionCheckDTO.getWithdrawAccountPassword() != null) {
            bodyMap.put("withdrawAccountPassword", transactionCheckDTO.getWithdrawAccountPassword());
        }

        if (transactionCheckDTO.getInquiryType() != null) {
            bodyMap.put("inquiryType", transactionCheckDTO.getInquiryType());
        }

        return ApiRequest.request2(urlPath, bodyMap);
    }

    //증권사 계좌조회(전계좌)
    public JSONObject getStockAccountList(StockAccountListDTO stockAccountListDTO, String token) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/stock/a/account/account-list";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", stockAccountListDTO.getOrganization());

        return ApiRequest.request2(urlPath, bodyMap);
    }

    //증권사 계좌조회(종합자산)
    public JSONObject getTotalStockAccountList(String organization, String accountNumber, String token) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/stock/a/account/financial-assets";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", organization);
        bodyMap.put("account", accountNumber);

        return ApiRequest.request2(urlPath, bodyMap);
    }

    //증권사 계좌조회(종합자산)
    public JSONObject getTotalStockAccountList(String organization, String accountNumber, Member member) throws IOException, ParseException, InterruptedException {
        String urlPath = "https://development.codef.io/v1/kr/stock/a/account/financial-assets";
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();

        String loginIdFromToken = member.getLogin_id();
        String foundConnectedId = memberService.getConnectedIdByLoginId(loginIdFromToken);

        bodyMap.put("connectedId", foundConnectedId);
        bodyMap.put("organization", organization);
        bodyMap.put("account", accountNumber);

        return ApiRequest.request2(urlPath, bodyMap);
    }

    //DB에 계좌정보 저장
    public void saveAccountToDB(String businessType, String loginType, String organization, String id, String password, String clientType, String token) throws IOException, ParseException, InterruptedException {

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        Member foundMember = memberRepository.findByLoginId(loginIdFromToken);

        //은행 계좌일 경우
        if(Objects.equals(businessType, "BK")) {
            JSONObject response = findOwnAccountList(organization, token);
            JSONObject resData = (JSONObject) response.get("data");

            //resDepositTrust가 리스트일 경우.
            if(resData.get("resDepositTrust") instanceof JSONArray) {
                JSONArray resDepositTrustList = (JSONArray) resData.get("resDepositTrust");

                for (Object depositTrust : resDepositTrustList) {
                    JSONObject deposit = (JSONObject) depositTrust;
                    String resAccountNumber = deposit.get("resAccount").toString();
                    String resAccountCurrency = deposit.get("resAccountCurrency").toString();
                    String resAccountName = deposit.get("resAccountName").toString();
                    BigDecimal resAccountBalance = null;

                    if(Objects.equals(resAccountCurrency, "KRW")) {//계좌의 화폐가 원화일 경우
                        resAccountBalance = new BigDecimal(deposit.get("resAccountBalance").toString());

                    } else if(Objects.equals(resAccountCurrency, "USD")) {//계좌의 화폐가 달러일 경우(테스트 필요)
                        BigDecimal dollar = new BigDecimal(deposit.get("resAccountBalance").toString());
                        BigDecimal wonDollar = CommonConstant.wonDollar;
                        resAccountBalance = dollar.multiply(wonDollar);

                    } else if(Objects.equals(resAccountCurrency, "JPY")) {
                        BigDecimal jpy = new BigDecimal(deposit.get("resAccountBalance").toString());
                        resAccountBalance = jpy.multiply(CommonConstant.wonJpy);

                    } else if(Objects.equals(resAccountCurrency, "EUR")) {
                        BigDecimal euro = new BigDecimal(deposit.get("resAccountBalance").toString());
                        resAccountBalance = euro.multiply(CommonConstant.wonEuro);
                    }

                    try {
                        accountRepository.saveBankAccount(foundMember, resAccountNumber, organization, businessType, resAccountBalance, resAccountName);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }

            } else if(resData.get("resDepositTrust") instanceof JSONObject) {//resDepositTrust가 단일객체일 경우.(테스트 필요)
                System.out.println("단일 계좌 저장");
                JSONObject deposit = (JSONObject) resData.get("resDepositTrust");
                String resAccountNumber = deposit.get("resAccount").toString();
                String resAccountCurrency = deposit.get("resAccountCurrency").toString();
                String resAccountName = deposit.get("resAccountName").toString();

                BigDecimal resAccountBalance = null;

                if(Objects.equals(resAccountCurrency, "KRW")) {//계좌의 화폐가 원화일 경우
                    resAccountBalance = new BigDecimal(deposit.get("resAccountBalance").toString());

                } else if(Objects.equals(resAccountCurrency, "USD")) {//계좌의 화폐가 달러일 경우(테스트 필요)
                    BigDecimal dollar = new BigDecimal(deposit.get("resAccountBalance").toString());
                    BigDecimal wonDollar = CommonConstant.wonDollar;
                    resAccountBalance = dollar.multiply(wonDollar);

                } else if(Objects.equals(resAccountCurrency, "JPY")) {
                    BigDecimal jpy = new BigDecimal(deposit.get("resAccountBalance").toString());
                    resAccountBalance = jpy.multiply(CommonConstant.wonJpy);

                } else if(Objects.equals(resAccountCurrency, "EUR")) {
                    BigDecimal euro = new BigDecimal(deposit.get("resAccountBalance").toString());
                    resAccountBalance = euro.multiply(CommonConstant.wonEuro);
                }

                try {
                    accountRepository.saveBankAccount(foundMember, resAccountNumber, organization, businessType , resAccountBalance, resAccountName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } else if(Objects.equals(businessType, "ST")) {// 증권사 계좌일 경우

            StockAccountListDTO stockAccountListDTO = new StockAccountListDTO();
            stockAccountListDTO.setOrganization(organization);

            JSONObject response = getStockAccountList(stockAccountListDTO, token);
            List<String> accountNumbers = new ArrayList<>();    //보유 계좌의 계좌번호들
            Map<String, String> accountInfo = new HashMap<>();  //key: 계좌번호, value: 계좌이름

            //리스트로 반환됐을 경우
            if(response.get("data") instanceof JSONArray) {
                JSONArray accountList = (JSONArray) response.get("data");

                for (Object account : accountList) {
                    JSONObject jsonAccount = (JSONObject) account;
                    String resAccountNum = jsonAccount.get("resAccount").toString();
                    accountNumbers.add(resAccountNum);
                    accountInfo.put(resAccountNum, ((JSONObject) account).get("resAccountName").toString());
                }

                for (String accountNumber : accountNumbers) {
                    //증권사 종합자산 조회
                    JSONObject totalStockAccountList = getTotalStockAccountList(organization, accountNumber, token);

                    JSONObject resData = (JSONObject) totalStockAccountList.get("data");
                    String resDepositReceived = resData.get("resDepositReceived").toString();
                    BigDecimal depositReceived = new BigDecimal(resDepositReceived);
                    String accountName = accountInfo.get(accountNumber);

                    accountRepository.saveStockAccount(foundMember, accountNumber, organization, businessType, depositReceived, accountName);

                    //주식 정보 저장
                    JSONArray resItemList = (JSONArray) resData.get("resItemList");

                    for (Object item : resItemList) {
                        JSONObject stock = (JSONObject) item;
                        String resItemName = stock.get("resItemName").toString();
                        String resValuationPL = stock.get("resValuationPL").toString();
                        String resValuationAmt = stock.get("resValuationAmt").toString();
                        String resQuantity = stock.get("resQuantity").toString();
                        String resPurchaseAmount = stock.get("resPurchaseAmount").toString();
                        String resEarningsRate = stock.get("resEarningsRate").toString();
                        String resAccountCurrency = stock.get("resAccountCurrency").toString();

                        StockAccount foundStockAccount = accountRepository.findStockAccountByAccountNum(accountNumber);
                        stockRepository.addOrUpdateStock(foundStockAccount, resItemName, resValuationPL, resValuationAmt, Long.parseLong(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
                    }
                }

            } else if(response.get("data") instanceof JSONObject) {//단일객체로 반환됐을 경우
                JSONObject jsonAccount = (JSONObject) response.get("data");
                String resAccountNum = jsonAccount.get("resAccount").toString();
                accountInfo.put(resAccountNum, jsonAccount.get("resAccountName").toString());
                JSONObject totalStockAccountList = getTotalStockAccountList(organization, resAccountNum, token);
                JSONObject resData = (JSONObject) totalStockAccountList.get("data");
                String resDepositReceived = resData.get("resDepositReceived").toString();
                BigDecimal depositReceived = new BigDecimal(resDepositReceived);

                String accountName = accountInfo.get(resAccountNum);
                accountRepository.saveStockAccount(foundMember, resAccountNum, organization, businessType, depositReceived, accountName);

                //주식 정보 저장
                JSONArray resItemList = (JSONArray) resData.get("resItemList");
                for (Object item : resItemList) {
                    JSONObject stock = (JSONObject) item;
                    String resItemName = stock.get("resItemName").toString();
                    String resValuationPL = stock.get("resValuationPL").toString();
                    String resValuationAmt = stock.get("resValuationAmt").toString();
                    String resQuantity = stock.get("resQuantity").toString();
                    String resPurchaseAmount = stock.get("resPurchaseAmount").toString();
                    String resEarningsRate = stock.get("resEarningsRate").toString();
                    String resAccountCurrency = stock.get("resAccountCurrency").toString();

                    StockAccount foundStockAccount = accountRepository.findStockAccountByAccountNum(resAccountNum);
                    stockRepository.addOrUpdateStock(foundStockAccount, resItemName, resValuationPL, resValuationAmt, Long.parseLong(resQuantity), resPurchaseAmount, resEarningsRate, AccountCurrency.valueOf(resAccountCurrency));
                }
            }

        }


    }

    //은행 계좌정보 업데이트
    public void updateBankAccount(String token) throws IOException, ParseException, InterruptedException {

        String loginIdFromToken = jwtUtil.getLoginIdFromToken(token);
        Member foundMember = memberRepository.findByLoginId(loginIdFromToken);

        Set<String> bankOrganizationSet = accountRepository.findBankOrganizationSet(token);
        for (String organization : bankOrganizationSet) {
            JSONObject resAccounts = findOwnAccountList(organization, token);

            JSONObject resData = (JSONObject) resAccounts.get("data");

            //예금/신탁 계좌가 여러개일 경우
            if(resData.get("resDepositTrust") instanceof JSONArray) {
                JSONArray resDepositTrustList = (JSONArray) resData.get("resDepositTrust");

                for (Object depositTrust : resDepositTrustList) {
                    JSONObject jsonDepositTrust = (JSONObject) depositTrust;

                    String accountNum = jsonDepositTrust.get("resAccount").toString();
                    String resAccountCurrency = jsonDepositTrust.get("resAccountCurrency").toString();
                    String resAccountBalance = jsonDepositTrust.get("resAccountBalance").toString();

                    accountRepository.updateBankAccountByAccountNumber(resAccountBalance, accountNum, AccountCurrency.valueOf(resAccountCurrency));
                }
            } else if (resData.get("resDepositTrust") instanceof JSONObject) {  //계좌가 1개일 경우

                JSONObject jsonDepositTrust = (JSONObject) resData.get("resDepositTrust");

                String accountNum = jsonDepositTrust.get("resAccount").toString();
                String resAccountCurrency = jsonDepositTrust.get("resAccountCurrency").toString();
                String resAccountBalance = jsonDepositTrust.get("resAccountBalance").toString();

                accountRepository.updateBankAccountByAccountNumber(resAccountBalance, accountNum, AccountCurrency.valueOf(resAccountCurrency));
            }
        }
    }

    //은행 계좌정보 업데이트
    public void updateBankAccount(Member member) throws IOException, ParseException, InterruptedException {

        Set<String> bankOrganizationSet = accountRepository.findBankOrganizationSet(member);
        for (String organization : bankOrganizationSet) {
            System.out.println("---AccountService.findOwnAccountList 시작---");
            JSONObject resAccounts = findOwnAccountList(organization, member);
            System.out.println("---AccountService.findOwnAccountList 끝---");

            JSONObject resData = (JSONObject) resAccounts.get("data");

            //예금/신탁 계좌가 여러개일 경우
            if(resData.get("resDepositTrust") instanceof JSONArray) {
                JSONArray resDepositTrustList = (JSONArray) resData.get("resDepositTrust");

                List<BankAccountUpdate> bankAccountDTOs = new ArrayList<>();

                for (Object depositTrust : resDepositTrustList) {
                    JSONObject jsonDepositTrust = (JSONObject) depositTrust;

                    String accountNum = jsonDepositTrust.get("resAccount").toString();
                    String resAccountCurrency = jsonDepositTrust.get("resAccountCurrency").toString();
                    String resAccountBalance = jsonDepositTrust.get("resAccountBalance").toString();

                    bankAccountDTOs.add(new BankAccountUpdate(accountNum, resAccountBalance, resAccountCurrency));

                }

                accountRepository.updateBankAccountByAccountNumber(bankAccountDTOs);

            } else if (resData.get("resDepositTrust") instanceof JSONObject) {  //계좌가 1개일 경우

                JSONObject jsonDepositTrust = (JSONObject) resData.get("resDepositTrust");

                String accountNum = jsonDepositTrust.get("resAccount").toString();
                String resAccountCurrency = jsonDepositTrust.get("resAccountCurrency").toString();
                String resAccountBalance = jsonDepositTrust.get("resAccountBalance").toString();

                accountRepository.updateBankAccountByAccountNumber(resAccountBalance, accountNum, AccountCurrency.valueOf(resAccountCurrency));
            }
        }
    }

}
