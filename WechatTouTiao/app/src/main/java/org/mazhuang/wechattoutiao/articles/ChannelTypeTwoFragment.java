package org.mazhuang.wechattoutiao.articles;

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

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String ua = webSettings.getUserAgentString();
        ua += " SogouSearch Android1.0 version3.0 AppVersion/5101";
        webSettings.setUserAgentString(ua);

        mWebView.addJavascriptInterface(new JSInvoker(), "JSInvoker");
        mWebView.setWebViewClient(new WebViewClient());

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

    public class JSInvoker {
        @JavascriptInterface
        public void openPictureNews(String entity) {
            String url = null;
            try {
                JSONObject jsonObject = new JSONObject(entity);
                String link = jsonObject.optString("link");
                String openLink = jsonObject.optString("open_link");
                if (!TextUtils.isEmpty(openLink)) {
                    url = openLink;
                } else {
                    url = link;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(url)) {
                return;
            }

            Context context = ChannelTypeTwoFragment.this.getContext();
            Intent intent = new Intent(context, PicNewsReadActivity.class);
            intent.putExtra(PicNewsReadActivity.URL, url);
            startActivity(intent);
        }
    }
}
