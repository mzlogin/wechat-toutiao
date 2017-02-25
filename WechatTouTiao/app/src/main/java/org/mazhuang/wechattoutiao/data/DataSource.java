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
                            int endStreamId,
                            @NonNull final LoadArticlesCallBack callback) {
        if (mCachedArticles != null && mCachedArticles.containsKey(channelInfo.id)) {
            callback.onArticlesLoaded(mCachedArticles.get(channelInfo.id));
            return;
        }

        mLocalDataSource.getArticles(
                channelInfo,
                channelInfo.id,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles) {
                        refreshArticlesCache(channelInfo.id, articles);
                        callback.onArticlesLoaded(articles);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        getArticlesFromRemote(channelInfo, getEndStreamId(channelInfo.id), callback);
                    }
        });

    }

    @Override
    public void getMoreArticles(WxChannel channelInfo,
                                int endSteamId,
                                @NonNull LoadArticlesCallBack callback) {
        getMoreArticlesFromRemote(channelInfo, endSteamId, callback);
    }

    private void getChannelsFromRemote(@NonNull final LoadChannelsCallback callback) {
        mRemoteDataSource.getChannels(new LoadChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<WxChannel> channels) {
                if (channels == null || channels.size() == 0) {
                    callback.onDataNotAvailable();
                } else {
                    refreshChannelsCache(channels);
                    callback.onChannelsLoaded(channels);
                }
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getArticlesFromRemote(final WxChannel channelInfo,
                                       int endStreamId,
                                       @NonNull final LoadArticlesCallBack callBack) {
        mRemoteDataSource.getArticles(
                channelInfo,
                endStreamId,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles) {
                        if (articles == null || articles.size() == 0) {
                            callBack.onDataNotAvailable();
                        } else {
                            refreshArticlesCache(channelInfo.id, articles);
                            callBack.onArticlesLoaded(articles);
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callBack.onDataNotAvailable();
                    }
                });
    }

    private void getMoreArticlesFromRemote(final WxChannel channelInfo,
                                           int endStreamId,
                                           @NonNull final LoadArticlesCallBack callBack) {
        mRemoteDataSource.getMoreArticles(
                channelInfo,
                endStreamId,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles) {
                        if (articles == null || articles.size() == 0) {
                            callBack.onDataNotAvailable();
                        } else {
                            addArticlesCache(channelInfo.id, articles);
                            callBack.onArticlesLoaded(mCachedArticles.get(channelInfo.id));
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callBack.onArticlesLoaded(mCachedArticles.get(channelInfo.id));
                    }
                }
        );
    }

    private void refreshChannelsCache(List<WxChannel> channels) {

        if (mCachedChannels == null) {
            mCachedChannels = new ArrayList<>();
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

    private void addArticlesCache(int channelId, List<WxArticle> articles) {
        if (mCachedArticles == null) {
            mCachedArticles = new LinkedHashMap<>();
        }

        if (!mCachedArticles.containsKey(channelId)) {
            mCachedArticles.put(channelId, articles);
        } else {
            mCachedArticles.get(channelId).addAll(articles);
        }
    }

    private int getEndStreamId(int channelId) {
        int endStreamId = -1;
        if (mCachedArticles != null && mCachedArticles.containsKey(channelId)) {
            endStreamId = mCachedArticles.get(channelId).get(0).stream_id;
        }
        return endStreamId;
    }
}
