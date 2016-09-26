package com.loveuu.vv.api.apifac;

import com.loveuu.vv.bean.BaseResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by VV on 2016/9/23.
 */

public interface ListApi {

    @GET("Index/getCarouselImg")
    Observable<BaseResponse> getBannerList(@QueryMap Map<String, String> params);

    @GET("Area/getOpenCity")
    Observable<BaseResponse> getLBSCityList(@QueryMap Map<String, String> params);
}
