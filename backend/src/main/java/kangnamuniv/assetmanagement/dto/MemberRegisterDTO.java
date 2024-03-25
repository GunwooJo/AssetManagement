package kangnamuniv.assetmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.Getter;

@Getter
public class MemberRegisterDTO {

    @Email(message = "Email should be valid")
    @NotNull(message = "login_id cannot be null")
    private String login_id;

    @NotNull(message = "password cannot be null")
    private String password;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "age cannot be null")
    private int age;

    public static Member toMember(MemberRegisterDTO memberRegisterDTO) {
        Member member = new Member();
        member.setLogin_id(memberRegisterDTO.login_id);
        member.setPassword(memberRegisterDTO.getPassword());
        member.setName(memberRegisterDTO.getName());
        member.setAge(memberRegisterDTO.getAge());
        return member;
    }
}
