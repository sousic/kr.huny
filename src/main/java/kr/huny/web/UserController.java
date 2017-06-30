package kr.huny.web;

import kr.huny.model.db.User;
import kr.huny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    UserService userService;

    @RequestMapping
    public String root()
    {
        return "redirect:/user/list";
    }


    @RequestMapping("/list")
    public String list(Model model, Pageable pageable)
    {
        Page<User> users = userService.findAll(pageable);

        model.addAttribute("users", users);

        return "user/list";
    }
}
