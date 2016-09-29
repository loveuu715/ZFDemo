package com.loveuu.vv.mvp.activity;

import android.content.Intent;
import android.view.View;

import com.loveuu.vv.MainActivity;
import com.loveuu.vv.R;
import com.loveuu.vv.base.BaseActivity;
import com.loveuu.vv.mvp.contract.LogingContract;
import com.loveuu.vv.mvp.presenter.LoginPresenter;
import com.loveuu.vv.utils.ActivityManager;
import com.loveuu.vv.utils.DialogManager;
import com.loveuu.vv.utils.TipUtil;
import com.loveuu.vv.widget.edittext.DelEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by VV on 2016/9/27.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements LogingContract.View {

    private LogingContract.Presenter mLoginPresenter;

    @BindView(R.id.det_login_account)
    DelEditText mDelEtAccount;
    @BindView(R.id.det_login_password)
    DelEditText mDelEtPassword;


    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        mLoginPresenter = new LoginPresenter(this);
        mDelEtPassword.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityManager.getInstances().removeActivityFromStackTop(LoginActivity.this);
                ActivityManager.getInstances().finishAll();
                ActivityManager.getInstances().activityEnqueue(LoginActivity.this);
            }
        }, 200);

    }

    @OnClick({R.id.tv_forget_pass, R.id.btn_login, R.id.btn_new_regist})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String account = mDelEtAccount.getText().toString().trim();
                String pwd = mDelEtPassword.getText().toString().trim();
                mLoginPresenter.login(account, pwd);
                break;
            case R.id.btn_new_regist:
                break;
            case R.id.tv_forget_pass:
                break;
        }
    }


    @Override
    public void showProgress(String msg) {
        DialogManager.showLoadingDialog(this, "正在登录");
    }

    @Override
    public void hideProgress() {
        DialogManager.dismissLoadingDialog();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
//        SceneManager.toScene(this, MainActivity.class, null);
        finish();
    }

    @Override
    public void loginError(int errorCode, String errorMsg) {
        DialogManager.dismissLoadingDialog();
        TipUtil.showToast(this, errorMsg);
    }

    @Override
    public void onNetworkError(String msg) {
        TipUtil.showToast(this, msg);
    }

    @Override
    public void setPresenter(LogingContract.Presenter presenter) {
        this.mLoginPresenter = presenter;
    }
}
