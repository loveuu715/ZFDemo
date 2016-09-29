package com.loveuu.vv.mvp.model;

/**
 * Created by VV on 2016/9/29.
 */

public interface ILoginModel {
    <T> void toLogin(String account, String password, ModelCallback<T> callback);
}
