package kr.huny.model.db.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sousic on 2017-08-02.
 * 페이징 정보
 */
@Builder
public class PageNaviInfo {
    @Setter
    private int totalPage;

    public int getTotalPage() {
        if(totalPage == 0)
            return 1;
        else
            return totalPage;
    }

    @Getter @Setter
    private int currentPage;
    @Getter @Setter
    private int totalRowCount;
    @Getter @Setter
    private int size;
}
