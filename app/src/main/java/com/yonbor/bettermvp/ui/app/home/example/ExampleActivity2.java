package com.yonbor.bettermvp.ui.app.home.example;


import android.util.Log;

import com.yonbor.baselib.base.CreatePresenter;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseActivity;
import com.yonbor.bettermvp.ui.app.home.login.LoginPresenter;
import com.yonbor.bettermvp.ui.app.home.login.LoginView;
import com.yonbor.bettermvp.ui.app.home.register.RegisterPresenter;
import com.yonbor.bettermvp.ui.app.home.register.RegisterView;

/**
 * 例子2：多个Presenter和如果不喜欢注解，还可以通过直接获取的方式获取presenter实例
 * 通过 getPresenterProviders().getPresenter(int index) 方法获取，
 * 传入的参数是你通过 @CreatePresenter 注解传入的 class 对象的 数组下标
 */
@CreatePresenter(presenter = {LoginPresenter.class, RegisterPresenter.class})
public class ExampleActivity2 extends BaseActivity implements LoginView, RegisterView {


    private LoginPresenter mLoginPresenter;
    private RegisterPresenter mRegisterPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_example1;
    }

    @Override
    protected void initView() {
        mLoginPresenter = getPresenterProviders().getPresenter(0);
        mRegisterPresenter = getPresenterProviders().getPresenter(1);

        mLoginPresenter.login();
        mRegisterPresenter.register();
    }


    @Override
    public void loginSuccess() {
        Log.d("ExampleActivity2", "登录成功");
        showToast("登录成功");
    }

    @Override
    public void registerSuccess() {
        Log.d("ExampleActivity2", "注册成功");
        showToast("注册成功");
    }
}
