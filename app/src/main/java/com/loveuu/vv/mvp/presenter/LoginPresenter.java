package com.loveuu.vv.mvp.presenter;

import android.text.TextUtils;

import com.loveuu.vv.mvp.contract.LogingContract;
import com.loveuu.vv.mvp.model.LoginModel;
import com.loveuu.vv.mvp.model.ModelCallback;

/**
 * Created by VV on 2016/9/28.
 */

public class LoginPresenter implements LogingContract.Presenter {

    public static int ACCOUNT_NULL = -10;
    public static int ACCOUNT_FORMAT_ERROR = -12;
    public static int PASSWORD_NULL = -14;

    private LogingContract.View mView;
    private final LoginModel mLoginModel;

    public LoginPresenter(LogingContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        mLoginModel = new LoginModel();
    }

    @Override
    public void getVerification(String mobile) {

    }

    @Override
    public void login(final String account, String password) {
        //登录
        if (TextUtils.isEmpty(account)) {
            mView.loginError(ACCOUNT_NULL, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mView.loginError(ACCOUNT_NULL, "密码不能为空");
            return;
        }

        mView.showProgress("");

        mLoginModel.toLogin(account, password, new ModelCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mView.loginSuccess();
                mView.hideProgress();
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                mView.loginError(errorCode, errorMsg);
                mView.hideProgress();
            }

            @Override
            public void onEmpty() {
                mView.hideProgress();
            }

            @Override
            public void onNetworkError(String msg) {
                mView.onNetworkError(msg);
            }
        });
    }

    @Override
    public void start() {

    }
}
