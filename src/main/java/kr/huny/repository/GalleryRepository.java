package kr.huny.repository;

import kr.huny.model.db.Gallery;
import kr.huny.model.db.embedded.AttachmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Gallery findByGallerySeqAndStatusEquals(long gallerySeq, AttachmentStatus status);

}
