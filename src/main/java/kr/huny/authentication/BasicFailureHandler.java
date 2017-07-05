package kr.huny.authentication;

import kr.huny.Exception.FindUserButNotNormalAccount;
import kr.huny.Exception.NotFoundNormalAccountException;
import kr.huny.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by sousic on 2017-07-05.
 */
public class BasicFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    CommonService commonService;

    @Resource
    LocaleResolver localeResolver;


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        String path = String.format("%s/", httpServletRequest.getContextPath());
        String loginRedirect = httpServletRequest.getParameter("loginRedirect");
        String message;

        Locale locale = localeResolver.resolveLocale(httpServletRequest);

        //인증 오류 처리
        if(exception instanceof InternalAuthenticationServiceException)
        {
            AuthenticationException customException = (AuthenticationException)exception.getCause();

            if(Objects.nonNull(customException)) {
                if(customException instanceof FindUserButNotNormalAccount) {
                    message = commonService.getResourceBudleMessage(locale, "message.user", "user.msg.notNormalAccount");
                } else if(customException instanceof NotFoundNormalAccountException) {
                    message = commonService.getResourceBudleMessage(locale, "message.user", "user.msg.loginNotFound");
                }
            } else {
                message = commonService.getResourceBudleMessage(locale, "message.common", "common.msg.loginfail");
            }

        } else if(exception instanceof BadCredentialsException) {
           message = commonService.getResourceBudleMessage(locale, "message.user", "user.msg.loingFailPassword");
        } else
        {
            message = commonService.getResourceBudleMessage(locale, "message.common", "common.msg.loginfail");
        }
    }
}