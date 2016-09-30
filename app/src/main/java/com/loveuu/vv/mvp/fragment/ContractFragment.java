package com.loveuu.vv.mvp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseFragment;

/**
 * Created by VV on 2016/9/21.
 */

public class ContractFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvTitleMyCustomer;
    private TextView mTvTitleMyAgent;

    public static ContractFragment newInstance(String tag) {
        Bundle args = new Bundle();
        ContractFragment fragment = new ContractFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void init() {
        mTvTitleMyCustomer = (TextView) mView.findViewById(R.id.contract_toolBar_my_customer);
        mTvTitleMyAgent = (TextView) mView.findViewById(R.id.contract_toolBar_my_agent);

        mTvTitleMyAgent.setOnClickListener(this);
        mTvTitleMyCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contract_toolBar_my_customer:
                switchAgent();
                break;
            case R.id.contract_toolBar_my_agent:
                switchCustomer();
                break;
        }
    }

    private void switchAgent(){
        mTvTitleMyCustomer.setEnabled(false);
        mTvTitleMyAgent.setEnabled(true);
    }

    private void switchCustomer(){
        mTvTitleMyCustomer.setEnabled(true);
        mTvTitleMyAgent.setEnabled(false);
    }
}
