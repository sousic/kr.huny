package kr.huny.model.db.common;

import lombok.Data;

/**
 * 기본 ajax 반환
 */
@Data
public class AjaxJsonCommon {
    public AjaxJsonCommon() {
        this.retMsg = null;
        this.retCode = -1;
    }
    private String retMsg;
    private int retCode;
}
