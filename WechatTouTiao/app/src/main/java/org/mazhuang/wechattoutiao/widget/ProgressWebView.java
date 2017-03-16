package org.mazhuang.wechattoutiao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import org.mazhuang.wechattoutiao.R;

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
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int defaultProgressHeight = (int)(3 * displayMetrics.density + 0.5);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressWebView);
        int progressHeightPixels = defaultProgressHeight;
        Drawable progressDrawable = null;
        try {
            progressHeightPixels = ta.getDimensionPixelSize(R.styleable.ProgressWebView_progressHeight, defaultProgressHeight);
            progressDrawable = ta.getDrawable(R.styleable.ProgressWebView_progressDrawable);
        } finally {
            ta.recycle();
        }

        if (progressDrawable == null) {
            progressDrawable = getResources().getDrawable(android.R.drawable.progress_horizontal);
        }


        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, progressHeightPixels, 0, 0);
        mProgressBar.setLayoutParams(lp);
        mProgressBar.setProgressDrawable(progressDrawable);
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
