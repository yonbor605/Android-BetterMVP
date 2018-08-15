package com.yonbor.baselib.base;


public interface BaseView {

    void showLoading(boolean isShow);

    void showError(String msg);

    void complete();
}
