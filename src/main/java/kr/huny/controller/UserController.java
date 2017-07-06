package kr.huny.controller;

import kr.huny.common.CommonConst;
import kr.huny.model.db.Authority;
import kr.huny.model.db.User;
import kr.huny.model.db.UserAuthority;
import kr.huny.service.AuthorityService;
import kr.huny.service.UserAuthorityService;
import kr.huny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
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
    AuthorityService authorityService;
    @Autowired
    UserAuthorityService userAuthorityService;

    @Autowired
    //ShaPasswordEncoder shaPasswordEncoder;
    StandardPasswordEncoder standardPasswordEncoder;

    @RequestMapping
    public String root()
    {
        return "redirect:/user/list";
    }

    /**
     * 회원 등록 테스트
     */
    @RequestMapping(value = "/addTest", method = RequestMethod.GET)
    public void addTest()
    {
        User user = User.builder().email("test@test.com")
                //.password(shaPasswordEncoder.encodePassword ("password", null))
                .password(standardPasswordEncoder.encode("password"))
                .providerId(CommonConst.SocialType.BASIC)
                .username("홍길동")
                .about("나야냐~~")
                .regDate(new Date()).build();

        userService.save(user);

        //권한 추가
        Authority authority = Authority.builder().authority_seq(255).authority_name("슈퍼관리자").build();
        authorityService.save(authority);

        //권한 매핑
        UserAuthority userAuthority = UserAuthority.builder().userSeq(user.getSeq()).authoritySeq(authority.getAuthority_seq()).build();
        userAuthorityService.save(userAuthority);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model)
    {
        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList("seq"));
        Pageable pageable = new PageRequest(0, 10, sort);

        Page<User> users = userService.findAll(pageable);

        model.addAttribute("users", users);

        return "user/list";
    }
}
