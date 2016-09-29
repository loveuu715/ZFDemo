package com.loveuu.vv.mvp.contract;

import com.loveuu.vv.mvp.BasePresenter;
import com.loveuu.vv.mvp.BaseView;

/**
 * Created by VV on 2016/9/28.
 */

public interface LogingContract {

    interface View extends BaseView<Presenter>{
        void loginSuccess();
        void loginError(int errorCode, String errorMsg);
    }

    interface Presenter extends BasePresenter {
        void getVerification(String mobile);
        void login(String account, String password);
    }
}
