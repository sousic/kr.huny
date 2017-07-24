package kr.huny.authentication.facebook;

import kr.huny.authentication.BasicPrincipal;
import kr.huny.authentication.common.OAuthDetailService;
import kr.huny.common.CommonConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

@Slf4j
public class FacebookAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	OAuthDetailService oAuthDetailService;

	private FacebookService facebookService;

	public void setFacebookService(FacebookService facebookService) { this.facebookService = facebookService; }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, "Only FacebookAuthenticationProvider is supported");
		
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		
		log.debug("username =>" + username);

		if(username.equals(CommonConst.SocialType.FACEBOOK.name().toLowerCase())) {
			FacebookUser facebookUser = facebookService.findUser();
			log.debug("facebookUser => " + facebookUser.toString());

			BasicPrincipal basicPrincipal = oAuthDetailService.loadUserId(facebookUser, CommonConst.SocialType.FACEBOOK);

			UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(basicPrincipal, authentication.getCredentials(), basicPrincipal.getAuthorities());

			token.setDetails(basicPrincipal);

			return token;
		} else {
			throw new BadCredentialsException("fail to authenticate Facebook");
		}
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
