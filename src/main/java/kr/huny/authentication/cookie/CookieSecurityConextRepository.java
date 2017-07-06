package kr.huny.authentication.cookie;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sousic on 2017-05-24.
 */
public class CookieSecurityConextRepository implements SecurityContextRepository {
    private final CookieSecurityService cookieSecurityService;

    public CookieSecurityConextRepository(CookieSecurityService cookieSecurityService) {
        this.cookieSecurityService = cookieSecurityService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest request = httpRequestResponseHolder.getRequest();

        SaveToSessionResponseWrapper responseWrapper = new SaveToSessionResponseWrapper(httpRequestResponseHolder.getResponse(), true);
        httpRequestResponseHolder.setResponse(responseWrapper);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        if(request.getCookies() != null)
        {
            Cookie cookie = cookieSecurityService.getSecurityCookieForm(request);
            if(cookie != null)
            {
                Authentication authentication = cookieSecurityService.getAuthenticationFrom(cookie);
                //인증정보가 없으면 쿠키 초기화
                if(authentication == null)
                {
                    Cookie tmpCookie = cookieSecurityService.removeCookie();
                    httpRequestResponseHolder.getResponse().addCookie(tmpCookie);
                }

                context.setAuthentication(authentication);
            }
        }

        return context;
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SaveToSessionResponseWrapper responseWrapper = (SaveToSessionResponseWrapper)httpServletResponse;
        if(!responseWrapper.isContextSaved()) {
            responseWrapper.saveContext(securityContext);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return cookieSecurityService.containsSecurityCookie(httpServletRequest);
    }

    final class SaveToSessionResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {

        public SaveToSessionResponseWrapper(HttpServletResponse response, boolean disableUrlRewriting) {
            super(response, disableUrlRewriting);
        }

        @Override
        protected void saveContext(SecurityContext securityContext) {
            Cookie cookie = cookieSecurityService.setSecurityCookie(securityContext.getAuthentication());
            if(cookie != null)
            {
                if(!this.isContextSaved())
                {
                    addCookie(cookie);
                }
            } else {
                addCookie(cookie);
                //addCookie(cookieSecurityService.removeCookie());
            }
        }
    }
}
