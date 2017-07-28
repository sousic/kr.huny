package kr.huny.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sousic on 2017-07-28.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "board")
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardseq")
    private Long boardSeq;

    @Column(length = 50)
    private String boardName;
    private String boardInfo;

    private Long articleCreateCount;
    private Long articleRemoveCount;

    //첨부파일수
    private Long articleAttechmentsCount;
    //이미지수
    private Long articleGalleriesCount;

    private byte isCommentType;
    private byte isRecommentType;

    @Temporal(TemporalType.TIMESTAMP)
    //@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regdate;
    private Date lastDateModify;
}
