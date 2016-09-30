package com.loveuu.vv.mvp.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by VV on 2016/9/22.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_text)
    TextView mTitle;


    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        mTitle.setText(getString(R.string.title_setting));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
