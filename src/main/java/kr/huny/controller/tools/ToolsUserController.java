package kr.huny.controller.tools;

import kr.huny.model.db.User;
import kr.huny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
@Slf4j
@RequestMapping("/tools/user")
public class ToolsUserController {

    @RequestMapping
    public String root()
    {
        return "redirect:/list";
    }

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model)
    {
        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList("seq"));
        Pageable pageable = new PageRequest(0, 10, sort);

        Page<User> users = userService.findAll(pageable);

        model.addAttribute("users", users);

        return "tools/user/list";
    }
}
