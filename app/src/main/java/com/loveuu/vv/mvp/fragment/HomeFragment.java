package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.app.AppConstants;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.base.eventbus.EventIds;
import com.loveuu.vv.base.eventbus.EventObject;
import com.loveuu.vv.bean.HomeLastShareBean;
import com.loveuu.vv.mvp.adapter.HomeLastShareAdapter;
import com.loveuu.vv.mvp.contract.HomeContract;
import com.loveuu.vv.mvp.presenter.HomePresenter;
import com.loveuu.vv.utils.LogUtil;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.utils.TipUtil;
import com.loveuu.vv.widget.citypicker.CityPickerActivity;
import com.mevv.library.CarouselView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VV on 2016/9/21.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View, View.OnClickListener {

    private HomeContract.Presenter mPresenter;

    private TextView mTitle;
    private CarouselView mCarouselView;
    private HomeLastShareAdapter mShareAdapter;
    private List<HomeLastShareBean> mShareBeanList;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvCurrentCity;

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
        EventBus.getDefault().register(this);
        initData();
        initViews();
        initEvent();
        mPresenter = new HomePresenter(this);
        mPresenter.getBannerList(AppConstants.CURRENT_ADCODE);
        mPresenter.getShareList();
    }

    private void initData() {
        mShareBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HomeLastShareBean bean = new HomeLastShareBean();
            bean.setName("测试" + i);
            mShareBeanList.add(bean);
        }
    }

    private void initEvent() {
        mView.findViewById(R.id.rl_home_toolbar_right_root).setOnClickListener(this);
        mView.findViewById(R.id.iv_home_toolbar_right).setOnClickListener(this);
    }

    private void initViews() {
        mTitle = (TextView) mView.findViewById(R.id.tv_home_toolbar_title);
        mTitle.setText("首页");

        mTvCurrentCity = (TextView) mView.findViewById(R.id.tv_home_toolbar_left_city);
        mTvCurrentCity.setText(AppConstants.CURRENT_CITY);

        initFreshLayout();

        mListView = (ListView) mView.findViewById(R.id.home_listView);
        mShareAdapter = new HomeLastShareAdapter(mContext, mShareBeanList);
        mListView.setAdapter(mShareAdapter);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mListView.addHeaderView(initHeader());
            }
        });
    }

    @NonNull
    private View initHeader() {
        View homeHeader = LayoutInflater.from(mContext).inflate(R.layout.home_list_head, null);
        mCarouselView = (CarouselView) homeHeader.findViewById(R.id.home_carouselView);
        mCarouselView.setOnCarouselClickListener(new CarouselView.OnCarouselClickListener() {
            @Override
            public void onCarouselClick(CarouselView.BannerInfo bannerInfo, int position) {
                TipUtil.showToast(position + "");
            }
        });

        mCarouselView.setPicInitListener(new CarouselView.PicInitListener() {
            @Override
            public void onPicInit(ImageView imageView, String url, int position) {
                mPresenter.loadImage(imageView, url);
            }
        });

        homeHeader.findViewById(R.id.ll_home_data_statistics_rootView).setOnClickListener(this);
        homeHeader.findViewById(R.id.ll_home_street_shoot_rootView).setOnClickListener(this);
        homeHeader.findViewById(R.id.ll_home_news_rootView).setOnClickListener(this);

        return homeHeader;
    }

    private void initFreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.home_list_srl_fresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBannerList(AppConstants.CURRENT_ADCODE);
            }
        };

        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh(); // 第一次必须手动调用，直接调用setRefreshing不会触发onRefresh方法
            }
        }, 50);
    }

    @Subscribe
    public void onEvent(EventObject eo) {
        if (eo.getEventId() == EventIds.HOME_CITY) {
            mTvCurrentCity.setText(AppConstants.CURRENT_CITY);
            LogUtil.i("hate", "当前adcode"+AppConstants.CURRENT_ADCODE);
            LogUtil.i("hate", "当前Lat"+AppConstants.CURRENT_LAT);
            LogUtil.i("hate", "当前Lng"+AppConstants.CURRENT_LNG);
            mPresenter.getBannerList(AppConstants.CURRENT_ADCODE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home_toolbar_right_root:
                SceneManager.toScene(mContext, CityPickerActivity.class, null);
                break;
            case R.id.iv_home_toolbar_right:
                break;
            case R.id.ll_home_data_statistics_rootView:
                break;
            case R.id.ll_home_street_shoot_rootView:
                break;
            case R.id.ll_home_news_rootView:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showProgress(String msg) {
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetworkError(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        TipUtil.showToast(mContext, msg);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mSwipeRefreshLayout.setRefreshing(false);
        TipUtil.showToast(mContext, errorMsg);
    }

    @Override
    public void showBanner(List<CarouselView.BannerInfo> infoList) {
        mCarouselView.reset(infoList);
        mSwipeRefreshLayout.setRefreshing(false);
        System.gc();
    }

    @Override
    public void showShare(List<HomeLastShareBean> shareBeanList) {
        LogUtil.i("hate", shareBeanList.size() + "----------");
//        this.mShareBeanList = shareBeanList;
//        if (mShareAdapter != null) {
//            mShareAdapter.notifyDataSetChanged();
//        } else {
//            mShareAdapter = new HomeLastShareAdapter(mContext, mShareBeanList);
//            mListView.setAdapter(mShareAdapter);
//        }
    }
}
