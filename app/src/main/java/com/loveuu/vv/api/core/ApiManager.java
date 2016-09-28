package com.loveuu.vv.api.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loveuu.vv.app.AppConstants;
import com.loveuu.vv.app.UserManager;
import com.loveuu.vv.utils.LogUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by VV on 2016/9/22.
 */

public class ApiManager {

    public static final String API_TAG = "API";
    private static final String USER_TYPE = "1";


    public static ObjectMapper objectMapper;
    private static HashMap<String, String> map = new HashMap<>();

    static {
        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    /**
     * 设置公共参数
     * @param isAddUserType 是否添加user_type参数
     * @return Map
     */
    public static Map<String, String> getBasicMap(boolean isAddUserType) {
        if (map == null)
            map = new LinkedHashMap<>();
        else
            map.clear();

        map.put("token", UserManager.getInstance().getToken());
        map.put("secret_key", AppConstants.SECRET_KEY);
        if (isAddUserType)
            map.put("user_type", USER_TYPE);

        //打印MAP
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            LogUtil.d(API_TAG, "- - - 基本请求参数 ====>" + entry.getKey() + " : " + entry.getValue());
        }
        return map;
    }
}
