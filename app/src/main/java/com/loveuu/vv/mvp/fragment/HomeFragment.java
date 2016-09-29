package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.app.AppConstants;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.mvp.activity.SettingActivity;
import com.loveuu.vv.mvp.contract.HomeContract;
import com.loveuu.vv.mvp.presenter.HomePresenter;
import com.loveuu.vv.utils.DialogManager;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.utils.TipUtil;
import com.mevv.library.CarouselView;

import java.util.List;

/**
 * Created by VV on 2016/9/21.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View ,View.OnClickListener {

    private HomeContract.Presenter mPresenter;

    private TextView mTitle;
    private CarouselView mCarouselView;

    public static HomeFragment newInstance(String tag) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initViews();
        initEvent();
        mPresenter = new HomePresenter(this);
        mPresenter.getBannerList(AppConstants.CURRENT_ADCODE);
    }

    private void initEvent() {
        mView.findViewById(R.id.rl_home_toolbar_right_root).setOnClickListener(this);
        mView.findViewById(R.id.iv_home_toolbar_right).setOnClickListener(this);
    }

    private void initViews() {
        mTitle = (TextView) mView.findViewById(R.id.tv_home_toolbar_title);
        mTitle.setText("首页");
        mCarouselView = (CarouselView) mView.findViewById(R.id.home_carouselView);
        mCarouselView.setOnCarouselClickListener(new CarouselView.OnCarouselClickListener() {
            @Override
            public void onCarouselClick(CarouselView.BannerInfo bannerInfo, int position) {
                TipUtil.showToast(position+"");
            }
        });

        mCarouselView.setPicInitListener(new CarouselView.PicInitListener() {
            @Override
            public void onPicInit(ImageView imageView, String url, int position) {
                mPresenter.loadImage(imageView, url);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home_toolbar_right_root:
                TipUtil.showToast("点击了城市");
                break;
            case R.id.iv_home_toolbar_right:
                SceneManager.toScene(mContext, SettingActivity.class, null);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgress(String msg) {
        DialogManager.showLoadingDialog(mContext, msg);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void hideProgress() {
        DialogManager.dismissLoadingDialog();
    }

    @Override
    public void networkError(String msg) {
        TipUtil.showToast(mContext, msg);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        TipUtil.showToast(mContext, errorMsg);
    }

    @Override
    public void showBanner(List<CarouselView.BannerInfo> infoList) {
        mCarouselView.setCarouselData(infoList);
    }
}
