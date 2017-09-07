package kr.huny.model.db;

import kr.huny.model.db.embedded.AttachmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="attchments", indexes = {
        @Index(name="gallery_idx_00_status", columnList = "status" )
})
/**
 * 첨부파일
 */
public class Attachments {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="aseq")
    private long attachSeq;

    @ManyToOne
    @JoinColumn(name = "board_seq")
    //private long boardSeq;
    private BoardFree boardFree;

    @Column(name="user_seq")
    private long userSeq;

    @Column(length = 50)
    private String username;

    //실제 파일명
    private String fileName;
    //저장파일명
    private String saveName;

    private String savePath;

    private long size;

    private String contentType;

    private AttachmentStatus status = AttachmentStatus.QUEUE;

    private int views = 0;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regdate;

    private int downloads = 0;
}
