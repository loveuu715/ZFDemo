package com.loveuu.vv.mvp.model;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.api.core.ApiManager;
import com.loveuu.vv.bean.HomeLastShareBean;
import com.loveuu.vv.mvp.model.imodel.IHomeModel;
import com.loveuu.vv.utils.LogUtil;
import com.mevv.library.CarouselView;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.loveuu.vv.api.core.ApiManager.API_TAG;

/**
 * Created by VV on 2016/9/29.
 */

public class HomeModel implements IHomeModel {
    @Override
    public <T> void getBannerList(String adcode, final ModelCallback<T> modelCallback) {
        Map<String ,String> params = ApiManager.getBasicMap(false);
        params.put("adcode", adcode);
        //打印MAP
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            LogUtil.d(API_TAG, "- - - 基本请求参数 ====>" + entry.getKey() + " : " + entry.getValue());
        }
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

    @Override
    public <T> void getShareList(final ModelCallback<T> modelCallback) {
        Map<String ,String> params = ApiManager.getBasicMap(true);
        Api.getList(ApiFactory.INSTANCE.getListApi().getShareList(params), HomeLastShareBean.class, this, new ICallback<List<HomeLastShareBean>>() {
            @Override
            public void onSuccess(List<HomeLastShareBean> result) {
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
