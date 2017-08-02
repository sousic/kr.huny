package kr.huny.model.db.common;

import lombok.Builder;
import lombok.Data;

/**
 * Created by sousic on 2017-08-02.
 * 페이징 정보
 */
@Data
@Builder
public class PageNaviInfo {
    private int totalPage;
    private int currentPage;
    private int totalRowCount;
    private int size;
}
