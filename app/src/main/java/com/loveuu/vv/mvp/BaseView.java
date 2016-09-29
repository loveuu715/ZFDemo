package com.loveuu.vv.mvp;

/**
 * Created by VV on 2016/9/21.
 */
public interface BaseView<T> {

    void showProgress(String msg);

    void setPresenter(T presenter);

    void hideProgress();

    void networkError(String msg);
}
