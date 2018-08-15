package com.yonbor.bettermvp.ui.app.home.example;


import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseActivity;

/**
 * 例子5：Fragment
 */
public class ExampleActivity5 extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initView() {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, new ExampleFragment())
                .commit();
    }
}
