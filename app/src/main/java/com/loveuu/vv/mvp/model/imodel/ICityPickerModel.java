package com.loveuu.vv.mvp.model.imodel;

import com.loveuu.vv.mvp.model.ModelCallback;

/**
 * Created by VV on 2016/9/30.
 */

public interface ICityPickerModel {
    <T> void getCities(ModelCallback<T> modelCallback);
}
