package kr.huny.repository;

import kr.huny.model.db.BoardFree;
import kr.huny.model.db.Gallery;
import kr.huny.model.db.embedded.AttachmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Gallery findByGallerySeqAndStatusEquals(long gallerySeq, AttachmentStatus status);

    @Transactional
    @Modifying
    @Query("update Gallery g set g.boardFree = ?1, g.status = ?2 where g.gallerySeq = ?3")
    void updateBoardSeq(BoardFree boardFree, AttachmentStatus status, Long gallerySeq);
}
