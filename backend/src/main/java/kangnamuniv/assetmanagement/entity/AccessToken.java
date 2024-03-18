package kangnamuniv.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_token_id")
    private Long id;

    private String token;

    private LocalDateTime created_at;

    private LocalDateTime expires_at;
}
