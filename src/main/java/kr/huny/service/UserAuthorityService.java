package kr.huny.service;

import kr.huny.model.db.UserAuthority;
import kr.huny.repository.UserAuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sousic on 2017-07-05.
 */
@Service
@Slf4j
public class UserAuthorityService {
    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    public void save(UserAuthority userAuthority)
    {
        userAuthorityRepository.save(userAuthority);
    }

    public List<UserAuthority> findByUserSeq(Long seq)
    {
        return userAuthorityRepository.findByUserSeq(seq);
    }
}
