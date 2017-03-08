package org.mazhuang.wechattoutiao.data;

import android.support.annotation.NonNull;

import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.source.local.LocalDataSource;
import org.mazhuang.wechattoutiao.data.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.Collections;
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

        getChannelsFromRemote(new LoadChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<WxChannel> channels) {
                callback.onChannelsLoaded(channels);
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getChannels(new LoadChannelsCallback() {
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
        });
    }

    @Override
    public void getArticles(final WxChannel channelInfo,
                            int endStreamId, // ignore
                            final boolean focusRefresh,
                            @NonNull final LoadArticlesCallBack callback) {
        if (focusRefresh) {
            getArticlesFromRemote(channelInfo, getEndStreamId(channelInfo.id), focusRefresh, callback);
        } else {
            if (mCachedArticles != null && mCachedArticles.containsKey(channelInfo.id)) {
                callback.onArticlesLoaded(mCachedArticles.get(channelInfo.id), 0);
                return;
            }

            mLocalDataSource.getArticles(
                    channelInfo,
                    getEndStreamId(channelInfo.id),
                    focusRefresh,
                    new LoadArticlesCallBack() {
                        @Override
                        public void onArticlesLoaded(List<WxArticle> articles, int addCount) {
                            addCount = addArticlesCache(channelInfo.id, articles);
                            callback.onArticlesLoaded(mCachedArticles.get(channelInfo.id), addCount);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            getArticlesFromRemote(channelInfo, getEndStreamId(channelInfo.id), focusRefresh, callback);
                        }
                    });
        }

    }

    @Override
    public void getMoreArticles(WxChannel channelInfo,
                                long startTime, // ignore
                                int endSteamId, // ignore
                                @NonNull LoadArticlesCallBack callback) {
        getMoreArticlesFromRemote(
                channelInfo,
                getMoreStartTime(channelInfo.id),
                getEndStreamId(channelInfo.id),
                callback);
    }

    @Override
    public void saveAll(List<WxChannel> channels, Map<Integer, List<WxArticle>> articles) {
        mLocalDataSource.saveAll(mCachedChannels, mCachedArticles);
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
                                       final boolean focusRefresh,
                                       @NonNull final LoadArticlesCallBack callBack) {
        mRemoteDataSource.getArticles(
                channelInfo,
                endStreamId,
                focusRefresh,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles, int addCount) {
                        if (articles == null || articles.size() == 0) {
                            if (!focusRefresh) {
                                callBack.onDataNotAvailable();
                                return;
                            }
                        } else {
                            addCount = addArticlesCache(channelInfo.id, articles);
                        }
                        callBack.onArticlesLoaded(mCachedArticles.get(channelInfo.id), addCount);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (!focusRefresh) {
                            callBack.onDataNotAvailable();
                        }
                    }
                });
    }

    private void getMoreArticlesFromRemote(final WxChannel channelInfo,
                                           long startTime,
                                           int endStreamId,
                                           @NonNull final LoadArticlesCallBack callBack) {
        mRemoteDataSource.getMoreArticles(
                channelInfo,
                startTime,
                endStreamId,
                new LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles, int addCount) {
                        if (articles != null && articles.size() != 0) {
                            addCount = addArticlesCache(channelInfo.id, articles);
                        }
                        callBack.onArticlesLoaded(mCachedArticles.get(channelInfo.id), addCount);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callBack.onArticlesLoaded(mCachedArticles.get(channelInfo.id), 0);
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

    private int addArticlesCache(int channelId, List<WxArticle> articles) {
        if (mCachedArticles == null) {
            mCachedArticles = new LinkedHashMap<>();
        }

        int addCount;

        if (!mCachedArticles.containsKey(channelId)) {
            mCachedArticles.put(channelId, articles);
            addCount = articles.size();
        } else {
            List<WxArticle> articleList = mCachedArticles.get(channelId);
            int oldCount = articleList.size();

            articleList.removeAll(articles);
            articleList.addAll(articles);
            Collections.sort(articleList);

            addCount = articleList.size() - oldCount;
        }

        return addCount;
    }

    private int getEndStreamId(int channelId) {
        int endStreamId = -1;
        if (mCachedArticles != null && mCachedArticles.containsKey(channelId)) {
            endStreamId = mCachedArticles.get(channelId).get(0).stream_id;
        }
        return endStreamId;
    }

    private long getMoreStartTime(int channelId) {
        long pubTime = 0;
        if (mCachedArticles != null && mCachedArticles.containsKey(channelId)) {
            List<WxArticle> list = mCachedArticles.get(channelId);
            pubTime = list.get(list.size() - 1).pub_time;
        }
        return pubTime;
    }
}
