package kr.huny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sousic on 2017-07-03.
 */
@Controller
@RequestMapping(value="/access")
public class AccessController {

    @RequestMapping(value="/login")
    public String login(Model model)
    {
        return "access/login";
    }


    @RequestMapping(value = "/logout")
    public String logout() {

        return "redirect:/";
    }
}
