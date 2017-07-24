package kr.huny.controller;

import kr.huny.configuration.ApplicationPropertyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by sousic on 2017-07-03.
 * 로그인 처리만 함
 */
@Slf4j
@Controller
public class AccessController {
    @Autowired
    ApplicationPropertyConfig applicationPropertyConfig;

    @RequestMapping(value="/login")
    public String login(@RequestParam(required = false) String result,
                        @RequestParam(required = false) String message,
                        @RequestParam(required = false) String loginID,
                        Model model)
    {
        if(StringUtils.isEmpty(message) == false)
        {
            model.addAttribute("message", message);
        }

        model.addAttribute("result", result);
        model.addAttribute("loginID", loginID);

        return "access/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model)
    {
        return "access/login";
    }


    @RequestMapping(value = "/access/logout")
    public String logout() {

        return "redirect:/";
    }

    @RequestMapping(value = "/access/denied")
    public String denied() {
        return "access/denied";
    }


    @RequestMapping(value = "/auth/facebook/connect")
    public String facebook_connect() throws IOException {

        return String.format("redirect:https://www.facebook.com/dialog/oauth?app_id=%s&redirect_uri=http://huny.kr:8080/auth/facebook/callback", applicationPropertyConfig.getApp_id());
    }

    /*@RequestMapping(value = "/auth/facebook/callback")
    public String auth_callback(@RequestParam("code") String code)
    {
        log.debug("code => " + code);

        //토큰 추출
        String token_url = String.format("https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&code=%s&redirect_uri=%s",
                applicationPropertyConfig.getApp_id(),
                applicationPropertyConfig.getApp_secret(),
                code,
                "http://huny.kr:8080/auth/facebook/callback"
                );

        //log.debug("token_url => " + token_url);

        RestTemplate restTemplate = new RestTemplate();

        Map accessToken = null;
        Map<String, String> accessTokens = new HashMap<String, String>();

        accessTokens = (Map<String, String>)restTemplate.getForObject(token_url, Map.class);

        log.debug("access_token =>" + accessTokens.get("access_token"));

        //이메일,이름 추출
        //https://www.facebook.com/dialog/oauth?app_id=&redirect_uri=http://huny.kr:8080/facebook/callback
        //return "access/login";
        String profile_url = String.format("https://graph.facebook.com/me?fields=id,email,last_name,first_name&access_token=%s",accessTokens.get("access_token"));

        Map<String, String> profileMap = new HashMap<String, String>();

        profileMap = (Map<String, String>)restTemplate.getForObject(profile_url, Map.class);

        log.debug("access_token =>" + profileMap.toString());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("facebook", null,null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return "redirect:/";

    }
*/

    /*@RequestMapping(value = "/auth/facebook/callback02")
    public void auth_callback02(Model model) {

    }*/

}
