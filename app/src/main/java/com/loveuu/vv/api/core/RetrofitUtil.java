package com.loveuu.vv.api.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by VV on 2016/9/22.
 */

public class RetrofitUtil {

    /**
     * api路径
     */
    private static final String BASE_URL = "http://test.hiweixiao.com/Linker/";

    private static Retrofit sRetrofit;
    private static Retrofit sDefRetrofit;

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(OkHttpUtil.getClient())
                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())//使用Gson转换工厂类
                    .addConverterFactory(JacksonConverterFactory.create())//使用Json转换工厂类
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//回调使用RxJava工厂类
                    .build();
        }
        return sRetrofit;
    }

    public static Retrofit getDefault(){
        if (sDefRetrofit == null) {
            sDefRetrofit = new Retrofit.Builder()
                    .client(OkHttpUtil.getClient())
                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())//使用Gson转换工厂类
//                    .addConverterFactory(JacksonConverterFactory.create())//使用Json转换工厂类
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//回调使用RxJava工厂类
                    .build();
        }
        return sDefRetrofit;
    }

}
