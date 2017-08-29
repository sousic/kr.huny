package kr.huny.model.db;

import kr.huny.model.db.embedded.GalleryStatus;
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
@Table(name="gallery")
public class Gallery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gseq")
    private long gallerySeq;

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

    private GalleryStatus status = GalleryStatus.QUEUE;

    private int views = 0;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regdate;

    private int downloads = 0;
}