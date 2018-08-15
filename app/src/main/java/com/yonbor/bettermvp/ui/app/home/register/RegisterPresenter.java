package com.yonbor.bettermvp.ui.app.home.register;

import com.yonbor.baselib.base.BasePresenter;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/10 15:40
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    public void register() {
        mView.registerSuccess();
    }
}
