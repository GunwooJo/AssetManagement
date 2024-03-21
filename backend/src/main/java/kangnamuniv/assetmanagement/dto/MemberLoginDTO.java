package kangnamuniv.assetmanagement.dto;

import kangnamuniv.assetmanagement.entity.Member;
import lombok.Getter;

@Getter
public class MemberLoginDTO {
    private String login_id;
    private String password;

    public static Member toMember(MemberLoginDTO memberLoginDTO) {
        Member member = new Member();
        member.setLogin_id(memberLoginDTO.getLogin_id());
        member.setPassword(memberLoginDTO.getPassword());
        return member;
    }
}
