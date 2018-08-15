package com.yonbor.bettermvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yonbor.baselib.base.BasePresenter;
import com.yonbor.baselib.base.BaseView;
import com.yonbor.baselib.base.PresenterDispatch;
import com.yonbor.baselib.base.PresenterProviders;
import com.yonbor.bettermvp.app.AppApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected View mRootView;
    protected LayoutInflater inflater;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;

    protected Context mContext;
    protected Activity mActivity;

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mActivity = getActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        isPrepared = true;
        initView();
        lazyLoad();
    }




    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }


    /**
     * 获取布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void initView();

    public View findViewById(int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 懒加载
     */
    protected void lazyLoadData() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
        mPresenterDispatch.detachView();
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(AppApplication.getApplication(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
