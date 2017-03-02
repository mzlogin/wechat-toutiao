package org.mazhuang.wechattoutiao.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.base.BaseActivity;

public class PicNewsReadActivity extends BaseActivity {

    public static final String PARAM_URL = "url";

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_news_read);

        WebView webView = (WebView) findViewById(R.id.web_content);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        if (intent != null) {
            Bundle args = intent.getExtras();
            if (args != null) {
                mUrl = args.getString(PARAM_URL, null);
            }
        }

        if (mUrl != null) {
            webView.loadUrl(mUrl);
        }
    }
}
