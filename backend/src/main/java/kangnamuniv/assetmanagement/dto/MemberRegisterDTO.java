package kangnamuniv.assetmanagement.dto;

import kangnamuniv.assetmanagement.entity.Member;
import lombok.Getter;

@Getter
public class MemberRegisterDTO {

    private String login_id;

    private String password;

    private String name;

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
