package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AccountRequestDTO {

    @NotBlank
    private String businessType;    // 은행, 저축은행 : BK, 카드 : CD , 증권 : ST ,보험 : IS

    @NotBlank
    private String loginType;   // 0 : 인증서, 1 : 아이디/패스워드

    @NotBlank
    private String organization;    // 기관코드

    @NotBlank
    private String clientType;  //고객구분 개인: P(은행, 저축은행, 카드), 통합: A(증권, 보험)

    @NotBlank
    private String birthday;    //yymmdd

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    private String connected_id;
}
