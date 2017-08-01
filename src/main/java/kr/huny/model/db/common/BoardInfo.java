package kr.huny.model.db.common;

import lombok.Data;

/**
 * Created by sousic on 2017-08-01.
 */
@Data
public class BoardInfo {
    public BoardInfo() {
        page = 1;
        size = 10;
        category = "";
    }

    private int page;
    private int size;
    private String category;
}
