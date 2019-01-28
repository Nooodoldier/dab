package com.bling.dab.common.httpclient;

/**
 * @author: hxp
 * @date: 2019/1/28 17:39
 * @description:
 */
public class HttpResult {


    private int code;

    private String body;


    public HttpResult(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
