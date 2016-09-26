package com.loveuu.vv.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loveuu.vv.api.ICallback;
import com.loveuu.vv.api.JsonParser;
import com.loveuu.vv.utils.LogUtil;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VV on 2016/9/22.
 */

public class ApiService {

    private static final String TAG = "ApiService";


    /**
     * 获取列表
     *
     * @param responseObservable
     * @param clazz
     * @param callback
     */
    public static <T> Subscription applySchedulersForList(Observable<T> responseObservable, final Class<?> clazz, Object tag, final ICallback callback) {
        Subscription subscription = responseObservable.subscribeOn(Schedulers.io())//改变call的线程
                .observeOn(AndroidSchedulers.mainThread())//改变onNext的线程
                .subscribe(new HttpSubscriber<T>() {
                    @Override
                    public void onStart() {
                        LogUtil.i(TAG, "- - -开始请求- - -");
                    }

                    @Override
                    public void onMyNext(String jsonStr) {
                        LogUtil.i(TAG, "- - -请求数据:===>" + jsonStr);
                        if (callback != null)
                            callback.onSuccess(JsonParser.jsonStrToListBean(jsonStr, clazz));
                    }

                    @Override
                    public void onMyError(int errorCode, String errorMsg) {
                        LogUtil.e(TAG, "- - -请求失败:====>|errorCode : " + errorCode + "|<==errorMsg : " + errorMsg + "|");
                        if (callback != null)
                            callback.onError(errorCode, errorMsg);
                    }

                    @Override
                    public void onMyCompleted() {
                        LogUtil.i(TAG, "- - -请求完成- - -");
                    }

                    @Override
                    public void onDataEmpty() {
                        if (callback != null)
                            callback.onEmpty();
                    }
                });
        if (tag != null)
            ApiStack.getInstance().enqueue(subscription, tag);
        return subscription;
    }


    /**
     * 获取对象
     *
     * @param responseObservable
     * @param clazz
     * @param callback
     * @param <T>
     */
    public static <T> Subscription applySchedulersObject(Observable<T> responseObservable, final Class<?> clazz, Object tag, final ICallback callback) {
        Subscription subscription = responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<T>() {

                    @Override
                    public void onStart() {
                        LogUtil.i(TAG, "- - -开始请求- - -");
                    }

                    @Override
                    public void onMyNext(String jsonStr) {
                        LogUtil.i(TAG, "- - -请求数据:===>" + jsonStr);
                        if (callback != null)
                            callback.onSuccess(JsonParser.jsonStrToBean(jsonStr, clazz));
                    }

                    @Override
                    public void onMyError(int errorCode, String errorMsg) {
                        LogUtil.e(TAG, "- - -请求失败:====>|errorCode : " + errorCode + "|<==errorMsg : " + errorMsg + "|");
                        if (callback != null)
                            callback.onError(errorCode, errorMsg);
                    }

                    @Override
                    public void onMyCompleted() {
                        LogUtil.i(TAG, "- - -请求完成- - - ");
                    }

                    @Override
                    public void onDataEmpty() {
                        if (callback != null)
                            callback.onEmpty();
                    }
                });
        if (tag != null)
            ApiStack.getInstance().enqueue(subscription, tag);
        return subscription;
    }

    /**
     * 自定义解析器
     *
     * @param responseObservable
     * @param callback
     * @param <T>
     */
    public static <T> Subscription applySchedulersCustome(Observable<T> responseObservable, Object tag, final ICallback<String> callback) {
        Subscription subscription = responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<T>() {

                    @Override
                    public void onStart() {
                        LogUtil.i(TAG, "- - -开始请求- - -");
                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            onMyNext(ApiManager.objectMapper.writeValueAsString(t));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            onMyError(HttpSubscriber.LOCAL_ERROR_CODE, e.getMessage());
                        }
                    }

                    @Override
                    public void onMyNext(String jsonStr) {
                        LogUtil.i(TAG, "- - -请求数据:===>" + jsonStr);
                        if (callback != null)
                            callback.onSuccess(jsonStr);
                    }

                    @Override
                    public void onMyError(int errorCode, String errorMsg) {
                        LogUtil.e(TAG, "- - -请求失败:====>|errorCode : " + errorCode + "|<==errorMsg : " + errorMsg + "|");
                        if (callback != null)
                            callback.onError(errorCode, errorMsg);
                    }

                    @Override
                    public void onMyCompleted() {
                        LogUtil.i(TAG, "- - -请求完成- - - ");
                    }

                    @Override
                    public void onDataEmpty() {
                        if (callback != null)
                            callback.onEmpty();
                    }
                });
        if (tag != null)
            ApiStack.getInstance().enqueue(subscription, tag);
        return subscription;
    }


}
