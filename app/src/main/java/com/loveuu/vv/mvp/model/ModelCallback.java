package com.loveuu.vv.mvp.model;

/**
 * Created by VV on 2016/9/29.
 */

public interface ModelCallback<T> {
    void onSuccess(T result);
    void onError(int errorCode, String errorMsg);
    void onEmpty();
    void noNetworkError(String msg);
}
