package kr.huny.repository;

import kr.huny.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sousic on 2017-06-30.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     *  이메일 조회
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 닉네임 조회
     * @param username
     * @return
     */
    User findByUsername(String username);
}
