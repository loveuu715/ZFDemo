package com.loveuu.vv.mvp.presenter;

import com.loveuu.vv.mvp.contract.CityPickerContract;
import com.loveuu.vv.mvp.model.CityPickerModel;
import com.loveuu.vv.mvp.model.ModelCallback;
import com.loveuu.vv.widget.citypicker.CityBean;

import java.util.List;

/**
 * Created by VV on 2016/9/30.
 * 开通LBS的城市中间者
 */

public class CityPickerPresenter implements CityPickerContract.Presenter {

    private CityPickerContract.View mView;
    private CityPickerModel mPickerModel;

    public CityPickerPresenter(CityPickerContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mPickerModel = new CityPickerModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void getCities() {
        mPickerModel.getCities(new ModelCallback<List<CityBean>>() {
            @Override
            public void onSuccess(List<CityBean> result) {
                mView.showCities(result);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                mView.onError(errorCode, errorMsg);
            }

            @Override
            public void onEmpty() {
                mView.onEmpty();
            }

            @Override
            public void onNetworkError(String msg) {
                mView.onNetworkError(msg);
            }
        });
    }
}
