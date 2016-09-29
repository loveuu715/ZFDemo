package com.loveuu.vv.mvp.model.imodel;

import com.loveuu.vv.mvp.model.ModelCallback;

/**
 * Created by VV on 2016/9/29.
 */

public interface IHomeModel {
    <T> void getBannerList(String adcode, ModelCallback<T> modelCallback);
}
