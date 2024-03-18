package kangnamuniv.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
public class MemberRequestDTO {

    private String businessType;
    private String loginType;
    private String organization;
}
