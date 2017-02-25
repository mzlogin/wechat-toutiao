package org.mazhuang.wechattoutiao.data;

import android.support.annotation.NonNull;

import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.source.local.LocalDataSource;
import org.mazhuang.wechattoutiao.data.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mazhuang on 2017/2/25.
 */

public class DataSource implements IDataSource {

    private IDataSource mLocalDataSource;

    private IDataSource mRemoteDataSource;

    List<WxChannel> mCachedChannels;

    Map<Integer, List<WxArticle>> mCachedArticles;

    private static class LazyHolder {
        private static DataSource INSTANCE =
                new DataSource(new LocalDataSource(), new RemoteDataSource());
    }

    private DataSource(@NonNull IDataSource localDataSource,
                       @NonNull IDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static DataSource getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void getChannels(@NonNull final LoadChannelsCallback callback) {
        if (mCachedChannels != null) {
            callback.onChannelsLoaded(mCachedChannels);
            return;
        }

        mLocalDataSource.getChannels(new LoadChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<WxChannel> channels) {
                refreshChannelsCache(channels);
                callback.onChannelsLoaded(channels);
            }

            @Override
            public void onDataNotAvailable() {
                getChannelsFromRemote(callback);
            }
        });
    }

    @Override
    public void getArticles(final WxChannel channelInfo,
                            @NonNull final LoadArticlesCallBack callback) {
        if (mCachedArticles != null && mCachedArticles.containsKey(channelInfo.id)) {
            callback.onArticlesLoaded(mCachedArticles.get(channelInfo.id));
            return;
        }

        mLocalDataSource.getArticles(
                channelInfo,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles) {
                        refreshArticlesCache(channelInfo.id, articles);
                        callback.onArticlesLoaded(articles);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        getArticlesFromRemote(channelInfo, callback);
                    }
        });

    }

    @Override
    public void getMoreArticles(WxChannel channelInfo,
                                @NonNull LoadArticlesCallBack callback) {

    }

    private void getChannelsFromRemote(@NonNull final LoadChannelsCallback callback) {
        mRemoteDataSource.getChannels(new LoadChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<WxChannel> channels) {
                refreshChannelsCache(channels);
                callback.onChannelsLoaded(channels);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getArticlesFromRemote(final WxChannel channelInfo,
                                       @NonNull final LoadArticlesCallBack callBack) {
        mRemoteDataSource.getArticles(
                channelInfo,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles) {
                        refreshArticlesCache(channelInfo.id, articles);
                        callBack.onArticlesLoaded(articles);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callBack.onDataNotAvailable();
                    }
                });
    }

    private void refreshChannelsCache(List<WxChannel> channels) {
        if (mCachedChannels == null) {
            mCachedChannels = new ArrayList<WxChannel>();
        }

        mCachedChannels.clear();

        mCachedChannels = channels;
    }

    private void refreshArticlesCache(int channelId, List<WxArticle> articles) {
        if (mCachedArticles == null) {
            mCachedArticles = new LinkedHashMap<>();
        }

        if (mCachedArticles.containsKey(channelId)) {
            mCachedArticles.get(channelId).clear();
        }

        mCachedArticles.put(channelId, articles);
    }
}
