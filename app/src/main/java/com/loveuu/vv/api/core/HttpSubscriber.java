package com.loveuu.vv.api.core;

import android.app.Activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loveuu.vv.BaseApplication;
import com.loveuu.vv.bean.BaseResponse;
import com.loveuu.vv.mvp.activity.LoginActivity;
import com.loveuu.vv.utils.ActivityManager;
import com.loveuu.vv.utils.DialogManager;
import com.loveuu.vv.utils.LogUtil;
import com.loveuu.vv.utils.SceneManager;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by VV on 2016/9/22.
 */

public abstract class HttpSubscriber<T> extends Subscriber<T> {

    public static final int LOCAL_ERROR_CODE = 100;//默认
    public static final int NET_CONNECT_FAILED = 102;//网络连接失败
    public static final String TAG = "HttpSubscriber";


    @Override
    public void onStart() {
        onMyStart();
    }

    @Override
    public void onCompleted() {
        onMyCompleted();
    }

    @Override
    public void onError(final Throwable e) {
        //TODO 需要完善异常的判断
        final String stringErr = e.getLocalizedMessage();
        Observable.just(e)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Throwable>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(Throwable throwable) {
                        if (e instanceof SocketTimeoutException) {
                            onMyError(NET_CONNECT_FAILED, "连接超时");
                        } else if (e instanceof UnknownHostException) {
                            onMyError(NET_CONNECT_FAILED, "无法连接服务器，请检查网络是否正常");
                        } else {
                            onMyError(NET_CONNECT_FAILED, stringErr);
                        }
                    }
                });
    }

    @Override
    public void onNext(T t) {
        if (BaseResponse.class.isInstance(t)) {
            BaseResponse bean = (BaseResponse) t;
            if (bean.isStatus()) {//请求成功
                if (bean.getData() == null && "".equals(bean.getData())) {
                    onDataEmpty();
                    return;
                } else {//数据不为空
                    String result;
                    try {
                        result = ApiManager.objectMapper.writeValueAsString(bean.getData());
                        onMyNext(result);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            } else {//请求失败
                int code = bean.getErrcode();
                LogUtil.i("hate", "code====>" + code);
                if (code == 4006 || code == 2003) {//token过期
                    ApiStack.getInstance().cancelAll();
                    BaseApplication.getsMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            final Activity activity = ActivityManager.getInstances().getTopActivity();
                            if (activity == null) {//只有主界面在
                                SceneManager.toScene(BaseApplication.getApplication(), LoginActivity.class, null);
                            } else {
                                DialogManager.showOfflineDialog(activity);
                            }
                        }
                    });
                } else {
                    //TODO 错误代码处理
//                    ErrorCodeProcess.getInstance().processErrorCode(bean.getErrcode());
                    onMyError(bean.getErrcode(), bean.getInfo());
                }
            }
        } else {
            //如果没返回数据没指向BaseResponse，直接返回原始json数据
            try {
                String jsonStr = ApiManager.objectMapper.writeValueAsString(t);
                LogUtil.i(TAG, "请求数据(非BaseResponse):" + jsonStr);
                onMyNext(jsonStr);
            } catch (JsonProcessingException e) {
                onMyError(LOCAL_ERROR_CODE, e.getMessage());
            }
        }
    }

    public void onMyStart() {
    }

    public abstract void onMyNext(String jsonStr);

    public abstract void onMyError(int errorCode, String errorMsg);

    public abstract void onMyCompleted();

    public abstract void onDataEmpty();
}
