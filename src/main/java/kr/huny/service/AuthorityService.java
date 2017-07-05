package kr.huny.service;

import kr.huny.model.db.Authority;
import kr.huny.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sousic on 2017-07-05.
 */
@Service
@Slf4j
public class AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    public void save(Authority authority)
    {
        authorityRepository.save(authority);
    }
}
