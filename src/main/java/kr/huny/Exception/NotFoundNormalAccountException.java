package kr.huny.Exception;

import lombok.Getter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Created by sousic on 2017-07-05.
 * 가입 회원 없음
 */
@Getter
public class NotFoundNormalAccountException extends InternalAuthenticationServiceException {


    public NotFoundNormalAccountException(String message) {
        super(message);
    }
}
