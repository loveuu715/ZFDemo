package com.loveuu.vv.widget.citypicker;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.api.core.ApiStack;
import com.loveuu.vv.app.AppConstants;
import com.loveuu.vv.base.BaseActivity;
import com.loveuu.vv.base.eventbus.EventIds;
import com.loveuu.vv.base.eventbus.EventObject;
import com.loveuu.vv.mvp.contract.CityPickerContract;
import com.loveuu.vv.mvp.presenter.CityPickerPresenter;
import com.loveuu.vv.utils.TipUtil;
import com.loveuu.vv.widget.edittext.DelEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by VV on 2016/9/22.
 * 选择LBS已开通的城市
 */

public class CityPickerActivity extends BaseActivity implements CityPickerContract.View {

    private List<SortModel> mSortModels;

    private CityPickerContract.Presenter mPresenter;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_text)
    TextView mTitle;
    @BindView(R.id.side_view)
    WaveSideBarView mSideBarView;
    @BindView(R.id.lv_city_picker_listView)
    ListView mListView;
    @BindView(R.id.det_city_picker_search)
    DelEditText mDelEditText;
    private CharacterParser mCharacterParser;
    private LetterComparator mLetterComparator;
    private SortAdapter mSortAdapter;

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

        mPresenter = new CityPickerPresenter(this);

        mCharacterParser = CharacterParser.getInstance();
        mLetterComparator = new LetterComparator();

        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String s) {
                // 该字母首次出现的位置
                if (s == null || "".equals(s) || mSortModels == null
                        || mSortModels.size() == 0) {

                } else {
                    int position = mSortAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        mListView.setSelection(position);
                    }
                }
            }
        });

        // 根据输入框输入值的改变来过滤搜索
        mDelEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        initEvent();
        //获取开通LBS的城市列表
        mPresenter.getCities();
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortModel selectedData = mSortAdapter.getItem(position);
                String cityName = selectedData.getName();
                String adcode = selectedData.getAdcode();
                if (cityName.endsWith("市"))
                    cityName = cityName.substring(0, cityName.length() - 1);
                AppConstants.CURRENT_ADCODE = adcode;
                AppConstants.CURRENT_CITY = cityName;
                AppConstants.CURRENT_LAT = selectedData.getLat();
                AppConstants.CURRENT_LNG = selectedData.getLng();
                EventBus.getDefault().post(new EventObject(selectedData, EventIds.HOME_CITY));
                onBackPressed();
            }
        });
    }

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void setPresenter(CityPickerContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onNetworkError(String msg) {
        TipUtil.showToast(msg);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        TipUtil.showToast(errorMsg);
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void showCities(final List<CityBean> cityBeanList) {
        mSortModels = filledData(cityBeanList);
        Collections.sort(mSortModels, mLetterComparator);
        mSortAdapter = new SortAdapter(mContext, mSortModels);
        mListView.setAdapter(mSortAdapter);
    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<CityBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            sortModel.setAdcode(date.get(i).getAdcode());
//			sortModel.setLng(date.get(i).lng);
//			sortModel.setLat(date.get(i).lat);
            // 汉字转换成拼音
            String pinyin = mCharacterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = mCharacterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        if (mSortModels == null || mSortModels.size() == 0) {

        } else {
            List<SortModel> filterDateList = new ArrayList<SortModel>();

            if (TextUtils.isEmpty(filterStr)) {
                filterDateList = mSortModels;
            } else {
                filterDateList.clear();
                for (SortModel sortModel : mSortModels) {
                    String name = sortModel.getName();
                    if (name.indexOf(filterStr.toString()) != -1
                            || mCharacterParser.getSelling(name).startsWith(
                            filterStr.toString())) {
                        filterDateList.add(sortModel);
                    }
                }
            }

            // 根据a-z进行排序
            Collections.sort(filterDateList, mLetterComparator);
            mSortAdapter.updateListView(filterDateList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApiStack.getInstance().cancel(((CityPickerPresenter)mPresenter).getPickerModel());
    }
}
