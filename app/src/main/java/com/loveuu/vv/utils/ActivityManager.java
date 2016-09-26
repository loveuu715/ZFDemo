package com.loveuu.vv.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by VV on 2016/9/21.
 * Activity任务栈管理
 */
public class ActivityManager {
    private static ActivityManager sActivityManager;
    private Stack<Activity> mStack = new Stack<Activity>();

    private ActivityManager() {}

    public static ActivityManager getInstances() {
        if (sActivityManager == null) {
            synchronized (ActivityManager.class) {
                if (sActivityManager == null)
                    sActivityManager = new ActivityManager();
            }
        }
        return sActivityManager;
    }

    /**
     * activity入栈
     *
     * @param activity
     */
    public void activityEnqueue(Activity activity) {
        if (!mStack.contains(activity)) {
            mStack.push(activity);
        }
    }

    /**
     * 从栈中移除activity
     *
     * @param activity
     */
    public void removeActivityFromStack(Activity activity) {
        if (activity != null && mStack.contains(activity))
//            mStack.peek();//取出栈顶元素并不移除
            mStack.pop();//取出栈顶元素并移除
    }

    /**
     * 关闭指定的activity,并从栈中移除
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mStack.contains(activity) && !activity.isFinishing()) {
            mStack.remove(activity);
            activity.finish();
        }
    }

    public void finishAll() {
        for (Activity activity : mStack) {
            if (activity != null && !activity.isFinishing())
                activity.finish();
        }
        mStack.clear();
    }

    /**
     * 取出栈顶activity
     *
     * @return
     */
    public Activity getTopActivity() {
        return mStack.peek();
    }

    /**
     * 取出栈底activity
     * @return
     */
    public Activity getLastActivity(){
        return mStack.lastElement();
    }

}
