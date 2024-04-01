package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String login_id;

    private String password;

    private String name;

    private String birthday;   //yymmdd 형식

    private String connected_id;

    @OneToMany(mappedBy = "member")
    private List<Account> accounts;

}
