package org.mazhuang.wechattoutiao.data.source.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.model.realm.RealmArticles;
import org.mazhuang.wechattoutiao.data.model.realm.RealmChannels;

import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mazhuang on 2017/2/25.
 */

public class LocalDataSource implements IDataSource {

    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public void getChannels(final LoadChannelsCallback callback) {
        final List<WxChannel> channels = new ArrayList<>();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<RealmChannels> result =
                        realm.where(RealmChannels.class).equalTo("id", 1).findAll();
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<List<WxChannel>>(){}.getType();
                for (RealmChannels realmChannels : result) {
                    List<WxChannel> channelList = gson.fromJson(realmChannels.gsonContent, type);
                    channels.addAll(channelList);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (channels.size() > 0) {
                    callback.onChannelsLoaded(channels);
                } else {
                    callback.onDataNotAvailable();
                }
            }
        });
    }

    @Override
    public void getArticles(final WxChannel channelInfo, int endStreamId, boolean focusRefresh, final LoadArticlesCallBack callback) {
        final List<WxArticle> articles = new ArrayList<>();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<RealmArticles> result =
                        realm.where(RealmArticles.class).equalTo("channelId", channelInfo.id).findAll();
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<List<WxArticle>>(){}.getType();
                for (RealmArticles realmArticles : result) {
                    List<WxArticle> arts = gson.fromJson(realmArticles.gsonContent, type);
                    articles.addAll(arts);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (articles.size() > 0) {
                    callback.onArticlesLoaded(articles, 0);
                } else {
                    callback.onDataNotAvailable();
                }
            }
        });
    }

    @Override
    public void getMoreArticles(WxChannel channelInfo, long startTime, int endStreamId, LoadArticlesCallBack callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void saveAll(final List<WxChannel> channels, final Map<Integer, List<WxArticle>> articles) {
        final Gson gson = new GsonBuilder().create();
        final Type channelsType = new TypeToken<List<WxChannel>>(){}.getType();
        final Type articlesType = new TypeToken<List<WxArticle>>(){}.getType();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (channels != null) {
                    RealmChannels channelList = new RealmChannels();
                    channelList.id = 1;
                    channelList.gsonContent = gson.toJson(channels, channelsType);
                    realm.copyToRealmOrUpdate(channelList);
                }

                if (articles != null) {
                    for (Map.Entry<Integer, List<WxArticle>> entry : articles.entrySet()) {
                        RealmArticles articleList = new RealmArticles();
                        articleList.channelId = entry.getKey();
                        articleList.gsonContent = gson.toJson(entry.getValue(), articlesType);
                        realm.copyToRealmOrUpdate(articleList);
                    }
                }
            }
        });
    }
}
