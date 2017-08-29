package kr.huny.model.db.web;

import kr.huny.model.db.embedded.GalleryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GallerySimple {
    private long gallerySeq;

    private String urlPath;

    private GalleryStatus status;
}
