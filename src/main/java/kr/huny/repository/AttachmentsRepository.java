package kr.huny.repository;

import kr.huny.model.db.Attachments;
import kr.huny.model.db.BoardFree;
import kr.huny.model.db.embedded.AttachmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AttachmentsRepository extends JpaRepository<Attachments, Long> {
    Attachments findByAttachSeqAndStatusEquals(long attachSeq, AttachmentStatus status);

    @Transactional
    @Modifying
    @Query("update Attachments a set a.boardFree = ?1, a.status = ?2 where a.attachSeq = ?3")
    void updateBoardSeq(BoardFree boardFree, AttachmentStatus status, Long attachSeq);

    List<Attachments> findByBoardFreeBoardSeq(long boardSeq);
}
