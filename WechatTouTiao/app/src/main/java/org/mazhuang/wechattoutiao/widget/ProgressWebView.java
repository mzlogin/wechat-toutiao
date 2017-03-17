package org.mazhuang.wechattoutiao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import org.mazhuang.wechattoutiao.R;

/**
 * Created by mazhuang on 2017/3/15.
 */

public class ProgressWebView extends WebView {

    private ProgressBar mProgressBar;

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

        setWebChromeClient(new ProgressWebChromeClient(null));
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

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        super.setWebChromeClient(new ProgressWebChromeClient(client));
    }

    private class ProgressWebChromeClient extends WebChromeClient {
        private WebChromeClient mBase;

        ProgressWebChromeClient(WebChromeClient webChromeClient) {
            mBase = (webChromeClient == null) ? new WebChromeClient() : webChromeClient;
        }

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

            mBase.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            mBase.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            mBase.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            mBase.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            mBase.onShowCustomView(view, callback);
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            mBase.onShowCustomView(view, requestedOrientation, callback);
        }

        @Override
        public void onHideCustomView() {
            mBase.onHideCustomView();
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return mBase.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onRequestFocus(WebView view) {
            mBase.onRequestFocus(view);
        }

        @Override
        public void onCloseWindow(WebView window) {
            mBase.onCloseWindow(window);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return mBase.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return mBase.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return mBase.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return mBase.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
            mBase.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }

        @Override
        public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
            mBase.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            mBase.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            mBase.onGeolocationPermissionsHidePrompt();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPermissionRequest(PermissionRequest request) {
            mBase.onPermissionRequest(request);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPermissionRequestCanceled(PermissionRequest request) {
            mBase.onPermissionRequestCanceled(request);
        }

        @Override
        public boolean onJsTimeout() {
            return mBase.onJsTimeout();
        }

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            mBase.onConsoleMessage(message, lineNumber, sourceID);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return mBase.onConsoleMessage(consoleMessage);
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            return mBase.getDefaultVideoPoster();
        }

        @Override
        public View getVideoLoadingProgressView() {
            return mBase.getVideoLoadingProgressView();
        }

        @Override
        public void getVisitedHistory(ValueCallback<String[]> callback) {
            mBase.getVisitedHistory(callback);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return mBase.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }
}
