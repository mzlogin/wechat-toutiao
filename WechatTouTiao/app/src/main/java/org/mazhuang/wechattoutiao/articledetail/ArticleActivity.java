package org.mazhuang.wechattoutiao.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.util.ViewUtil;

public class ArticleActivity extends BaseActivity {

    public static final String PARAM_URL = "url";
    public static final String PARAM_TITLE = "title";

    private String mUrl;
    private String mTitle;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle args = intent.getExtras();
            if (args != null) {
                mUrl = args.getString(PARAM_URL, null);
                mTitle = args.getString(PARAM_TITLE, null);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar(toolbar, mTitle, mUrl);

        mWebView = (WebView) findViewById(R.id.web_content);
        ViewUtil.setupWebView(this, mWebView);

        if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        }
    }
}
