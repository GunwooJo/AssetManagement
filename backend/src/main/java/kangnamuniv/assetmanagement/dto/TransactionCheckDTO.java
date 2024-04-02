package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TransactionCheckDTO {

    @NotBlank
    private String organization;    // 기관코드

    @NotBlank
    private String birthday;    //yymmdd    [아이디 로그인] 추가정보

    @NotBlank
    private String account;    // 계좌번호

    @NotBlank
    private String startDate;    //시작일자 yyyymmdd

    @NotBlank
    private String endDate;    //종료일자 yyyymmdd

    @NotBlank
    private String orderBy;    //일자정렬순서 	"0" : 최신순, "1": 과거순 (default: "0")

    private String withdrawAccountNo;   //	출금계좌번호 대구은행 [아이디 로그인] 추가정보

    private String withdrawAccountPassword;    //	출금계좌비밀번호 대구은행 [아이디 로그인] 추가정보, RSA암호화된 비밀번호(암호화는 백엔드에서 하고 프론트에서 순수 비번 넘기자)

    private String inquiryType; //  조회구분 [계좌상세포함여부] "0":미포함, "1":포함 ( default: "1")

    private String accountPassword; //계좌비밀번호    RSA암호화 백엔드에서 하자.

}
