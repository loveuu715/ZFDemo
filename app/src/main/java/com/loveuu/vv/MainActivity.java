package com.loveuu.vv;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.loveuu.vv.base.BaseActivity;
import com.loveuu.vv.base.eventbus.EventIds;
import com.loveuu.vv.base.eventbus.EventObject;
import com.loveuu.vv.mvp.fragment.ContractFragment;
import com.loveuu.vv.mvp.fragment.HomeFragment;
import com.loveuu.vv.mvp.fragment.HouseSourceFragment;
import com.loveuu.vv.mvp.fragment.UserCenterFragment;
import com.loveuu.vv.utils.DialogManager;
import com.loveuu.vv.utils.TipUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, TencentLocationListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.view_badge_center_tip)
    View mCenterBadge;
    private ArrayList<Fragment> fragments;
    private TencentLocationManager mLocationManager;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    private void initLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
//        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        mLocationManager = TencentLocationManager.getInstance(this);
        mLocationManager.requestLocationUpdates(request, this);
    }

    @Override
    public void init() {
        initBottomNavigationBar();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initLocation();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCenterBadge.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }
    private void initBottomNavigationBar() {

        initialiseBottombar();

        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void initialiseBottombar() {

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.homelan, "首页")
                .setActiveColorResource(R.color.main_home_color))
                .addItem(new BottomNavigationItem(R.mipmap.houselan, "房源")
                        .setActiveColorResource(R.color.main_house_color))
                .addItem(new BottomNavigationItem(R.mipmap.tongxunlan, "联系人")
                        .setActiveColorResource(R.color.main_contract_color))
                .addItem(new BottomNavigationItem(R.mipmap.icon_center, "个人中心")
                        .setActiveColorResource(R.color.main_center_color))
                .setFirstSelectedPosition(0)
                .setBarBackgroundColor(R.color.bottom_bar_color)
                .setInActiveColor(R.color.bottom_bar_def_icon)
                .initialise();
        bottomNavigationBar.setAutoHideEnabled(true);

    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(HouseSourceFragment.newInstance("House"));
        fragments.add(ContractFragment.newInstance("Contractor"));
        fragments.add(UserCenterFragment.newInstance("Center"));
        return fragments;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_layFrame, fragments.get(0));
        transaction.commit();
    }

    @Subscribe
    public void onEvent(EventObject eo) {
        if (eo.getEventId() == EventIds.TOKEN_INVALID){
            DialogManager.showOfflineDialog(this);
        }
    }

    @Override
    public void onBackPressed() {
        BaseApplication.getApplication().exit();
//        UserManager.getInstance().clearUserInfo();
        super.onBackPressed();
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 3)
            mCenterBadge.setVisibility(View.GONE);
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
