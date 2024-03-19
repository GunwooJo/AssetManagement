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
	}

}
