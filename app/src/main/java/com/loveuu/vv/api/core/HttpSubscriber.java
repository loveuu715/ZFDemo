package com.loveuu.vv.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loveuu.vv.bean.BaseResponse;
import com.loveuu.vv.utils.LogUtil;

import rx.Subscriber;

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
    public void onError(Throwable e) {
        String stringErr = e.getLocalizedMessage();
        //TODO 需要完善异常的判断
        if (stringErr.contains("UnknownHostException")) {
            onMyError(NET_CONNECT_FAILED, "无法连接服务器，请检查网络是否正常");
        } else {
            onMyError(LOCAL_ERROR_CODE, stringErr);
        }
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
                onMyError(bean.getErrcode(), bean.getInfo());
            }
        } else {
            //如果没返回数据没指向BaseResponse，直接返回原始json数据
            try {
                String jsonStr = ApiManager.objectMapper.writeValueAsString(t);
                LogUtil.i(TAG, "请求数据(非BaseResponse):"+jsonStr);
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
