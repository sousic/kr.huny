package kr.huny.blog.model.db;

import kr.huny.blog.common.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

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
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String email;

    private String password;

    private String username;

    private CommonConst.SocialType providerId; //계정 제공자

    private String providerUserId; // SNS USER ID

    private String about;

    private Date regDate;
}
