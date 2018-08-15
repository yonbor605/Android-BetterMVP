package com.yonbor.bettermvp.ui.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yonbor.baselib.widght.NormalTitleBar;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.base.BaseActivity;


/**
 * 全局公用的WebActivity
 */
public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";
    public static final String URL = "url";
    public static final String TITLE = "title";
    protected WebView web;
    protected ProgressBar emptyProgress;
    protected String url;
    protected String title;

    protected NormalTitleBar ntb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(URL);
        Log.d(TAG, url);
        title = getIntent().getStringExtra(TITLE);
        findView();
        if (!TextUtils.isEmpty(url)) {
            web.loadUrl(url);
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        ntb = (NormalTitleBar) findViewById(R.id.ntb);
        emptyProgress = (ProgressBar) findViewById(R.id.emptyProgress);
        web = (WebView) findViewById(R.id.webview);
    }

    private void findView() {
        ntb.setBackVisibility(true);
        ntb.setTitleText(TextUtils.isEmpty(title) ? "" : title);
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (web.canGoBack()) {
                    web.goBack();
                    return;
                }
                finish();
            }
        });

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

        addJavascriptInterface(web);
        // web.addJavascriptInterface(new InJavaScript(), "health");
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.requestFocus();
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    emptyProgress.setVisibility(View.GONE);
                }
            }
        });

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.local_obj.showSource(document.head.innerHTML" +
//                        " || document.getElementsByTagName('head')[0].innerHTML);");
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }
        });

    }

    protected void addJavascriptInterface(WebView webView) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

}
