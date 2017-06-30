package kr.huny.service;

import kr.huny.model.db.User;
import kr.huny.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sousic on 2017-06-30.
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * 사용자 등록
     * @param user
     */
    public void save(User user)
    {
        userRepository.save(user);
    }

    /**
     * 사용자 목록 - 페이징
     * @param pageable
     * @return
     */
    public Page<User> findAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }
}
