package kr.huny.Exception;

import kr.huny.common.CommonConst;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Created by sousic on 2017-07-05.
 * 회원계정은 있으나 SNS 계정임
 */
public class FindUserButNotNormalAccount extends InternalAuthenticationServiceException {
    private CommonConst.SocialType providerId;

    public FindUserButNotNormalAccount(String message, CommonConst.SocialType providerId) {
        super(message);
        this.providerId = providerId;
    }
}
