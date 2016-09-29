package com.loveuu.vv.api;

import com.loveuu.vv.api.core.ApiService;
import com.loveuu.vv.api.core.HttpSubscriber;
import com.loveuu.vv.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by VV on 2016/9/22.
 * 请求数据api统一管理类
 */

public class Api {
    public static final String ADCODE = "440300";


    public static <T> void getList(final Observable<?> observable, Class<?> clazz, Object tag, ICallback<T> callback) {
        if (!NetworkUtil.isNetworkEnable()) {
            callback.noNetworkError("无网络可用");
            return;
        }
        ApiService.applySchedulersForList(observable, clazz, tag, callback);
    }

    public static <T> void getObject(final Observable<?> observable, Class<?> clazz, Object tag, ICallback<T> callback) {
        if (!NetworkUtil.isNetworkEnable()) {
            callback.noNetworkError("无网络可用");
            return;
        }
        ApiService.applySchedulersObject(observable, clazz, tag, callback);
    }

    public static void getString(final Call<ResponseBody> call, Object tag, final ICallback<String> callback) {

        if (!NetworkUtil.isNetworkEnable()) {
            callback.noNetworkError("无网络可用");
            return;
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (callback != null)
                    try {
                        callback.onSuccess(new String(response.body().bytes(), "UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String stringErr = t.getLocalizedMessage();
                if (callback != null)
                    callback.onError(HttpSubscriber.LOCAL_ERROR_CODE, stringErr);
            }
        });
    }


}
