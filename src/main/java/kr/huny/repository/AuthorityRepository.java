package kr.huny.repository;

import kr.huny.model.db.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sousic on 2017-07-05.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
