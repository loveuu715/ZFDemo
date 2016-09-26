package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;

/**
 * Created by VV on 2016/9/21.
 */

public class ContractorFragment extends BaseFragment {

    public static ContractorFragment newInstance(String tag) {
        Bundle args = new Bundle();
        ContractorFragment fragment = new ContractorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_music;
    }

    @Override
    protected void init() {

    }


}
