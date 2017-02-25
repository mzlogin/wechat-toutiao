package org.mazhuang.wechattoutiao.data.source.remote;

import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxArticlesResult;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;
import org.mazhuang.wechattoutiao.util.Security;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mazhuang on 2017/2/25.
 */

public class RemoteDataSource implements IDataSource {

    private static String TAG = RemoteDataSource.class.getSimpleName();

    private static String BASE_URL = "http://weixin.sa.sogou.com/";

    private static final String BODY_PREFIX = "req=";
    private static final String BODY_FIELD_NEEDCATLIST = "needcatlist";
    private static final String BODY_FIELD_ACTION = "action";
    private static final String BODY_FIELD_END_STREAM_ID = "end_stream_id";
    private static final String BODY_FIELD_CHANNEL = "channel";

    private Retrofit mRetrofit;

    public RemoteDataSource() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public void getChannels(final LoadChannelsCallback callback) {
        getChannels(new Observer<WxChannelsResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WxChannelsResult wxChannelsResult) {
                if (wxChannelsResult.isSuccessful()) {
                    callback.onChannelsLoaded(wxChannelsResult.result.channel_list);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getArticles(WxChannel channelInfo, final LoadArticlesCallBack callback) {
        getArticles(channelInfo, new Observer<WxArticlesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WxArticlesResult wxArticlesResult) {
                if (wxArticlesResult.isSuccessful()) {
                    callback.onArticlesLoaded(wxArticlesResult.result.article_list);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getMoreArticles(WxChannel channelInfo, LoadArticlesCallBack callback) {
        throw new OnErrorNotImplementedException(new Throwable("Not implement yet"));
    }

    private void getChannels(Observer<WxChannelsResult> observer) {
        try {
            JSONObject jsonObject = getChannelsBody();

            Log.d(TAG, "getChannels jsonObject: " + jsonObject.toString());

            RequestBody body = RequestBody.create(null, (BODY_PREFIX + Security.aesEnc(jsonObject.toString())).getBytes());

            WxService request = mRetrofit.create(WxService.class);

            request.getChannels("ed2b99000712042888", body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getArticles(WxChannel channelInfo, Observer<WxArticlesResult> observer) {
        try {
            JSONObject jsonObject = getArticlesBody(channelInfo);

            Log.d(TAG, "getArticles jsonObject: " + jsonObject.toString());

            RequestBody body = RequestBody.create(null, (BODY_PREFIX + Security.aesEnc(jsonObject.toString())).getBytes());

            WxService request = mRetrofit.create(WxService.class);

            request.getArticles("ed2b99000712042888", body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getCommonBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mid", "ed2b99000712042888");
        jsonObject.put("xid", "303b00c0881f01da1148bc8d8cc7c930387a");
        jsonObject.put("imsi", "460030971693100");
        jsonObject.put("timeMillis", String.valueOf(System.currentTimeMillis()));
        jsonObject.put("os", "android");
        jsonObject.put("req_ver", 3);
        jsonObject.put("ver", "5101");
        jsonObject.put("os_ver", Build.VERSION.SDK_INT);
        jsonObject.put("location", "116.331,39.993");

        JSONObject user = new JSONObject();
        user.put("network", 1);
        user.put("model", "MI 5");
        user.put("screen_width", 1080);
        user.put("distribution", "3028");

        jsonObject.put("userinfo", user);

        return jsonObject;
    }

    public static JSONObject getChannelsBody() throws JSONException {
        JSONObject jsonObject = getCommonBody();
        jsonObject.put(BODY_FIELD_NEEDCATLIST, true);

        return jsonObject;
    }

    public static JSONObject getArticlesBody(WxChannel channel) throws JSONException {
        JSONObject jsonObject = getCommonBody();
        jsonObject.put(BODY_FIELD_NEEDCATLIST, false);
        jsonObject.put(BODY_FIELD_ACTION, 1);
        jsonObject.put(BODY_FIELD_END_STREAM_ID, -1);

        JSONObject channelObj = new JSONObject();
        channelObj.put("id", channel.id);
        channelObj.put("name", channel.name);
        channelObj.put("subid", Integer.valueOf(channel.subid));

        jsonObject.put(BODY_FIELD_CHANNEL, channelObj);

        return jsonObject;
    }
}
