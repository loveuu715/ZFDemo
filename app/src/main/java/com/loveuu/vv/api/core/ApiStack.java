package com.loveuu.vv.api.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import rx.Subscription;

/**
 * Created by VV on 2016/9/23.
 * api请求任务栈管理
 * 由于Retrofit的请求一旦发出就不可取消,所以这里变相取消观察者的监听来实现取消网络请求操作
 * 目的只是让观察者收不到订阅消息,但网络请求一旦发出会一直进行,直至请求结束(完成).
 */

public class ApiStack {

    private static ApiStack sApiStack;
    private static LinkedHashMap<Object, Subscription> sStack;
    /**
     * 统一tag订阅者如果存在,前一个订阅者是否取消订阅,
     * 如果为false,覆盖同tag的订阅者,但不取消前一个订阅者的订阅
     * 如果为true,覆盖同tag的订阅者,前一个并取消订阅者的订阅
     */
    public static boolean TASK_SINGLE = false;

    private ApiStack() {
        sStack = new LinkedHashMap<>();
    }

    public static ApiStack getInstance() {
        if (sApiStack == null) {
            synchronized (ApiStack.class) {
                if (sApiStack == null)
                    sApiStack = new ApiStack();
            }
        }
        return sApiStack;
    }

    public void enqueue(Subscription subscription, Object tag) {
        if (TASK_SINGLE) {//取消同tag前一个订阅者的订阅
            if (sStack.containsKey(tag)) {
                Subscription localSub = sStack.get(tag);
                if (!localSub.isUnsubscribed())
                    localSub.unsubscribe();
            }
        }
        sStack.put(tag, subscription);
    }

    public void cancel(Object tag) {
        if (sStack.containsKey(tag)) {
            Subscription subscription = sStack.get(tag);
            if (!subscription.isUnsubscribed())
                subscription.unsubscribe();
            sStack.remove(tag);
        }
    }

    public void cancelAll() {
        Set<Map.Entry<Object, Subscription>> entrySet = sStack.entrySet();
        for (Map.Entry<Object, Subscription> entry : entrySet) {
            if (!entry.getValue().isUnsubscribed())
                entry.getValue().unsubscribe();
        }
        sStack.clear();
    }

}
