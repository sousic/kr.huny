package kr.huny.model.db.web.response;

import kr.huny.model.db.embedded.AttachmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GallerySimple {
    private long fSeq;

    private String urlPath;

    private AttachmentStatus status;
}
