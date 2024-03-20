package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String login_id;

    private String password;

    private String name;

    private int age;

    private String connected_id;

}
