package com.zhhl.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {

    private String uri = "file:///android_asset/index.html";
//    private String uri = "https://8psd6p.axshare.com/#g=1";

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatus));
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.mWeb);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);
//        File file = new File(getFilesDir().getAbsolutePath() + "/zhcl/login.html");

        webView.loadUrl(/*"file:///android_asset/login.html"*/uri);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 隐藏滚动条webView.requestFocus();
        webView.requestFocusFromTouch();

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);// 支持JS
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);//
        webView.getSettings().setLoadWithOverviewMode(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        mWebSettings.setBuiltInZoomControls(false);// 设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        mWebSettings.setBlockNetworkImage(true);// 首先阻塞图片，让图片不显示
        mWebSettings.setBlockNetworkImage(false);//  页面加载好以后，在放开图片：
        mWebSettings.setSupportMultipleWindows(false);// 设置同一个界面
        mWebSettings.setBlockNetworkImage(false);
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebSettings.setNeedInitialFocus(false);// 禁止webview上面控件获取焦点(黄色边框)

        String ua =mWebSettings.getUserAgentString();
        mWebSettings.setUserAgentString("AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0");

        // Mozilla/5.0 (Linux; Android 8.0.0; HWI-AL00 Build/HUAWEIHWI-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/64.0.3282.137 Mobile Safari/537.36
        Log.e( "onCreate: ",ua );
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载时
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束时
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("tenvideo2:")) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return true;
//                }
                view.loadUrl(url);
                /**
                 * 网页跳转：
                 * 1.在当前的webview跳转到新连接
                 * view.loadUrl(url);
                 * 2.调用系统浏览器跳转到新网页
                 * Intent i = new Intent(Intent.ACTION_VIEW);
                 * i.setData(Uri.parse(url));
                 * startActivity(i);
                 */
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // 获得网页的加载进度 newProgress为当前加载百分比
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                // 获取网页的title，客户端可以在这里动态修改页面的title
                // 另外，当加载错误时title为“找不到该网页”
                super.onReceivedTitle(view, title);
            }
        });
//        webView.addJavascriptInterface(new JSInterface(), "jsInterface");
//        JSInterface对象：


    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

