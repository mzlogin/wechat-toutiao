package org.mazhuang.wechattoutiao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mazhuang on 2017/3/15.
 */

public class ProgressWebView extends WebView {

    private ProgressBar mProgressBar;
    private WebChromeClient mWebChromeClient;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 6, 0, 0); // TODO 高度改为自定义属性
        mProgressBar.setLayoutParams(lp);
        mProgressBar.setProgressDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal)); // TODO 改为自定义属性
        addView(mProgressBar);
        mWebChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() != View.VISIBLE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        setWebChromeClient(mWebChromeClient); // TODO 解决可能被覆盖的问题
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) mProgressBar.getLayoutParams();
        if (lp != null) {
            lp.x = l;
            lp.y = t;
            mProgressBar.setLayoutParams(lp);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
