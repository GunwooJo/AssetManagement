package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AccountCheckDTO {

    @NotBlank
    private String organization;    // 기관코드

    @NotBlank
    private String birthday;    //yymmdd    [아이디 로그인] 추가정보

    private String withdrawAccountNo;   //	대구은행 [아이디 로그인] 추가정보

    private String withdrawAccountPassword;    //	대구은행 [아이디 로그인] 추가정보, RSA암호화된 비밀번호
}
