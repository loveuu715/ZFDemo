package com.loveuu.vv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.loveuu.vv.base.eventbus.EventObject;
import com.loveuu.vv.mvp.fragment.ContractorFragment;
import com.loveuu.vv.mvp.fragment.HomeFragment;
import com.loveuu.vv.mvp.fragment.HouseSourceFragment;
import com.loveuu.vv.mvp.fragment.UserCenterFragment;
import com.loveuu.vv.utils.TipUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, TencentLocationListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private ArrayList<Fragment> fragments;
    private Unbinder mUnbinder;
    private TencentLocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initLocation();
            }
        });
    }

    private void initLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
//        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        mLocationManager = TencentLocationManager.getInstance(this);
        mLocationManager.requestLocationUpdates(request, this);
    }

    public void init() {
        initBottomNavigationBar();
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.homelan, "首页")
                .setActiveColorResource(R.color.main_home_color))
                .addItem(new BottomNavigationItem(R.mipmap.houselan, "房源")
                        .setActiveColorResource(R.color.main_house_color))
                .addItem(new BottomNavigationItem(R.mipmap.tongxunlan, "联系人")
                        .setActiveColorResource(R.color.main_contract_color))
                .addItem(new BottomNavigationItem(R.mipmap.mycenterlan, "个人中心")
                        .setActiveColorResource(R.color.main_center_color))
                .setFirstSelectedPosition(0)
                .setBarBackgroundColor(R.color.bottom_bar_color)
                .setInActiveColor(R.color.bottom_bar_def_icon)
                .initialise();

        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(HouseSourceFragment.newInstance("House"));
        fragments.add(ContractorFragment.newInstance("Contractor"));
        fragments.add(UserCenterFragment.newInstance("Center"));
        return fragments;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_layFrame, HomeFragment.newInstance("Home"));
        transaction.commit();
    }

    @Subscribe
    public void onEvent(EventObject eo) {
    }

    @Override
    public void onBackPressed() {
        BaseApplication.getApplication().exit();
        super.onBackPressed();
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.main_layFrame, fragment);
                } else {
                    ft.add(R.id.main_layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLocationChanged(TencentLocation location, int errorCode, String reason) {
        if (TencentLocation.ERROR_OK == errorCode) {
            // 定位成功
            mLocationManager.removeUpdates(this);
            TipUtil.showToast(location.getCity());
        } else {
            // 定位失败
            TipUtil.showToast(reason);
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
