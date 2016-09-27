package com.loveuu.vv.mvp;

/**
 * Created by VV on 2016/9/27.
 */

public interface BasePresenter<V extends BaseView> {


    void onAttachView(V view);

    void onDestroy();
}
