package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.util.ApiRequest;
import kangnamuniv.assetmanagement.util.CommonConstant;
import kangnamuniv.assetmanagement.util.RSAUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {


    //계정을 등록하여 connectedId 발급.
    public String addAccount(String businessType, String loginType, String organization, String id, String password, String birthday) throws Exception{
        System.out.println("커넥티드 아이디 없는 addAccount 호출");
        String urlPath = "https://development.codef.io/v1/account/create";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	"P");
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);
        accountMap.put("birthday", birthday);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);

        // CODEF API 호출
        String connectedId = null;
        JSONParser parser = new JSONParser();

        try {
            String response = ApiRequest.request(urlPath, bodyMap);
            JSONObject jsonResponse = (JSONObject) parser.parse(response);

            //추후 수정 필요: 에러메시지의 유무에 따라 동작하도록.
            if(jsonResponse.containsKey("data")) {
                JSONObject data = (JSONObject)jsonResponse.get("data");
                connectedId = data.get("connectedId").toString();
                return connectedId;
            } else {
                throw new Exception("connectedId 속성을 찾을 수 없음.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return connectedId;
        }

    }

    //계정 최초 등록 후 발급된 connectedId로 기관을 추가로 등록하고 싶을 때 사용.
    public void addAccount(String businessType, String loginType, String organization, String id, String password, String birthday, String connectedId) throws Exception{
        System.out.println("커넥티드 아이디 넘어온 addAccount 호출");
        String urlPath = "https://development.codef.io/v1/account/add";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap = new HashMap<String, Object>();
        accountMap.put("countryCode",	"KR");
        accountMap.put("businessType",	businessType);
        accountMap.put("clientType",  	"P");
        accountMap.put("organization",	organization);
        accountMap.put("loginType",  	loginType);
        accountMap.put("id", id);
        accountMap.put("birthday", birthday);

        /**	password RSA encrypt */
        try {
            accountMap.put("password", RSAUtil.encryptRSA(password, CommonConstant.PUBLIC_KEY));
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);
        bodyMap.put("connectedId", connectedId);

        // CODEF API 호출
        JSONParser parser = new JSONParser();

        try {
            String response = ApiRequest.request(urlPath, bodyMap);
            JSONObject jsonResponse = (JSONObject) parser.parse(response);
            JSONObject resResult = (JSONObject) jsonResponse.get("result");
            String resMessage = resResult.get("message").toString();

            //다른 resMessage 나오며 처리가 불가능 한 경우는 없는지 살펴봐야함.
            if(resMessage.equals("사용자 계정정보 등록에 실패했습니다.")) {
                JSONObject resData = (JSONObject) jsonResponse.get("data");
                JSONArray resErrorList = (JSONArray) resData.get("errorList");
                JSONObject jsonObj = (JSONObject) resErrorList.get(0);
                System.out.println("jsonObj = " + jsonObj);
                String errorMessage = jsonObj.get("message").toString();
                throw new Exception(errorMessage);
            }

        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

    }
}
