package kr.huny.repository;

import kr.huny.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sousic on 2017-06-30.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
