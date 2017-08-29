package kr.huny.authentication.common;

import kr.huny.common.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonPrincipal {
    private long seq;

    private String email;

    private String username;

    private CommonConst.SocialType providerId; //계정 제공자

}
