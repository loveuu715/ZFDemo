package com.loveuu.vv.app;

import com.loveuu.vv.utils.SPKeys;
import com.loveuu.vv.utils.SPUtil;

/**
 * Created by VV on 2016/9/22.
 * 用户本地信息管理类
 */

public class UserManager {

    private static UserManager sUserManager;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (sUserManager == null) {
            synchronized (UserManager.class) {
                if (sUserManager == null) {
                    sUserManager = new UserManager();
                }
            }
        }
        return sUserManager;
    }


    public String getToken() {
        return SPUtil.getString(SPKeys.KEY_TOKEN);
    }

    public void saveToken(String token) {
        SPUtil.putString(SPKeys.KEY_TOKEN, token);
    }

    public void clearToken() {
        SPUtil.removeValue(SPKeys.KEY_TOKEN);
    }

    public String getAccount(){
        return SPUtil.getString(SPKeys.KEY_ACCOUNT);
    }

    public void saveAccount(String account) {
        SPUtil.putString(SPKeys.KEY_ACCOUNT, account);
    }

    public void clearAccount(){
        SPUtil.removeValue(SPKeys.KEY_ACCOUNT);
    }

    public int getUserType(){
        return SPUtil.getInt(SPKeys.KEY_USER_TYPE);
    }

    public void saveUserType(int type) {
        SPUtil.putInt(SPKeys.KEY_USER_TYPE, type);
    }

    public void clearUserType(){
        SPUtil.removeValue(SPKeys.KEY_USER_TYPE);
    }

    public String getSign(){
        return SPUtil.getString(SPKeys.KEY_SIGN);
    }

    public void saveSign(String sign) {
        SPUtil.putString(SPKeys.KEY_SIGN, sign);
    }

    public void clearSign(){
        SPUtil.removeValue(SPKeys.KEY_SIGN);
    }

    public void saveIdentifier(String identifier) {
        SPUtil.putString(SPKeys.KEY_IDENTIFIER, identifier);
    }

    public String getIdentifier() {
        return SPUtil.getString(SPKeys.KEY_IDENTIFIER);
    }

    public void clearIdentifier(){
        SPUtil.removeValue(SPKeys.KEY_IDENTIFIER);
    }

    public void saveIsMobile(boolean isMobile){
        SPUtil.putBoolean(SPKeys.KEY_IS_MOBILE, isMobile);
    }

    public boolean isMobile(){
        return SPUtil.getBoolean(SPKeys.KEY_IS_MOBILE);
    }

    public void clearIsMobile(){
        SPUtil.removeValue(SPKeys.KEY_IS_MOBILE);
    }

    public void saveIsZhongYuan(boolean isZhongYuan){
        SPUtil.putBoolean(SPKeys.KEY_IS_ZHOGNYUAN, isZhongYuan);
    }

    public boolean isZhongYuan(){
        return SPUtil.getBoolean(SPKeys.KEY_IS_ZHOGNYUAN);
    }

    public void clearIsZhongYuan(){
        SPUtil.removeValue(SPKeys.KEY_IS_ZHOGNYUAN);
    }

    public void saveTime(String time){
        SPUtil.putString(SPKeys.KEY_USER_TIME, time);
    }

    public String getTime(){
        return SPUtil.getString(SPKeys.KEY_USER_TIME);
    }

    public void clearTime(){
        SPUtil.removeValue(SPKeys.KEY_USER_TIME);
    }

    public void clearUserInfo(){
        clearAccount();
        clearIdentifier();
        clearIsMobile();
        clearIsZhongYuan();
        clearSign();
        clearToken();
        clearUserType();
        clearTime();
    }
}
