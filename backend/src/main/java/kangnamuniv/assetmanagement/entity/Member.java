package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "access_token_id")
    private AccessToken accessToken;

    private String login_id;

    private String password;

    private String name;

    private String connected_id;

}
