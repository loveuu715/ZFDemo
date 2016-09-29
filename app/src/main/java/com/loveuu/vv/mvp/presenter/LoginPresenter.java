package com.loveuu.vv.mvp.presenter;

import android.text.TextUtils;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.app.UserManager;
import com.loveuu.vv.mvp.contract.LogingContract;
import com.loveuu.vv.utils.LogUtil;
import com.loveuu.vv.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by VV on 2016/9/28.
 */

public class LoginPresenter implements LogingContract.Presenter {

    public static int ACCOUNT_NULL = -10;
    public static int ACCOUNT_FORMAT_ERROR = -12;
    public static int PASSWORD_NULL = -14;

    private LogingContract.View mView;

    public LoginPresenter(LogingContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
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

        mView.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);

        Api.getString(ApiFactory.INSTANCE.getStringApi().userLogin(params), this, new ICallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i("hate", "loginResult" + result);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("status")) {//状态正常
                        String data = jsonObject.getString("data");
                        JSONObject dataJson = new JSONObject(data);
                        String token = dataJson.getString("token");
                        JSONObject userSignJson = new JSONObject(dataJson.getString("UserSig"));
                        String expiry_after = userSignJson.getString("TLS.expiry_after");
                        String identifier = userSignJson.getString("TLS.identifier");
                        String sign = userSignJson.getString("TLS.sig");
                        String time = userSignJson.getString("TLS.time");

                        LogUtil.i("hate", "time:"+time);
                        UserManager.getInstance().saveIdentifier(identifier);
                        UserManager.getInstance().saveAccount(account);
                        UserManager.getInstance().saveSign(sign);
                        UserManager.getInstance().saveToken(token);
                        boolean isMobile = StringUtil.isMobileNo(account);
                        LogUtil.i("hate", "isMobile:"+isMobile);
                        UserManager.getInstance().saveUserType(isMobile ? 1 : 2);
                        LogUtil.i("hate", "userType:"+UserManager.getInstance().getUserType());
                        UserManager.getInstance().saveIsMobile(isMobile);
                        UserManager.getInstance().saveIsZhongYuan(!isMobile);
                        UserManager.getInstance().saveTime(time);
                        mView.loginSuccess();
                        mView.hideProgress();
                    } else {
                        mView.loginError(jsonObject.getInt("errcode"), jsonObject.getString("info"));
                        mView.hideProgress();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            public void noNetworkError(String msg) {
                mView.networkError(msg);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void start() {

    }
}
