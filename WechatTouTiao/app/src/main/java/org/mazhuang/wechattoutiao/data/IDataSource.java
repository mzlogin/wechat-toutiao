package org.mazhuang.wechattoutiao.data;

import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/25.
 */

public interface IDataSource {

    interface LoadChannelsCallback {
        void onChannelsLoaded(List<WxChannel> channels);
        void onDataNotAvailable();
    }

    interface LoadArticlesCallBack {
        void onArticlesLoaded(List<WxArticle> articles);
        void onDataNotAvailable();
    }

    void getChannels(LoadChannelsCallback callback);

    void getArticles(WxChannel channelInfo, LoadArticlesCallBack callback);

    void getMoreArticles(WxChannel channelInfo, LoadArticlesCallBack callback);
}
