package com.loveuu.vv.api.apifac;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by VV on 2016/9/23.
 */

public interface StringApi {

    @GET("Area/getOpenCity")
    Call<ResponseBody> getString(@QueryMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Broker/login") //登录
    Call<ResponseBody> userLogin(@FieldMap Map<String, String> params);
}
