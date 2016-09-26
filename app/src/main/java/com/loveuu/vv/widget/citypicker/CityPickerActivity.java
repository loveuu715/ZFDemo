package com.loveuu.vv.widget.citypicker;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by VV on 2016/9/22.
 */

public class CityPickerActivity extends BaseActivity {

    private List<Area> alist = new ArrayList<Area>();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_text)
    TextView mTitle;
    @BindView(R.id.side_view)
    WaveSideBarView mSideBarView;
    private CharacterParser mCharacterParser;
    private LetterComparator mLetterComparator;

    @Override
    public int bindLayout() {
        return R.layout.activity_city_picker;
    }

    @Override
    public void init() {
        mTitle.setText("选择城市");
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCharacterParser = CharacterParser.getInstance();
        mLetterComparator = new LetterComparator();

        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {

            }
        });
    }
}
