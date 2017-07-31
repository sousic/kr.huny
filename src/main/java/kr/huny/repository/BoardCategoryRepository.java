package kr.huny.repository;

import kr.huny.model.db.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sousic on 2017-07-31.
 */
public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
}
