package com.loveuu.vv.bean;

/**
 * Created by VV on 2016/9/22.
 */

public class BaseResponse<T> {
    /**
     * status : true
     * info : success
     * data :
     */

    private boolean status;
    private String info;
    private int errcode;
    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
