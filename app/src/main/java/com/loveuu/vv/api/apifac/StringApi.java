package com.loveuu.vv.api.apifac;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by VV on 2016/9/23.
 */

public interface StringApi {

    @GET("Area/getOpenCity")
    Call<ResponseBody> getString(@QueryMap Map<String, String> params);


    @POST("Broker/login")
    Call<ResponseBody> userLogin(@Query("account") String account, @Query("password") String password);
}
