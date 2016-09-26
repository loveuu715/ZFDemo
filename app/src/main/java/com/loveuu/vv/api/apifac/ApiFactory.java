package com.loveuu.vv.api.apifac;

import com.loveuu.vv.api.core.RetrofitUtil;

/**
 * Created by VV on 2016/9/23.
 * api 管理枚举类
 */

public enum ApiFactory {
    INSTANCE;
    private final ListApi mListApi;
    private final ObjectApi mObjectApi;
    private final StringApi mStringApi;

    ApiFactory() {
        mListApi = RetrofitUtil.getRetrofit().create(ListApi.class);
        mObjectApi = RetrofitUtil.getRetrofit().create(ObjectApi.class);
        mStringApi = RetrofitUtil.getDefault().create(StringApi.class);
    }

    public ListApi getListApi() {
        return mListApi;
    }

    public ObjectApi getObjectApi() {
        return mObjectApi;
    }

    public StringApi getStringApi() {
        return mStringApi;
    }
}
