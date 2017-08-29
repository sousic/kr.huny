package kr.huny.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by sousic on 2017-04-26.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_authority", indexes = {
    @Index(name = "user_authority_idx_00_user_no", columnList = "user_no")
})
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long id;

    @Column(name = "user_no", nullable = false)
    private long userSeq;

    @Column(name = "authority_no", nullable = false)
    private int authoritySeq;
}
