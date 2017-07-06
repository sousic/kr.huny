package kr.huny.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sousic on 2017-07-05.
 */
@Slf4j
public class BasicSuccessHandler implements AuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String targetUrlParameter;
    private String defaultUrl;
    private boolean useReferer;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public BasicSuccessHandler() {
        targetUrlParameter = "";
        defaultUrl = "/";
        useReferer = false;
    }

    public String getTargetUrlParameter() {
        return targetUrlParameter;
    }

    public void setTargetUrlParameter(String targetUrlParameter) {
        this.targetUrlParameter = targetUrlParameter;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public boolean isUseReferer() {
        return useReferer;
    }

    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(httpServletRequest);

        int intRedirectStrategy = decideRedirectStrategy(httpServletRequest, httpServletResponse);

        log.debug("intRedirectStrategy : " + intRedirectStrategy);


        switch (intRedirectStrategy) {
            case 1:
                useTargetUrl(httpServletRequest, httpServletResponse);
                break;
            case 2:
                useSessionUrl(httpServletRequest, httpServletResponse);
                break;
            case 3:
                useRefererUrl(httpServletRequest, httpServletResponse);
                break;
            default:
                useDefaultUrl(httpServletRequest, httpServletResponse);
                break;
        }
    }


    private void clearAuthenticationAttributes(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private void useTargetUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        if (savedRequest != null) {
            requestCache.removeRequest(httpServletRequest, httpServletResponse);
        }
        String targetUrl = httpServletRequest.getParameter(targetUrlParameter);
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);

    }

    private void useSessionUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String targetUrl = savedRequest.getRedirectUrl();
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
    }

    private void useRefererUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String targetUrl = httpServletRequest.getHeader("REFERER");
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
    }

    private void useDefaultUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, defaultUrl);
    }

    private int decideRedirectStrategy(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        int result = 0;

        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);

        if (!"".equals(targetUrlParameter)) {
            String targetUrl = httpServletRequest.getParameter(targetUrlParameter);
            if (StringUtils.hasText(targetUrl)) {
                result = 1;
            } else {
                if (savedRequest != null) {
                    result = 2;
                } else {
                    String refererUrl = httpServletRequest.getHeader("REFERER");
                    if (useReferer && StringUtils.hasText(refererUrl)) {
                        result = 3;
                    } else {
                        result = 0;
                    }
                }
            }

            return result;
        }

        if (savedRequest != null) {
            result = 2;
            return result;
        }

        String refererUrl = httpServletRequest.getHeader("REFERER");
        if (useReferer && StringUtils.hasText(refererUrl)) {
            result = 3;
        } else {
            result = 0;
        }

        return result;
    }
}
