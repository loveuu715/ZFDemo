package com.loveuu.vv.api;

/**
 * Created by VV on 2016/9/22.
 */

public class HttpExceptionBean {
    private String code;
    private String message;
    private String body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
