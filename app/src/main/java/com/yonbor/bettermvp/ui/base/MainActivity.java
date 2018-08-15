package com.yonbor.bettermvp.ui.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yonbor.baselib.utils.LogUtils;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.app.AppConstant;
import com.yonbor.bettermvp.base.BaseActivity;
import com.yonbor.bettermvp.model.TabEntity;
import com.yonbor.bettermvp.ui.base.fragment.HomeFragment;
import com.yonbor.bettermvp.ui.base.fragment.MessageFragment;
import com.yonbor.bettermvp.ui.base.fragment.MyFragment;
import com.yonbor.bettermvp.ui.base.fragment.ServiceFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"首页", "消息", "服务", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_foot1_normal, R.drawable.ic_foot2_normal, R.drawable.ic_foot3_normal, R.drawable.ic_foot4_normal};
    private int[] mIconSelectIds = {
            R.drawable.ic_foot1_selected, R.drawable.ic_foot2_selected, R.drawable.ic_foot3_selected, R.drawable.ic_foot4_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private ServiceFragment serviceFragment;
    private MyFragment myFragment;
    private static int tabLayoutHeight;

    /**
     * 入口
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }


    @Override
    public void initView() {
        //初始化菜单
        initTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0, 0);
        tabLayoutHeight = tabLayout.getMeasuredHeight();
    }


    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    /**
     * 菜单显示隐藏动画
     */
    private void startAnimation(boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentByTag("messageFragment");
            serviceFragment = (ServiceFragment) getSupportFragmentManager().findFragmentByTag("serviceFragment");
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("myFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            homeFragment = new HomeFragment();
            messageFragment = new MessageFragment();
            serviceFragment = new ServiceFragment();
            myFragment = new MyFragment();

            transaction.add(R.id.fl_body, homeFragment, "homeFragment");
            transaction.add(R.id.fl_body, messageFragment, "messageFragment");
            transaction.add(R.id.fl_body, serviceFragment, "serviceFragment");
            transaction.add(R.id.fl_body, myFragment, "myFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(messageFragment);
                transaction.hide(serviceFragment);
                transaction.hide(myFragment);
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //消息
            case 1:
                transaction.hide(homeFragment);
                transaction.hide(serviceFragment);
                transaction.hide(myFragment);
                transaction.show(messageFragment);
                transaction.commitAllowingStateLoss();
                break;
            //服务
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(messageFragment);
                transaction.hide(myFragment);
                transaction.show(serviceFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(messageFragment);
                transaction.hide(serviceFragment);
                transaction.show(myFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }


    /**
     * 监听返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 非正常退出前保存位置
        if (tabLayout != null) {
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
