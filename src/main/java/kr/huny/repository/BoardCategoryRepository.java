package kr.huny.repository;

import kr.huny.model.db.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by sousic on 2017-07-31.
 */
public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
    BoardCategory findByRestName(String restName);

    List<BoardCategory> findByUsedTrue();

    @Transactional
    @Modifying
    @Query("update BoardCategory c set c.createCount = c.createCount+1 where c.categorySeq = ?1")
    void updateCategoryCreateCount(Long categorySeq);
}
