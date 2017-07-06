package kr.huny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by sousic on 2017-07-03.
 */
@Controller
public class AccessController {

    @RequestMapping(value="/login")
    public String login(@RequestParam(required = false) String result,
                        @RequestParam(required = false) String message,
                        @RequestParam(required = false) String loginRedirect,
                        Model model)
    {
        if(StringUtils.isEmpty(loginRedirect) == false)
        {
            model.addAttribute("loginRedirect", loginRedirect);
        }

        if(StringUtils.isEmpty(message) == false)
        {
            model.addAttribute("message", message);
        }

        model.addAttribute("result", result);

        return "access/login";
    }


    @RequestMapping(value = "/access/logout")
    public String logout() {

        return "redirect:/";
    }
}
