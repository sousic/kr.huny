package kr.huny.facade;

import kr.huny.common.CommonRole;
import kr.huny.model.db.User;
import kr.huny.model.db.UserAuthority;
import kr.huny.service.AuthorityService;
import kr.huny.service.UserAuthorityService;
import kr.huny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sousic on 2017-07-10.
 */
@Slf4j
@Component
public class UserFacadeImpl implements UserFacade {
    @Autowired
    UserService userService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    UserAuthorityService userAuthorityService;

    @Override
    public void SetUserRegist(User user) {
        log.debug("user => " + user.toString());

        userService.save(user);

        UserAuthority userAuthority = UserAuthority.builder().userSeq(user.getSeq()).authoritySeq(CommonRole.ROLE_NUMBER_USER).build();

        userAuthorityService.save(userAuthority);
    }
}
