package kr.huny.repository;

import kr.huny.model.db.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sousic on 2017-07-05.
 */
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    List<UserAuthority> findByUserSeq(Long seq);
}
