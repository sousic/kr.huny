package kr.huny.common.serializer;

import org.springframework.security.core.Authentication;

/**
 * Created by sousic on 2017-05-24.
 */
public interface IAuthSerializer {
    byte[] write(final Authentication object);
    Authentication read(byte[] data);
}
