package com.loveuu.vv.api;

/**
 * Created by VV on 2016/9/22.
 */

public interface ICallback<T> {

    void onSuccess(T result);

    void onError(int errorCode, String errorMsg);

    void onEmpty();

}
