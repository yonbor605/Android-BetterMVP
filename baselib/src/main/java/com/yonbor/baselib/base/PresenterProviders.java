package com.yonbor.baselib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 这个类的作用是解析用到的注解以及完成绑定和解绑 View 等一些公共的 Presenter 操作
 */
public class PresenterProviders {

    private PresenterStore mPresenterStore = new PresenterStore<>();
    private Activity mActivity;
    private Fragment mFragment;
    private Class<?> mClass;

    public static PresenterProviders inject(Activity activity) {
        return new PresenterProviders(activity, null);
    }

    public static PresenterProviders inject(Fragment fragment) {
        return new PresenterProviders(null, fragment);
    }

    private PresenterProviders(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.mActivity = activity;
            mClass = this.mActivity.getClass();
        }
        if (fragment != null) {
            this.mFragment = fragment;
            mClass = this.mFragment.getClass();
        }
        resolveCreatePresenter();
        resolvePresenterVariable();
    }

    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request PresenterProviders before onCreate call.");
        }
        return application;
    }

    private static Activity checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create PresenterProviders for detached fragment");
        }
        return activity;
    }

    private static Context checkContext(Context context) {
        Context resultContent = null;
        if (context instanceof Activity) {
            resultContent = context;
        }
        if (resultContent == null) {
            throw new IllegalStateException("Context must Activity Context");
        }
        return resultContent;
    }

    /**
     * resolveCreatePresenter() 方法主要的作用是解析 @CreatePresenter 注解，
     * 过程是这样的：首先获取到注解上所有定义的 class 对象数组 classes，然后 循环，
     * 取它们的 canonicalName 作为 key ,调用 newInstance 方法实例化后作为 value 存入上面说到的 HasMap 中。
     */
    private <P extends BasePresenter> PresenterProviders resolveCreatePresenter() {
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter != null) {

            Class<P>[] classes = (Class<P>[]) createPresenter.presenter();
            for (Class<P> clazz : classes) {
                String canonicalName = clazz.getCanonicalName();
                try {
                    mPresenterStore.put(canonicalName, clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    /**
     * resolvePresenterVariable 方法主要的作用就是为将用 @PresenterVariable 注解标记的对象在 HashMap 中找到对应的实例，并赋值。
     * 过程是这样的，首先通过 getDeclaredFields 获取类上所以的变量的 Field，然后判断如果该变量有标记 @PresenterVariable 注解的话，
     * 就取它的 Type 对应的 Name，这个 Name 的值是会与 canonicalName 一样的，所以就可以通过它作为 key 在 HashMap 中查找对应的实例，
     * 找到后通过 Field 的 set 方法给变量赋值。
     */
    private <P extends BasePresenter> PresenterProviders resolvePresenterVariable() {
        for (Field field : mClass.getDeclaredFields()) {
            //获取字段上的注解
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1) {
                continue;
            }
            if (anns[0] instanceof PresenterVariable) {
                String canonicalName = field.getType().getName();
                P presenterInstance = (P) mPresenterStore.get(canonicalName);
                if (presenterInstance != null) {
                    try {
                        field.setAccessible(true);
                        field.set(mFragment != null ? mFragment : mActivity, presenterInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }


    public <P extends BasePresenter> P getPresenter(int index) {
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter == null) {
            return null;
        }
        if (createPresenter.presenter().length == 0) {
            return null;
        }
        if (index >= 0 && index < createPresenter.presenter().length) {
            String key = createPresenter.presenter()[index].getCanonicalName();
            BasePresenter presenter = mPresenterStore.get(key);
            if (presenter != null) {
                return (P) presenter;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public PresenterStore getPresenterStore() {
        return mPresenterStore;
    }
}
