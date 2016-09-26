package com.loveuu.vv.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.bean.BannerListBean;
import com.loveuu.vv.mvp.activity.SettingActivity;
import com.loveuu.vv.utils.DensityUtil;
import com.loveuu.vv.utils.LogUtil;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.utils.TipUtil;
import com.loveuu.vv.widget.viewpager.InterceptScrollViewPager;

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
    private CarouselTask mCarouselTask;
    private int carouselIndex = 0;
    private TextView mTitle;
    private InterceptScrollViewPager mCarouselPager;
    private TextView mTvCarouselDes;
    private LinearLayout mCarouselIndicator;
    private CarouselAdapter mCarouselAdapter;


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
        mCarouselTask = new CarouselTask();
        initViews();
        initDatas();
        initEvent();
    }

    private void initEvent() {
        mView.findViewById(R.id.rl_home_toolbar_right_root).setOnClickListener(this);
        mView.findViewById(R.id.iv_home_toolbar_right).setOnClickListener(this);

        //给轮播图添加页面切换事件
        mCarouselPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                carouselIndex = position;
                setCarouselDesAndIndicator(carouselIndex);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initViews() {
        mTitle = (TextView) mView.findViewById(R.id.tv_home_toolbar_title);
        mTitle.setText("首页");
        mCarouselPager = (InterceptScrollViewPager) mView.findViewById(R.id.vp_home_carousel_view);
        mTvCarouselDes = (TextView) mView.findViewById(R.id.tv_home_carousel_pic_desc);
        mCarouselIndicator = (LinearLayout) mView.findViewById(R.id.ll_home_carousel_pic_indicator);
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
        LogUtil.d("hate", "进行初始化");
        carouselIndex = 0;
        mCarouselTask.stop();
        BannerListBean bannerListBean = null;
        List<BannerListBean> beans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bannerListBean = new BannerListBean();
            bannerListBean.img = imageUrls[i];
            bannerListBean.name = des[i];
            bannerListBean.url = i + "";
            beans.add(bannerListBean);
        }
        setCarouselDatas(beans);//设置轮播图数据
        initCarouselIndicators();//初始化轮播图导航条
        setCarouselDesAndIndicator(0);//设置导航条数据
        mCarouselPager.setCurrentItem(0);
        mCarouselTask.start();//开始轮播
    }

    private void initCarouselIndicators() {
        //清空以前存在的点
        mCarouselIndicator.removeAllViews();
        //轮播图有几张 加几个点
        for (int i = 0; i < sBannerList.size(); i++) {
            View v_point = new View(mContext);
            //设置点的背景选择器
            v_point.setBackgroundResource(R.drawable.carousel_indicator_seletor);
            v_point.setEnabled(false);//默认是默认的灰色点

            //设置点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dp2px(mContext, 8),
                    DensityUtil.dp2px(mContext, 8));
            //设置点与点直接的间距
            params.leftMargin = DensityUtil.dp2px(mContext, 6);

            //设置参数
            v_point.setLayoutParams(params);
            mCarouselIndicator.addView(v_point);
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

    private void setCarouselDatas(List<BannerListBean> list) {
        //获取轮播图的数据
        sBannerList = list;
        if (mCarouselAdapter == null) {
            mCarouselAdapter = new CarouselAdapter();
            mCarouselPager.setAdapter(mCarouselAdapter);
        }
        mCarouselAdapter.notifyDataSetChanged();//更新界面
    }

    private void setCarouselDesAndIndicator(int position) {
        if (position < 0 || position > sBannerList.size() - 1) {
            return;
        }
        //设置描述信息
        mTvCarouselDes.setText(sBannerList.get(position).name);
        //设置点是否是选中
        for (int i = 0; i < sBannerList.size(); i++) {
            mCarouselIndicator.getChildAt(i).setEnabled(i == position);
        }
    }

    private class CarouselTask extends Handler implements Runnable {

        public void stop() {
            //移除当前所有的任务
            removeCallbacksAndMessages(null);
        }

        public void start() {
            stop();
            if (sBannerList.size() <= 1) {
                return;
            }
            postDelayed(this, 2000);
        }

        @Override
        public void run() {
            //控制轮播图的显示
            mCarouselPager.setCurrentItem((mCarouselPager.getCurrentItem() + 1)
                    % mCarouselPager.getAdapter().getCount(), true);
            postDelayed(this, 2000);
        }
    }


    private class CarouselAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView ivCarouselPic = new ImageView(mContext);
            ivCarouselPic.setScaleType(ImageView.ScaleType.FIT_XY);

            //设备默认的图片,网络缓慢
//            ivCarouselPic.setImageResource(R.drawable.home_scroll_default);

            //异步加载图片，并且显示到组件中
            Glide.with(mContext).load(sBannerList.get(position).img).crossFade().into(ivCarouselPic);

            //给图片添加触摸事件
            ivCarouselPic.setOnTouchListener(new View.OnTouchListener() {

                private float downX;
                private float downY;
                private long downTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://按下停止轮播
                            downX = event.getX();
                            downY = event.getY();
                            downTime = System.currentTimeMillis();
                            mCarouselTask.stop();
                            break;
                        case MotionEvent.ACTION_CANCEL://事件取消
                            mCarouselTask.start();
                            break;
                        case MotionEvent.ACTION_UP://松开
                            float upX = event.getX();
                            float upY = event.getY();

                            if (upX == downX && upY == downY) {
                                long upTime = System.currentTimeMillis();
                                if (upTime - downTime < 500) {
                                    //点击
                                    lunboPicClick(carouselIndex);
                                }
                            }
                            mCarouselTask.start();//开始轮播
                            break;
                        default:
                            break;
                    }
                    return true;
                }

                private void lunboPicClick(int position) {
                    //处理图片的点击事件
                    TipUtil.showToast("点击了轮播" + position);

                }
            });
            container.addView(ivCarouselPic);
            return ivCarouselPic;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return sBannerList == null ? 0 : sBannerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d("hate", "onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("hate", "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("hate", "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("hate", "onDestroy");
    }
}
