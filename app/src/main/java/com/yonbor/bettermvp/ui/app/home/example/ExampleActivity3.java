package com.yonbor.bettermvp.ui.app.home.example;


import android.util.Log;

import com.yonbor.baselib.base.CreatePresenter;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseActivity;
import com.yonbor.bettermvp.ui.app.home.login.LoginPresenter;
import com.yonbor.bettermvp.ui.app.home.login.LoginView;

/**
 * 例子3：一个Presenter和使用 getPresenter 方法获取实例
 * 其中在 Activity 中，Presenter 实例的获取方法可以通过下面代码所示的通过 getPresenter 来获取，
 * 这种方法需要在 BaseActivity 后面 填入泛型参数你的 Presenter 实现类，比如下面所示的 LoginPresenter。
 * 除了这种方法，也可以通过注解的方式获取实例。
 */
@CreatePresenter(presenter = {LoginPresenter.class})
public class ExampleActivity3 extends BaseActivity<LoginPresenter> implements LoginView {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_example1;
    }

    @Override
    protected void initView() {
       getPresenter().login();
    }


    @Override
    public void loginSuccess() {
        Log.d("ExampleActivity3", "登录成功");
        showToast("登录成功");
    }

}
