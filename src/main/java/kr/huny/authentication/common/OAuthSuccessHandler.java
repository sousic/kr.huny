package kr.huny.authentication.common;

import kr.huny.authentication.BasicPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String loginRedirect = request.getParameter("loginRedirect");
		
		if (authentication.getPrincipal() instanceof BasicPrincipal) {
			BasicPrincipal principal = (BasicPrincipal) authentication.getPrincipal();
			//String addInfoStatus = principal.getAddInfoStatus();

			//log.debug("addInfoStatus => " + addInfoStatus);

			/*if (addInfoStatus.equals(CommonConst.OAUTH_ADDITIONAL_INFO_STATUS_BLANK)) {
				if (log.isDebugEnabled()) {
					log.debug("Didn't input your additional infomation. Redrict input form.");
				}
				
				String targetUrl = "/oauth/write";
				getRedirectStrategy().sendRedirect(request, response, targetUrl);
				return;
			} */
			/*String targetUrl = "/oauth/write";
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;*/
		}
		
		clearAuthenticationAttributes(request);
		
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
			}
			
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;
		}
		
		if (loginRedirect != null) {
			log.debug("loginRedirect => " + loginRedirect);
			/*if (commonService.isRedirectUrl(loginRedirect)) {
				if (log.isDebugEnabled()) {
					log.debug("Redirecting to this Url: " + loginRedirect);
				}
				getRedirectStrategy().sendRedirect(request, response, loginRedirect);
				return;
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Don't redirect to this Url" + loginRedirect);
				}
			}*/
		}
		
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}        
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

}
