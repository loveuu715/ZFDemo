package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;

/**
 * Created by VV on 2016/9/21.
 */

public class UserCenterFragment extends BaseFragment {

    public static UserCenterFragment newInstance(String tag) {
        Bundle args = new Bundle();
        UserCenterFragment fragment = new UserCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTitle;
    private Toolbar mToolbar;


    @Override
    protected int bindLayout() {
        return R.layout.fragment_game;
    }

    @Override
    protected void init() {
        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mTitle = (TextView) mView.findViewById(R.id.title_text);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(null);
        mTitle.setText("个人中心");
    }
}
