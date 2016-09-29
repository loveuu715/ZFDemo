package com.loveuu.vv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.loveuu.vv.app.UserManager;
import com.loveuu.vv.mvp.activity.LoginActivity;
import com.loveuu.vv.utils.SceneManager;

/**
 * Created by VV on 2016/9/21.
 * 闪屏页面
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(UserManager.getInstance().getToken())) {
                    SceneManager.toScene(SplashActivity.this, LoginActivity.class, null);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
//                    SceneManager.toScene(SplashActivity.this, MainActivity.class, null);
                }
                finish();
            }
        }, 2000);
    }
}
