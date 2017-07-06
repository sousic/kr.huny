package kr.huny.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.Collection;

/**
 * Created by sousic on 2017-07-05.
 */
@Slf4j
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    BasicDetailService basicDetailService;
    @Autowired
    StandardPasswordEncoder standardPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(authentication);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.debug("authentication => " + json);

        UserDetails user = null;
        Collection<? extends GrantedAuthority> authorities;
        try {
            user = basicDetailService.loadUserByUsername(username);

            if (standardPasswordEncoder.matches(password, user.getPassword()) == false)
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            authorities = user.getAuthorities();
        }
        catch(UsernameNotFoundException e)
        {
            log.info(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e)
        {
            log.info(e.toString());
            throw new BadCredentialsException(e.getMessage());
        }
        catch(InternalAuthenticationServiceException e)
        {
            log.info(e.toString());
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
        catch(Exception e)
        {
            log.info(e.toString());
            throw new RuntimeException(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
