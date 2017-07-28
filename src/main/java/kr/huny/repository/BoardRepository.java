package kr.huny.repository;

import kr.huny.model.db.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sousic on 2017-07-28.
 */
public interface BoardRepository extends JpaRepository<Board, Long> {

}
