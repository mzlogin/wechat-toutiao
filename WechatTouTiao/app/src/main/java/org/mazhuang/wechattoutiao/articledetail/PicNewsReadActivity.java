package org.mazhuang.wechattoutiao.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mazhuang.wechattoutiao.R;

public class PicNewsReadActivity extends AppCompatActivity {

    public static final String URL = "url";

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
                mUrl = args.getString(URL, null);
            }
        }

        if (mUrl != null) {
            webView.loadUrl(mUrl);
        }
    }
}
