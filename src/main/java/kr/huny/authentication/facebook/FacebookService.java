package kr.huny.authentication.facebook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestOperations;

import java.util.Map;


@Slf4j
public class FacebookService {
	private String profileUri;
	private RestOperations facebookRestTemplate;

	public void setProfileUri(String profileUri) {
		this.profileUri = profileUri;
	} 
	public void setFacebookRestTemplate(RestOperations facebookRestTemplate) {
		this.facebookRestTemplate = facebookRestTemplate;
	}

	public FacebookUser findUser() {
		
		log.debug("profileUri => " + facebookRestTemplate.getForObject(profileUri, Map.class));
		FacebookUser facebookUser = 	facebookRestTemplate.getForObject(profileUri, FacebookUser.class);
		if(log.isInfoEnabled()) {
			log.info(facebookUser.toString());
		}
		return facebookUser;
	}

}
