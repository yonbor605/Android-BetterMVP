package com.yonbor.bettermvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yonbor.baselib.base.BasePresenter;
import com.yonbor.baselib.base.BaseView;
import com.yonbor.baselib.base.PresenterDispatch;
import com.yonbor.baselib.base.PresenterProviders;
import com.yonbor.baselib.widght.StatusBarCompat;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.app.AppApplication;

import butterknife.ButterKnife;


/**
 * 本架构使用可参考 https://github.com/lizixian18/EasyMvp
 * base基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(this,this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        initView();
    }



    protected abstract int getLayoutId();

    protected abstract void initView();

    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void complete() {

    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(AppApplication.getApplication(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 默认着色状态栏
        SetStatusBarColor();
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_color));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
    }
}
