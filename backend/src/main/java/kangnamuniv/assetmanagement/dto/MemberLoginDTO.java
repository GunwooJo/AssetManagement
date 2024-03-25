package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.Getter;

@Getter
public class MemberLoginDTO {

    @Email(message = "Email should be valid")
    @NotNull(message = "login_id cannot be null")
    private String login_id;

    @NotNull(message = "password cannot be null")
    private String password;

    public static Member toMember(MemberLoginDTO memberLoginDTO) {
        Member member = new Member();
        member.setLogin_id(memberLoginDTO.getLogin_id());
        member.setPassword(memberLoginDTO.getPassword());
        return member;
    }
}
