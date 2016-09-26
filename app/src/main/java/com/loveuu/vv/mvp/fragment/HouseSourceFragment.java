package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.view.View;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;
import com.loveuu.vv.utils.SceneManager;
import com.loveuu.vv.widget.citypicker.CityPickerActivity;

/**
 * Created by VV on 2016/9/21.
 */

public class HouseSourceFragment extends BaseFragment implements View.OnClickListener {

    public static HouseSourceFragment newInstance(String tag) {
        Bundle args = new Bundle();
        HouseSourceFragment fragment = new HouseSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_book;
    }

    @Override
    protected void init() {
        mView.findViewById(R.id.iv_house_toolbar_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_house_toolbar_right:
                SceneManager.toScene(mContext, CityPickerActivity.class, null);
                break;
        }
    }
}
