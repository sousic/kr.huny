package kr.huny.authentication.cookie;

import kr.huny.common.crypto.AESImpl;
import kr.huny.common.crypto.ICrypto;
import kr.huny.common.serializer.IAuthSerializer;
import kr.huny.common.serializer.IAuthSerializerImpl;
import org.springframework.security.core.Authentication;
import org.springframework.util.Base64Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by sousic on 2017-05-24.
 */
public class CookieSecurityService {
    private static final String DEFAULT_COOKIE_PATH = "/";
    private final String cookieName;
    private final String cookePath;
    private final ICrypto iCrypto;
    private final IAuthSerializer iAuthSerializer;

    public CookieSecurityService(String cookieName, String encryptKey)
    {
        this.cookieName = cookieName;
        this.cookePath = DEFAULT_COOKIE_PATH;
        this.iCrypto = new AESImpl(encryptKey);
        this.iAuthSerializer = new IAuthSerializerImpl();
    }

    public CookieSecurityService(String cookieName, String encryptKey, String cookePath) {
        this.cookieName = cookieName;
        this.cookePath = cookePath;
        this.iCrypto = new AESImpl(encryptKey);
        this.iAuthSerializer = new IAuthSerializerImpl();
    }

    public Authentication getAuthenticationFrom(Cookie cookie)
    {
        byte[] decodedFromBase64 = Base64Utils.decodeFromUrlSafeString(cookie.getValue());
        byte[] decrpytAuthentication = iCrypto.decrypt(decodedFromBase64);

        return iAuthSerializer.read(decrpytAuthentication);
    }

    public Cookie setSecurityCookie(Authentication object)
    {
        byte[] serializedAuthentication = iAuthSerializer.write(object);
        byte[] encrpytAuthentication = iCrypto.encrypt(serializedAuthentication);
        String endcodedWithBase64 = Base64Utils.encodeToUrlSafeString(encrpytAuthentication);

        Cookie cookie = new Cookie(cookieName, endcodedWithBase64);
        cookie.setPath(cookePath);

        return cookie;
    }

    public Cookie getSecurityCookieForm(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public Cookie removeCookie()
    {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath(cookePath);
        cookie.setMaxAge(0);
        return cookie;
    }

    public boolean containsSecurityCookie(HttpServletRequest request)
    {
        return getSecurityCookieForm(request) != null;
    }
}
