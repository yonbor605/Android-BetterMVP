package com.yonbor.bettermvp.ui.app.home.login;

import com.yonbor.baselib.base.BasePresenter;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/10 15:40
 * 编写 LoginPresenter 继承 BasePresenter 并实现你的 Presenter 功能方法
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public void login() {
        mView.loginSuccess();
    }
}
