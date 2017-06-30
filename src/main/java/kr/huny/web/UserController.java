package kr.huny.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sousic on 2017-06-30.
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @RequestMapping
    public String root()
    {
        return "redirect:/user/list";
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        return "user/list";
    }
}
