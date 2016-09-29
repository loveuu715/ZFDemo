package com.loveuu.vv.mvp.model;

import com.loveuu.vv.api.Api;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.apifac.ApiFactory;
import com.loveuu.vv.app.UserManager;
import com.loveuu.vv.mvp.BaseModel;
import com.loveuu.vv.utils.LogUtil;
import com.loveuu.vv.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by VV on 2016/9/29.
 * 登录数据层
 */

public class LoginModel extends BaseModel implements ILoginModel {

    @Override
    public <T> void toLogin(final String account, final String password, final ModelCallback<T> callback) {
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

                        LogUtil.i("hate", "time:" + time);
                        UserManager.getInstance().saveIdentifier(identifier);
                        UserManager.getInstance().saveAccount(account);
                        UserManager.getInstance().saveSign(sign);
                        UserManager.getInstance().saveToken(token);
                        boolean isMobile = StringUtil.isMobileNo(account);
                        LogUtil.i("hate", "isMobile:" + isMobile);
                        UserManager.getInstance().saveUserType(isMobile ? 1 : 2);
                        LogUtil.i("hate", "userType:" + UserManager.getInstance().getUserType());
                        UserManager.getInstance().saveIsMobile(isMobile);
                        UserManager.getInstance().saveIsZhongYuan(!isMobile);
                        UserManager.getInstance().saveTime(time);
                        callback.onSuccess((T) "成功");
                    } else {
                        callback.onError(jsonObject.getInt("errcode"), jsonObject.getString("info"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                callback.onError(errorCode, errorMsg);
            }

            @Override
            public void onEmpty() {
                callback.onEmpty();
            }

            @Override
            public void noNetworkError(String msg) {
                callback.noNetworkError(msg);
            }
        });
    }
}
