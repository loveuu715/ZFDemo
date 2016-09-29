package com.loveuu.vv.mvp.contract;

import android.widget.ImageView;

import com.loveuu.vv.bean.HomeLastShareBean;
import com.loveuu.vv.mvp.BasePresenter;
import com.loveuu.vv.mvp.BaseView;
import com.mevv.library.CarouselView;

import java.util.List;

/**
 * Created by VV on 2016/9/29.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter> {
        void onError(int errorCode, String errorMsg);
        void showBanner(List<CarouselView.BannerInfo> bannerInfos);
        void showShare(List<HomeLastShareBean> shareBeanList);
    }

    interface Presenter extends BasePresenter{
        void getBannerList(String adcode);
        void loadImage(ImageView imageView, String url) ;
        void getShareList();
    }
}
