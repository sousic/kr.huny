package kr.huny.authentication.facebook;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sousic on 2017-07-20.
 */
public class FacebookAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public FacebookAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public FacebookAuthenticationFilter() {
        super("/auth/facebook/connect");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("facebook", null);

        SecurityContextHolder.getContext().setAuthentication(authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof AccessTokenRequiredException
                || failed instanceof AccessTokenRequiredException) {
            // Need to force a redirect via the OAuth client filter, so rethrow
            // here
            throw failed;
        } else {
            // If the exception is not a Spring Security exception this will
            // result in a default error page
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }
}
