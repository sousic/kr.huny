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
public class AttachmentSimple {
    private long fSeq;

    private String urlPath;

    private AttachmentStatus status;

    private String fileName;

    private long size;

    private String fileSize;
}
