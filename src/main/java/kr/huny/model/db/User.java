package kr.huny.model.db;

import kr.huny.common.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sousic on 2017-06-28.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "uni_email")},
        indexes = {
            @Index(columnList = "username", name = "idx_00_username")
        }
)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String email;

    private String password;

    @Column(length = 50)
    private String username;

    private CommonConst.SocialType providerId; //계정 제공자

    private String providerUserId; // SNS USER ID

    @Column(columnDefinition = "text")
    private String about;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
}
