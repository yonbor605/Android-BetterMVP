package com.yonbor.bettermvp.app;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.yonbor.baselib.base.BaseApplication;
import com.yonbor.baselib.log.LogUtil;
import com.yonbor.bettermvp.BuildConfig;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class AppApplication extends BaseApplication {

    public static AppApplication appApplication;
    /**
     * 维护Activity的List
     */
    private static List<Activity> mActivities = Collections.synchronizedList(new LinkedList<Activity>());

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        /**初始化一些应用配置*/
        init();

    }

    private void init() {

        /**初始化logger*/
        LogUtil.logInit(BuildConfig.LOG_DEBUG);

        /**注册ActivityListener*/
        registerActivityListener();
    }


    /**
     * 注册ActivityListener
     */
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    if (null == mActivities) {
                        return;
                    }
                    mActivities.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activity && mActivities.isEmpty()) {
                        return;
                    }
                    if (mActivities.contains(activity)) {
                        mActivities.remove(activity);
                    }
                }
            });
        }
    }


    public static AppApplication getApplication() {
        return appApplication;
    }

    public static void finishAllActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }


    /**
     * 获取当前Activity
     */
    public static Activity getCurrentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return null;
        }
        Activity activity = mActivities.get(mActivities.size() - 1);
        return activity;
    }
}
