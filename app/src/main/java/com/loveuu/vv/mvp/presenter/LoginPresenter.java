package com.loveuu.vv.mvp.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.mvp.contract.LogingContract;
import com.loveuu.vv.utils.NetworkUtil;

/**
 * Created by VV on 2016/9/28.
 */

public class LoginPresenter implements LogingContract.Presenter {

    public static int ACCOUNT_NULL = -10;
    public static int ACCOUNT_FORMAT_ERROR = -12;
    public static int PASSWORD_NULL = -14;
    public static int NETWORK_ERROR = -16;

    private LogingContract.View mView;

    public LoginPresenter(LogingContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getVerification(String mobile) {

    }

    @Override
    public void login(String account, String password) {
        //登录
        if (TextUtils.isEmpty(account)) {
            mView.loginError(ACCOUNT_NULL, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mView.loginError(ACCOUNT_NULL, "密码不能为空");
            return;
        }
        if (!NetworkUtil.isNetworkEnable()){
            mView.loginError(NETWORK_ERROR, "无网络可用");
            return;
        }

        mView.showProgress();

        Api.getString(ApiFactory.INSTANCE.getStringApi().userLogin(account, password), this, new ICallback<String>() {
            @Override
            public void onSuccess(String result) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO 解析数据
                        mView.loginSuccess();
                        mView.hideProgress();
                    }
                }, 3000);
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
            public void noNetworkError(int code) {
                mView.loginError(NETWORK_ERROR, "网络异常");
                mView.hideProgress();
            }
        });
    }

    @Override
    public void start() {

    }
}
