package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.widget.citypicker.CityPickerActivity;

/**
 * Created by VV on 2016/9/21.
 */

public class HouseSourceFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mTitleRootView;
    private TextView mTvTitleNewHouse;
    private TextView mTvTitleOldHouse;
    private int mCurrentPosition = 0;

    public static HouseSourceFragment newInstance(String tag) {
        Bundle args = new Bundle();
        HouseSourceFragment fragment = new HouseSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_house_source;
    }

    @Override
    protected void init() {
        mTitleRootView = (LinearLayout) mView.findViewById(R.id.housesource_table_ll);
        mTvTitleNewHouse = (TextView) mView.findViewById(R.id.housesource_table_new);
        mTvTitleOldHouse = (TextView) mView.findViewById(R.id.housesource_table_old);
        switchTitleNew();
        mView.findViewById(R.id.iv_house_toolbar_right_search).setOnClickListener(this);
        mTvTitleNewHouse.setOnClickListener(this);
        mTvTitleOldHouse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.housesource_table_new:
                switchTitleNew();
                break;
            case R.id.housesource_table_old:
                switchTitleOld();
                break;
            case R.id.iv_house_toolbar_right_search:
                SceneManager.toScene(mContext, CityPickerActivity.class, null);
                break;
        }
    }

    private void switchTitleNew() {
        if (mCurrentPosition != 0) {
//            mTitleRootView.setEnabled(true);
            mTvTitleNewHouse.setEnabled(false);
            mTvTitleOldHouse.setEnabled(true);
            mCurrentPosition = 0;
        }
    }

    private void switchTitleOld() {
        if (mCurrentPosition != 1) {
//            mTitleRootView.setEnabled(false);
            mTvTitleNewHouse.setEnabled(true);
            mTvTitleOldHouse.setEnabled(false);
            mCurrentPosition = 1;
        }
    }

}
