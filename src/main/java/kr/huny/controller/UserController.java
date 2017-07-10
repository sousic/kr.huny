package kr.huny.controller;

import kr.huny.common.CommonConst;
import kr.huny.facade.UserFacade;
import kr.huny.model.db.Authority;
import kr.huny.model.db.User;
import kr.huny.model.db.UserAuthority;
import kr.huny.model.db.web.UserRegister;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sousic on 2017-06-30.
 * 회원관리(가입, 수정 등등... )
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
    StandardPasswordEncoder standardPasswordEncoder;

    @Autowired
    UserFacade userFacade;

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
                .password(standardPasswordEncoder.encode("1111"))
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String userRegister()
    {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String userRegister(@Valid UserRegister userRegister, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            log.debug("[1]result => " + bindingResult);
            model.addAttribute(userRegister);
            return "user/register";
        }

        if(!userRegister.getPassword().equals(userRegister.getPasswordConfirm()))
        {
            bindingResult.reject("pwdConfirm");
            model.addAttribute("pwdConfirm", true);
            return "user/register";
        }

        //아이디 중복 체크
        User user;

        user = userService.findByEmail(userRegister.getEmail());
        if(!Objects.isNull(user))
        {
            bindingResult.reject("failEmail");
            model.addAttribute("failEmail", true);
            return "user/register";
        }

        //닉네임 중복 체크
        user = userService.findByUsername(userRegister.getUsername());
        if(!Objects.isNull(user))
        {
            bindingResult.reject("failUsername");
            model.addAttribute("failUsername", true);
            return "user/register";
        }

        user = User.builder().email(userRegister.getEmail())
                .password(standardPasswordEncoder.encode(userRegister.getPassword()))
                .username(userRegister.getUsername())
                .providerId(CommonConst.SocialType.BASIC)
                .about(userRegister.getAbout()).build();

        userFacade.SetUserRegist(user);

        return "redirect:/login";
    }
}
