package com.loveuu.vv.mvp.contract;

import com.loveuu.vv.mvp.BasePresenter;
import com.loveuu.vv.mvp.BaseView;
import com.loveuu.vv.widget.citypicker.CityBean;

import java.util.List;

/**
 * Created by VV on 2016/9/30.
 */

public interface CityPickerContract {

    interface View extends BaseView<Presenter> {
        void onError(int errorCode, String errorMsg);
        void onEmpty();
        void showCities(List<CityBean> cityBeanList);
    }


    interface Presenter extends BasePresenter {
        void getCities();
    }

}
