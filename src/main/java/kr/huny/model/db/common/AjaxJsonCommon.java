package kr.huny.model.db.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 기본 ajax 반환
 */
@Data
public class AjaxJsonCommon<T> {
    public AjaxJsonCommon() {
        this.retMsg = null;
        this.retCode = -1;

        this.result = new ArrayList<T>();
    }
    private String retMsg;
    private int retCode;

    private List<T> result;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public void addResultItem(T t)
    {
        this.result.add(t);
    }
}
