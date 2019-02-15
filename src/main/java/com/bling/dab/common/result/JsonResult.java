package com.bling.dab.common.result;

/**
 * @author: hxp
 * @date: 2019/2/15 19:46
 * @description:
 */
public class JsonResult {
    private String status;

    private Object result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "status='" + status + '\'' +
                ", result=" + result +
                '}';
    }
}
