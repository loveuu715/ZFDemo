package com.loveuu.vv.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.Gson;
import com.loveuu.vv.api.core.ApiManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by VV on 2016/9/22.
 */

public class JsonParser {

    private static Gson gson;

    public static <T> Object jsonStrToBean(String jsonStr, Class<T> obj) {
        T t = null;
        //设置bean可以不完全接收返回来的json数据
        ApiManager.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            t = ApiManager.objectMapper.readValue(jsonStr, obj);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> ArrayList jsonStrToListBean(String jsonStr, Class<T> obj) {
        ArrayList<T> t = null;
        //设置bean可以不完全接收返回来的json
        ApiManager.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = ApiManager.objectMapper.getTypeFactory().constructParametricType(
                ArrayList.class, obj);
        try {
            t = ApiManager.objectMapper.readValue(jsonStr, javaType);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

}
