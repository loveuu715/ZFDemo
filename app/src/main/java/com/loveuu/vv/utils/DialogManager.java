package com.loveuu.vv.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loveuu.vv.R;
import com.loveuu.vv.widget.dialog.LVCircularSmile;

/**
 * Created by VV on 2016/9/28.
 */

public class DialogManager {

    private static Dialog sLoadingDialog;

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

    public static void dismissLoadingDialog() {
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
    }

}
