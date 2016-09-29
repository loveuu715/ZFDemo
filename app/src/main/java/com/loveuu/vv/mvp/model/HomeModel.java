package com.loveuu.vv.mvp.model;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.api.core.ApiManager;
import com.loveuu.vv.mvp.model.imodel.IHomeModel;
import com.mevv.library.CarouselView;

import java.util.List;
import java.util.Map;

/**
 * Created by VV on 2016/9/29.
 */

public class HomeModel implements IHomeModel {
    @Override
    public <T> void getBannerList(String adcode, final ModelCallback<T> modelCallback) {
        Map<String ,String> params = ApiManager.getBasicMap(false);
        params.put("adcode", adcode);
        Api.getList(ApiFactory.INSTANCE.getListApi().getBannerList(params), CarouselView.BannerInfo.class, this, new ICallback<List<CarouselView.BannerInfo>>() {
            @Override
            public void onSuccess(List<CarouselView.BannerInfo> result) {
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
