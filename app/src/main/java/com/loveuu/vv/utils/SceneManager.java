package com.loveuu.vv.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.loveuu.vv.R;

/**
 * Created by VV on 2016/9/21.
 */
public class SceneManager {

    public static void toScene(Context context, Class<? extends Activity> target, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        if (data != null) {
            intent.putExtras(data);
        }
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.b_enter_anim, R.anim.b_exit_anim);
        }
    }
}
