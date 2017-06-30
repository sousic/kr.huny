package kr.huny.web;

import kr.huny.common.CommonConst;
import kr.huny.model.db.User;
import kr.huny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by sousic on 2017-06-30.
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ShaPasswordEncoder shaPasswordEncoder;

    @RequestMapping
    public String root()
    {
        return "redirect:/user/list";
    }

    /**
     * 회원 등록 테스트
     */
    @RequestMapping("/addTest")
    public void addTest()
    {
        User user = User.builder().email("test@test.com")
                .password(shaPasswordEncoder.encodePassword ("password", null))
                .providerId(CommonConst.SocialType.BASIC)
                .username("홍길동")
                .about("나야냐~~")
                .regDate(new Date()).build();

        userService.save(user);
    }

    @RequestMapping("/list")
    public String list(Model model, Pageable pageable)
    {
        Page<User> users = userService.findAll(pageable);

        model.addAttribute("users", users);

        return "user/list";
    }
}
