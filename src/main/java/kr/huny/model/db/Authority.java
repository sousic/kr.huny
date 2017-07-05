package kr.huny.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by sousic on 2017-04-26.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "authority_seq")
public class Authority {
    @Id
    @Column(name="seq")
    private int authority_seq;

    @Column(length = 50)
    private  String authority_name;
}
