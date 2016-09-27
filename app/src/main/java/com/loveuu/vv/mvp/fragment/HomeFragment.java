package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.bean.BannerListBean;
import com.loveuu.vv.mvp.activity.SettingActivity;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.utils.TipUtil;
import com.mevv.library.CarouselView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VV on 2016/9/21.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private boolean isInit = false;
    private List<BannerListBean> sBannerList;
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private String[] des = {"哇哈哈", "我不是你", "从你眼中发现", "我是孤独的", "一切都是虚假"};
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
        initDatas();
        initEvent();
    }

    private void initEvent() {
        mView.findViewById(R.id.rl_home_toolbar_right_root).setOnClickListener(this);
        mView.findViewById(R.id.iv_home_toolbar_right).setOnClickListener(this);
    }

    private void initViews() {
        mTitle = (TextView) mView.findViewById(R.id.tv_home_toolbar_title);
        mCarouselView = (CarouselView) mView.findViewById(R.id.home_carouselView);
        mTitle.setText("首页");

    }

    private void initDatas() {
        initCarousel(2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initCarousel(5);
                isInit = true;
            }
        }, 3000);
    }

    private void initCarousel(int count) {
        BannerListBean bannerListBean = null;
        List<BannerListBean> beans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bannerListBean = new BannerListBean();
            bannerListBean.img = imageUrls[i];
            bannerListBean.name = des[i];
            bannerListBean.url = i + "";
            beans.add(bannerListBean);
        }
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
}
