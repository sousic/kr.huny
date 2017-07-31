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
@Table(name = "board_Free")
public class BoardFree {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_seq")
    private Long boardSeq;

    @Column(name="user_seq")
    private Long userSeq;

    @Column(length = 50)
    private String username;

    /*@Column(name = "category_seq")
    private Long catecorySeq;
    @Column(name = "category_name")
    private String categoryName;*/
    @ManyToOne(optional = false)
    @JoinColumn(name = "cate_seq")
    private BoardCategory boardCategory;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    private int attachmentCount;
    private int galleryCount;
    private int commentCount;
    private int recommendCount;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regdate;
    private Date lastDateModify;
}
