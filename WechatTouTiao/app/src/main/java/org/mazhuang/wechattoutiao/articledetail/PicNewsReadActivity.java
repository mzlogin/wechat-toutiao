package org.mazhuang.wechattoutiao.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articles.ChannelTypeTwoFragment;
import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.util.JSInvoker;
import org.mazhuang.wechattoutiao.util.ViewUtil;

public class PicNewsReadActivity extends BaseActivity {

    public static final String PARAM_URL = "url";
    public static final String PARAM_TITLE = "title";

    private String mUrl;
    private String mTitle;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_news_read);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle args = intent.getExtras();
            if (args != null) {
                mUrl = args.getString(PARAM_URL, null);
                mTitle = args.getString(PARAM_TITLE, null);
            }
        }

        mWebView = (WebView) findViewById(R.id.web_content);
        ViewUtil.setupWebView(this, mWebView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar(toolbar, mTitle, mUrl);

        if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        }
    }
}
