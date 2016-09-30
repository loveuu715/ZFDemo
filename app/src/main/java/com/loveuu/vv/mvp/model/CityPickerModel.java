package com.loveuu.vv.mvp.model;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.api.core.ApiManager;
import com.loveuu.vv.mvp.BaseModel;
import com.loveuu.vv.mvp.model.imodel.ICityPickerModel;
import com.loveuu.vv.widget.citypicker.CityBean;

import java.util.List;
import java.util.Map;

/**
 * Created by VV on 2016/9/30.
 */

public class CityPickerModel extends BaseModel implements ICityPickerModel {

    @Override
    public <T> void getCities(final ModelCallback<T> modelCallback) {
        Map<String,String> params = ApiManager.getBasicMap(false);
        Api.getList(ApiFactory.INSTANCE.getListApi().getLBSCityList(params), CityBean.class, this, new ICallback<List<CityBean>>() {
            @Override
            public void onSuccess(List<CityBean> result) {
                modelCallback.onSuccess((T) result);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                modelCallback.onError(errorCode, errorMsg);
            }

            @Override
            public void onEmpty() {
                modelCallback.onEmpty();
            }

            @Override
            public void noNetworkError(String msg) {
                modelCallback.onNetworkError(msg);
            }
        });
    }
}
