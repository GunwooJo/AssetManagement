package kangnamuniv.assetmanagement;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class AssetmanagementApplication {

//	public String getConnectedId() {
//        String urlPath = "https://development.codef.io/v1/account/create";
//
//        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
//        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//
//        HashMap<String, Object> accountMap2 = new HashMap<String, Object>();
//        accountMap2.put("countryCode",	"KR");
//        accountMap2.put("businessType",	"BK");
//        accountMap2.put("clientType",  	"P");
//        accountMap2.put("organization",	"0081");
//        accountMap2.put("loginType",  	"1");
//
//        String password2 = "";
//        /**	password RSA encrypt */
//        try {
//            accountMap2.put("password", RSAUtil.encryptRSA(password2, CommonConstant.PUBLIC_KEY));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchPaddingException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeyException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalBlockSizeException e) {
//            throw new RuntimeException(e);
//        } catch (BadPaddingException e) {
//            throw new RuntimeException(e);
//        }
//
//        accountMap2.put("id", "kanggi1997");
//        accountMap2.put("birthday",	"971101");
//        list.add(accountMap2);
//
//        bodyMap.put("accountList", list);
//
//        // CODEF API 호출
//        String result = null;
//        try {
//            result = ApiRequest.request(urlPath, bodyMap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("result = " + result);
//        return result;
//    }

    public static void showAccounts() {
        String urlPath = "https://development.codef.io/v1/kr/bank/p/account/account-list";

        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> accountMap2 = new HashMap<String, Object>();
        accountMap2.put("organization",	"0081");
        accountMap2.put("connectedId", "5cKBSS.ZkHtbRzHCJeS3Hh");
//        accountMap2.put("birthDate", "19971101");

        String result = null;
        try {
            result = ApiRequest.request(urlPath, accountMap2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("result = " + result);
    }
	public static void main(String[] args) {
		SpringApplication.run(AssetmanagementApplication.class, args);

//        showAccounts();


//		String token = null;
//		System.out.println("--acess_token 요청--");
//		try {
//			token = RequestToken.getToken("a7a4ef93-f8d5-4daa-ac0e-316364bb708d", "44f6fc59-36e2-4163-87f9-8cb9849038ad");
//			System.out.println("token = " + token);
//		} catch (IOException | InterruptedException | ParseException error) {
//			System.out.println("error = " + error);
//		}

//		try {
//			String token = RequestToken.getToken("a7a4ef93-f8d5-4daa-ac0e-316364bb708d", "44f6fc59-36e2-4163-87f9-8cb9849038ad");
//			System.out.println("token = " + token);
//		} catch (IOException | InterruptedException | ParseException error) {
//			System.out.println("error = " + error);
//		}
//
//		System.out.println("====Api 요청 날리기====");
//
//		HashMap<String, Object> bodyMap = new HashMap<>();
//		String encryptedPassword;
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//		HashMap<String, Object> bodyBodyMap = new HashMap<String, Object>();
//
//		try {
//			encryptedPassword = RSAUtil.encryptRSA("gunwoo1234", CommonConstant.PUBLIC_KEY);
//
//			bodyMap.put("countryCode", "KR");
//			bodyMap.put("businessType", "BK");
//			bodyMap.put("clientType", "P");
//			bodyMap.put("organization", "0081");
//			bodyMap.put("loginType", "1");
//			bodyMap.put("id", "kanggi1997");
//			bodyMap.put("password", encryptedPassword);
//			bodyMap.put("birthDate", "971101");
//			list.add(bodyMap);
//			bodyBodyMap.put("accountList", list);
//
//		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
//				InvalidKeyException | IllegalBlockSizeException | BadPaddingException error) {
//			error.printStackTrace();
//		}
//
//		try {
//			String result = ApiRequest.request("https://development.codef.io/v1/account/create", bodyBodyMap);
//			System.out.println("result = " + result); // Print or further process the response
//		} catch (IOException | InterruptedException | ParseException e) {
//			System.out.println("에러나는거임?");
//			e.printStackTrace(); // Handle potential exceptions here
//		}


	}

}
