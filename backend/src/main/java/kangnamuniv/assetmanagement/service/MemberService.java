package kangnamuniv.assetmanagement.service;

import kangnamuniv.assetmanagement.util.ApiRequest;
import kangnamuniv.assetmanagement.util.CommonConstant;
import kangnamuniv.assetmanagement.util.RSAUtil;
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
public class MemberService {

    public String generateConnectedId(String businessType, String loginType, String organization, String id, String password, String birthday) {
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
        } catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        list.add(accountMap);

        bodyMap.put("accountList", list);

        // CODEF API 호출
        String result = null;

        try {
            result = ApiRequest.request(urlPath, bodyMap);
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }
}
