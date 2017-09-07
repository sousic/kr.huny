package kr.huny.repository;

import kr.huny.model.db.Attachments;
import kr.huny.model.db.embedded.AttachmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachments, Long> {
    Attachments findByAttachSeqAndStatusEquals(long attachSeq, AttachmentStatus status);
}
