package com.loveuu.vv.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.app.UserManager;
import com.loveuu.vv.mvp.activity.LoginActivity;
import com.loveuu.vv.widget.dialog.LVCircularSmile;

/**
 * Created by VV on 2016/9/28.
 */

public class DialogManager {

    private static Dialog sLoadingDialog;
    private static Dialog sOfflineDialog;

    /**
     * 自定义progressDialog
     *
     * @param context
     * @param msg
     */
    public static void showLoadingDialog(Context context, String msg) {
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
        createLoadingDialog(context, msg).show();
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    private static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_loading, null);
        LVCircularSmile lvCircularSmile = (LVCircularSmile) v.findViewById(R.id.lv_loading_view);
        lvCircularSmile.startAnim();
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) v.findViewById(R.id.tv_loading_tip_msg);
            textView.setText(msg);
            textView.setVisibility(View.VISIBLE);
        }
        // 创建自定义样式dialog
        sLoadingDialog = new Dialog(context, R.style.style_loading_dialog);
        sLoadingDialog.setCancelable(true);// 不可以用“返回键”取消
        sLoadingDialog.setContentView(v, new LinearLayout.LayoutParams(UIUtils.dip2px(context, 100), UIUtils.dip2px(context, 100)));// 设置布局
        sLoadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        return sLoadingDialog;
    }

    /**
     * 隐藏自定义progressDialog
     */
    public static void dismissLoadingDialog() {
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
    }

    /**
     * 显示token失效dialog
     *
     * @param context
     */
    public static void showOfflineDialog(final Context context) {
        UserManager.getInstance().clearUserInfo();
        if (sOfflineDialog != null)
            return;
        sOfflineDialog = new Dialog(context, R.style.style_loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_offline, null);
        TextView textView = (TextView) v.findViewById(R.id.tv_dialog_offline_tip);
        v.findViewById(R.id.tv_dialog_offline_confirm_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOfflineDialog.dismiss();
                sOfflineDialog = null;
                SceneManager.toScene(context, LoginActivity.class, null);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtils.dip2px(context, 240), UIUtils.dip2px(context, 120));
        layoutParams.weight = Gravity.CENTER;
        sOfflineDialog.setContentView(v, layoutParams);// 设置布局
        sOfflineDialog.setCancelable(false);// 不可以用“返回键”取消
        sOfflineDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        sOfflineDialog.show();
    }

}
