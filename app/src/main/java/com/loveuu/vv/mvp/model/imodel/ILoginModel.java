package com.loveuu.vv.mvp.model.imodel;

import com.loveuu.vv.mvp.model.ModelCallback;

/**
 * Created by VV on 2016/9/29.
 * 登录数据层接口
 */

public interface ILoginModel {
    <T> void toLogin(String account, String password, ModelCallback<T> callback);
}
