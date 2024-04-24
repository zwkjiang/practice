package com.example.textview;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.Contons;

@Route(path = Contons.WEB_VIEW)
public class MyWebViewActivity extends BaseActivity{

    private WebView webView;

    private RelativeLayout mRootView;

    private ImageView mBack;

    private ImageView mGo;

    private Button webBt;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void initView() {
        webBt = findViewById(R.id.web_bt);
        webView = findViewById(R.id.web_view);
        mRootView = findViewById(R.id.web_root_view);
        mBack = findViewById(R.id.web_back);
        mGo = findViewById(R.id.web_go);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.canGoBack();
        webView.canGoForward();
        webView.loadUrl("file:///android_asset/index.html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }

    @Override
    public void initListener() {
        mGo.setOnClickListener(this);
        mBack.setOnClickListener(this);
        webBt.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_back:
                webView.goBack();
                break;
            case R.id.web_go:
                webView.goForward();
                break;
            case R.id.web_bt:
                webView.loadUrl("javascript:toast()");
                break;
        }
    }

    @JavascriptInterface
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        mRootView.removeView(webView);
        webView.destroy();
        super.onDestroy();
    }
}
