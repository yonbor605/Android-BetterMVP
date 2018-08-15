package com.yonbor.bettermvp.ui.app.home.example;


import android.util.Log;

import com.yonbor.baselib.base.CreatePresenter;
import com.yonbor.baselib.base.PresenterVariable;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseActivity;
import com.yonbor.bettermvp.ui.app.home.login.LoginPresenter;
import com.yonbor.bettermvp.ui.app.home.login.LoginView;
import com.yonbor.bettermvp.ui.app.home.register.RegisterPresenter;
import com.yonbor.bettermvp.ui.app.home.register.RegisterView;

/**
 * 例子1：多个Presenter和使用@PresenterVariable注解的方式获取presenter实例
 * Activity 继承 BaseActivity 并实现你的 View 接口
 */
@CreatePresenter(presenter = {LoginPresenter.class, RegisterPresenter.class})
public class ExampleActivity1 extends BaseActivity implements LoginView, RegisterView {


    @PresenterVariable
    private LoginPresenter mLoginPresenter;
    @PresenterVariable
    private RegisterPresenter mRegisterPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_example1;
    }

    @Override
    protected void initView() {
        mLoginPresenter.login();
        mRegisterPresenter.register();
    }


    @Override
    public void loginSuccess() {
        Log.d("ExampleActivity1", "登录成功");
        showToast("登录成功");
    }

    @Override
    public void registerSuccess() {
        Log.d("ExampleActivity1", "注册成功");
        showToast("注册成功");
    }
}
