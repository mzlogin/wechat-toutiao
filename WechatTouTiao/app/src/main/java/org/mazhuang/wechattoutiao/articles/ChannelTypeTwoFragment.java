package org.mazhuang.wechattoutiao.articles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articledetail.PicNewsReadActivity;
import org.mazhuang.wechattoutiao.util.JSInvoker;
import org.mazhuang.wechattoutiao.util.ViewUtil;

import java.lang.ref.WeakReference;

/**
 * Created by mazhuang on 2017/2/13.
 */

public class ChannelTypeTwoFragment extends BaseFragment {

    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fetchArguments();

        View viewRoot = inflater.inflate(R.layout.fragment_channel_type_two, container, false);

        mWebView = (WebView) viewRoot.findViewById(R.id.web_content);
        ViewUtil.setupWebView(getActivity(), mWebView);

        if (mChannelInfo.h5_link != null) {
            mWebView.loadUrl(mChannelInfo.h5_link);
        }

        return viewRoot;
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onBackPressed();
    }
}
