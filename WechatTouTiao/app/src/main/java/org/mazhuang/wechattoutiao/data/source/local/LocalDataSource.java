package org.mazhuang.wechattoutiao.data.source.local;

import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

/**
 * Created by mazhuang on 2017/2/25.
 */

public class LocalDataSource implements IDataSource {

    @Override
    public void getChannels(LoadChannelsCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void getArticles(WxChannel channelInfo, LoadArticlesCallBack callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void getMoreArticles(WxChannel channelInfo, LoadArticlesCallBack callback) {
        callback.onDataNotAvailable();
    }
}
