package kr.huny.authentication.common;

import kr.huny.authentication.BasicPrincipal;
import kr.huny.authentication.facebook.FacebookUser;
import kr.huny.common.CommonConst;
import kr.huny.common.CommonRole;
import kr.huny.model.db.User;
import kr.huny.model.db.UserAuthority;
import kr.huny.repository.UserAuthorityRepository;
import kr.huny.repository.UserRepository;
import kr.huny.service.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by sousic on 2017-07-24.
 */
@Slf4j
public class OAuthDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    UserAuthorityService userAuthorityService;

    FacebookUser facebookUser;
    CommonConst.SocialType socialType;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            user = User.builder().email(facebookUser.getEmail())
                    .providerId(CommonConst.SocialType.FACEBOOK)
                    .username(facebookUser.getName())
                    .providerUserId(facebookUser.getId())
                    .regDate(new Date()).build();

            userRepository.save(user);

            UserAuthority userAuthority = UserAuthority.builder().userSeq(user.getSeq()).authoritySeq(CommonRole.ROLE_NUMBER_USER).build();

            userAuthorityService.save(userAuthority);
        }

        BasicPrincipal basicPrincipal = new BasicPrincipal(user.getSeq(), user.getEmail(),
                user.getPassword(), user.getUsername(), user.getProviderId(),
                user.getProviderUserId(), user.getAbout(), user.getRegDate(), getAuthorities(userAuthorityRepository.findByUserSeq(user.getSeq())));

        log.info("basicPrincipal => " + basicPrincipal.toString());

        if(basicPrincipal.getAuthorities().size() == 0)
            throw  new UsernameNotFoundException(String.format("User %s has no GrantedAuthority", user.getEmail()));

        return basicPrincipal;
    }

    //public Collection<? extends GrantedAuthority> getAuthorities(List<Integer> roles) {
    public Collection<? extends GrantedAuthority> getAuthorities(List<UserAuthority> roles) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(roles));
        return authList;
    }

    public BasicPrincipal loadUserId(FacebookUser facebookUser, CommonConst.SocialType socialType) {
        this.facebookUser = facebookUser;
        this.socialType = socialType;

        return (BasicPrincipal) loadUserByUsername(this.facebookUser.getEmail());
    }

    public List<String> getRoles(List<UserAuthority> roles) {
        List<String> newRoles = new ArrayList<String>();

        if (roles != null) {
            for (UserAuthority userAuthority : roles) {
                String roleName = CommonRole.getRoleName(userAuthority.getAuthoritySeq());
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
}
