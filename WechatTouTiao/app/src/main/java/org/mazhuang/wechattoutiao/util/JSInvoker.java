package org.mazhuang.wechattoutiao.util;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articledetail.ArticleActivity;
import org.mazhuang.wechattoutiao.articledetail.PicNewsReadActivity;

import java.lang.ref.WeakReference;

/**
 * Created by mazhuang on 2017/3/12.
 */

public class JSInvoker {

    private WeakReference<Activity> mActivity;

    public JSInvoker(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    @JavascriptInterface
    public void openPictureNews(String entity) {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }

        String url = null;
        String title = null;
        try {
            JSONObject jsonObject = new JSONObject(entity);
            String link = jsonObject.optString("link");
            String openLink = jsonObject.optString("open_link");
            title = jsonObject.optString("title");
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

        Intent intent = new Intent(activity, PicNewsReadActivity.class);
        intent.putExtra(PicNewsReadActivity.PARAM_URL, url);
        intent.putExtra(PicNewsReadActivity.PARAM_TITLE, title);
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public String getTabbarHeight() {
        Activity activity = mActivity.get();
        if (activity == null) {
            return "";
        }

        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("topHeight", (double) activity.getResources().getDimension(R.dimen.titlebar_height));
            jSONObject.put("bottomHeight", (double) activity.getResources().getDimension(R.dimen.web_bottombar_height));
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    @JavascriptInterface
    public void openWeixinNews(String entity) {
        openUrl(entity);
    }

    @JavascriptInterface
    public void weixin_openWeixinNews(String entity) {
        openUrl(entity);
    }

    @JavascriptInterface
    public void weixin_openUrl(String entity) {
        openUrl(entity);
    }

    private void openUrl(String entity) {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }

        String url = null;
        String title = null;
        try {
            JSONObject jsonObject = new JSONObject(entity);
            String link = jsonObject.optString("link");
            String openLink = jsonObject.optString("open_link");
            title = jsonObject.optString("title");
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

        Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(ArticleActivity.PARAM_URL, url);
        intent.putExtra(ArticleActivity.PARAM_TITLE, title);
        activity.startActivity(intent);
    }
}