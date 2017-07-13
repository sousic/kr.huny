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

    @RequestMapping(value = "/auth/facebook/callback")
    public void auth_callback(@RequestParam("code") String code)
    {
        log.debug("code => " + code);

    }

    @RequestMapping(value = "/auth/facebook/connect")
    public String facebook_connect() throws IOException {

        return String.format("redirect:https://www.facebook.com/dialog/oauth?app_id=%s&redirect_uri=http://huny.kr:8080/auth/facebook/callback", applicationPropertyConfig.getApp_id());
    }
}
