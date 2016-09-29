package com.loveuu.vv.mvp.presenter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loveuu.vv.BaseApplication;
import com.loveuu.vv.mvp.contract.HomeContract;
import com.loveuu.vv.mvp.model.HomeModel;
import com.loveuu.vv.mvp.model.ModelCallback;
import com.mevv.library.CarouselView;

import java.util.List;

/**
 * Created by VV on 2016/9/29.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private final HomeModel mHomeModel;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mHomeModel = new HomeModel();
    }

    @Override
    public void getBannerList(String adcode) {
        if (TextUtils.isEmpty(adcode)) {
            mView.networkError("adcode不能为空");
            return;
        }
        mHomeModel.getBannerList(adcode, new ModelCallback<List<CarouselView.BannerInfo>>() {
            @Override
            public void onSuccess(List<CarouselView.BannerInfo> result) {
                mView.hideProgress();
                mView.showBanner(result);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                mView.hideProgress();
                mView.onError(errorCode, errorMsg);
            }

            @Override
            public void onEmpty() {
                mView.hideProgress();
            }

            @Override
            public void onNetworkError(String msg) {
                mView.hideProgress();
                mView.networkError(msg);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void loadImage(ImageView imageView, String url) {
        Glide.with(BaseApplication.getApplication()).load(url).crossFade().into(imageView);
    }
}
