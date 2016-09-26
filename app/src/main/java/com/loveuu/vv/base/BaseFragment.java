package com.loveuu.vv.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loveuu.vv.base.eventbus.EventObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by VV on 2016/9/21.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.mContext = getContext();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(bindLayout(), null);
            EventBus.getDefault().register(this);
            init();
        }
        return mView;
    }

    protected abstract int bindLayout();

    protected abstract void init();

    @Subscribe
    public void onEvent(EventObject obj) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
