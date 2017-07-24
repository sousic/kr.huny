package kr.huny.authentication.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sousic on 2017-07-21.
 */
@Slf4j
public class OAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    protected OAuthProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public OAuthProcessingFilter() {
        super("/oauth/callback");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("facebook", null);
        SecurityContextHolder.getContext().setAuthentication(authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
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

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        if (!authentication.isAuthenticated() && (!httpRequest.getServletPath().equals("/oauth/callback") ||
                !httpRequest.getServletPath().equals("/oauth/write"))) {
            SecurityContextHolder.getContext().setAuthentication(null);

            if (log.isInfoEnabled()) {
                log.info("oauth was cancled. Authentication object was deleted.");
            }
        }

        super.doFilter(req, res, chain);
    }
}
