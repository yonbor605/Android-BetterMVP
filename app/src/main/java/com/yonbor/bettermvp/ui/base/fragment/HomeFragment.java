package com.yonbor.bettermvp.ui.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.yonbor.baselib.widght.NormalTitleBar;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseFragment;
import com.yonbor.bettermvp.ui.app.home.example.ExampleActivity1;
import com.yonbor.bettermvp.ui.app.home.news.NewsListActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Yonbor on 2018/08/10.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.btn_example)
    Button btnExample;
    @BindView(R.id.btn_news)
    Button btnNews;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        ntb.setBackVisibility(false);
        ntb.setTitleText("首页");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }





    @OnClick(R.id.btn_example)
    public void onClick() {
        Intent intent = new Intent(getActivity(), ExampleActivity1.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.btn_news)
    public void onClick2() {
        Intent intent = new Intent(getActivity(), NewsListActivity.class);
        getActivity().startActivity(intent);
    }


}
