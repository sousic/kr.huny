package kr.huny.model.db.common;

import lombok.Data;
/**
 * Created by sousic on 2017-08-02.
 */
@Data
public class BoardPageInfo<T> {
    PageNaviInfo pageNaviInfo;
    private T list;
}
