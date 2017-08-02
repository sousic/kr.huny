package kr.huny.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sousic on 2017-07-31.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "board_Category")
public class BoardCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_seq")
    private Long categorySeq;

    private String categoryName;

    private String restName;

    private int createCount;

    private int removeCount;

    private boolean isUsed;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regdate;
}