package com.yonbor.bettermvp.ui.app.home.example;


import android.util.Log;

import com.yonbor.baselib.base.CreatePresenter;
import com.yonbor.baselib.base.PresenterVariable;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseFragment;
import com.yonbor.bettermvp.ui.app.home.login.LoginPresenter;
import com.yonbor.bettermvp.ui.app.home.login.LoginView;
import com.yonbor.bettermvp.ui.app.home.register.RegisterPresenter;
import com.yonbor.bettermvp.ui.app.home.register.RegisterView;


@CreatePresenter(presenter = {LoginPresenter.class, RegisterPresenter.class})
public class ExampleFragment extends BaseFragment implements LoginView, RegisterView {

    @PresenterVariable
    private LoginPresenter mLoginPresenter;
    @PresenterVariable
    private RegisterPresenter mRegisterPresenter;

    public ExampleFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example1;
    }

    @Override
    protected void initView() {
        mLoginPresenter.login();
        mRegisterPresenter.register();
    }


    @Override
    public void loginSuccess() {
        Log.d("ExampleFragment", "登录成功");
        showToast("登录成功");
    }

    @Override
    public void registerSuccess() {
        Log.d("ExampleFragment", "注册成功");
        showToast("注册成功");
    }
}
