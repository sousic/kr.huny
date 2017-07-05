package kr.huny.authentication;

import kr.huny.Exception.FindUserButNotNormalAccount;
import kr.huny.Exception.NotFoundNormalAccountException;
import kr.huny.common.CommonConst;
import kr.huny.common.CommonRole;
import kr.huny.model.db.User;
import kr.huny.model.db.UserAuthority;
import kr.huny.repository.UserAuthorityRepository;
import kr.huny.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by sousic on 2017-07-05.
 * 기본 회원 로그인 처리
 */
@Slf4j
public class BasicDetailService implements UserDetailsManager {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(Objects.isNull(email)) {
            throw new IllegalArgumentException("email은 꼭 필요한 값입니다.");
        } else {
            User user = userRepository.findByEmail(email);

            if(Objects.isNull(user))
                throw new NotFoundNormalAccountException("로그인 할 사용자 데이터가 존재 하지 않습니다. email=" + email);

            if(user.getProviderId().equals(CommonConst.SocialType.BASIC) == false)
                throw new FindUserButNotNormalAccount("일반 계쩡이 아니라 SNS 계정으로 연동되어 있습니다. email=" + email, user.getProviderId());

            log.debug("user =>" + user);

            BasicPrincipal basicPrincipal = new BasicPrincipal(user.getSeq(), user.getEmail(),
                    user.getPassword(), user.getUsername(), user.getProviderId(),
                    user.getProviderUserId(), user.getAbout(), user.getRegDate(), getAuthorities(userAuthorityRepository.findByUser_Seq(user.getSeq())));

            log.info("basicPrincipal => " + basicPrincipal);

            return basicPrincipal;
        }
    }
    //public Collection<? extends GrantedAuthority> getAuthorities(List<Integer> roles) {
    public Collection<? extends GrantedAuthority> getAuthorities(List<UserAuthority> roles) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(roles));
        return authList;
    }

    public List<String> getRoles(List<UserAuthority> roles) {
        List<String> newRoles = new ArrayList<String>();

        if (roles != null) {
            for (UserAuthority userAuthority : roles) {
                String roleName = CommonRole.getRoleName(userAuthority.getId().intValue());
                if (!roleName.isEmpty()) {
                    newRoles.add(roleName);
                }
            }
        }

        return newRoles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }
}
