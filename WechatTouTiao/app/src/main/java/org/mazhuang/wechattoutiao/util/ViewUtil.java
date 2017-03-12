package org.mazhuang.wechattoutiao.util;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mazhuang on 2017/3/12.
 */

public abstract class ViewUtil {
    public static void setupWebView(Activity activity, WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.addJavascriptInterface(new JSInvoker(activity), "JSInvoker");

        String ua = webSettings.getUserAgentString();
        ua += " SogouSearch Android1.0 version3.0 AppVersion/5101";
        webSettings.setUserAgentString(ua);
    }
}
