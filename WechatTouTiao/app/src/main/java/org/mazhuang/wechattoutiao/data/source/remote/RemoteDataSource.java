package org.mazhuang.wechattoutiao.data.source.remote;

import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxArticlesResult;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;
import org.mazhuang.wechattoutiao.util.DeviceInfo;
import org.mazhuang.wechattoutiao.util.Security;
import org.mazhuang.wechattoutiao.util.TimeUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    private static final String BODY_FIELD_START_TIME = "start_time";
    private static final String BODY_FIELD_START_STREAM_ID = "start_stream_id";
    private static final String BODY_FIELD_MID = "mid";
    private static final String BODY_FIELD_XID = "xid";
    private static final String BODY_FIELD_IMSI = "imsi";
    private static final String BODY_FIELD_TIMEMILLIS = "timeMillis";
    private static final String BODY_FIELD_OS = "os";
    private static final String BODY_FIELD_REQ_VER = "req_ver";
    private static final String BODY_FIELD_VER = "ver";
    private static final String BODY_FIELD_OS_VER = "os_ver";
    private static final String BODY_FIELD_LOCATION = "location";
    private static final String BODY_FIELD_USERINFO = "userinfo";

    private static final String BODY_FIELD_CHANNEL_ID = "id";
    private static final String BODY_FIELD_CHANNEL_NAME = "name";
    private static final String BODY_FIELD_CHANNEL_SUBID = "subid";

    private static final String BODY_FIELD_USERINFO_NETWORK = "network";
    private static final String BODY_FIELD_USERINFO_MODEL = "model";
    private static final String BODY_FIELD_USERINFO_SCREEN_WIDTH = "screen_width";
    private static final String BODY_FIELD_USERINFO_DISTRIBUTION = "distribution";

    private Retrofit mRetrofit;

    private DeviceInfo mDevice;

    public RemoteDataSource() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mDevice = DeviceInfo.getInstance();
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
    public void getArticles(WxChannel channelInfo,
                            int endStreamId,
                            boolean focusRefresh,
                            final LoadArticlesCallBack callback) {
        getArticles(channelInfo, endStreamId, new Observer<WxArticlesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WxArticlesResult wxArticlesResult) {
                if (wxArticlesResult.isSuccessful()) {
                    callback.onArticlesLoaded(wxArticlesResult.result.article_list, 0);
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
    public void getMoreArticles(WxChannel channelInfo, long startTime, int endStreamId, final LoadArticlesCallBack callback) {
        getMoreArticles(channelInfo, startTime, endStreamId, new Observer<WxArticlesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WxArticlesResult wxArticlesResult) {
                if (wxArticlesResult.isSuccessful()) {
                    callback.onArticlesLoaded(wxArticlesResult.result.article_list, 0);
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
    public void saveAll(List<WxChannel> channels, Map<Integer, List<WxArticle>> articles) {

    }

    private void getChannels(Observer<WxChannelsResult> observer) {
        try {
            JSONObject jsonObject = getChannelsBody();

            Log.v(TAG, "getChannels jsonObject: " + jsonObject.toString());

            RequestBody body = RequestBody.create(null, (BODY_PREFIX + Security.aesEnc(jsonObject.toString())).getBytes());

            WxService request = mRetrofit.create(WxService.class);

            request.getChannels(mDevice.getMid(), body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
            observer.onError(new Throwable());
        }
    }

    private void getArticles(WxChannel channelInfo, int endStreamId, Observer<WxArticlesResult> observer) {
        try {
            JSONObject jsonObject = getArticlesBody(channelInfo, endStreamId);

            Log.v(TAG, "getArticles jsonObject: " + jsonObject.toString());

            RequestBody body = RequestBody.create(null, (BODY_PREFIX + Security.aesEnc(jsonObject.toString())).getBytes());

            WxService request = mRetrofit.create(WxService.class);

            request.getArticles(mDevice.getMid(), body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
            observer.onError(new Throwable());
        }
    }

    private void getMoreArticles(WxChannel channelInfo, long startTime, int endStreamId, Observer<WxArticlesResult> observer) {
        try {
            JSONObject jsonObject = getMoreArticlesBody(channelInfo, startTime, endStreamId);

            Log.v(TAG, "getMoreArticles jsonObject: " + jsonObject.toString());

            RequestBody body = RequestBody.create(null, (BODY_PREFIX + Security.aesEnc(jsonObject.toString().getBytes())));

            WxService request = mRetrofit.create(WxService.class);

            request.getMoreArticles(mDevice.getMid(), body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
            observer.onError(new Throwable());
        }
    }

    public JSONObject getCommonBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(BODY_FIELD_MID, mDevice.getMid());
        jsonObject.put(BODY_FIELD_XID, mDevice.getXid());
        jsonObject.put(BODY_FIELD_IMSI, mDevice.getIMSI());
        jsonObject.put(BODY_FIELD_TIMEMILLIS, String.valueOf(System.currentTimeMillis()));
        jsonObject.put(BODY_FIELD_OS, "android");
        jsonObject.put(BODY_FIELD_REQ_VER, 3);
        jsonObject.put(BODY_FIELD_VER, "5101");
        jsonObject.put(BODY_FIELD_OS_VER, Build.VERSION.SDK_INT);
        jsonObject.put(BODY_FIELD_LOCATION, mDevice.getLocation());

        JSONObject user = new JSONObject();
        user.put(BODY_FIELD_USERINFO_NETWORK, mDevice.getNetworkType());
        user.put(BODY_FIELD_USERINFO_MODEL, mDevice.getModel());
        user.put(BODY_FIELD_USERINFO_SCREEN_WIDTH, mDevice.getScreenWidth());
        user.put(BODY_FIELD_USERINFO_DISTRIBUTION, mDevice.getDistribution());

        jsonObject.put(BODY_FIELD_USERINFO, user);

        return jsonObject;
    }

    public JSONObject getChannelsBody() throws JSONException {
        JSONObject jsonObject = getCommonBody();
        jsonObject.put(BODY_FIELD_NEEDCATLIST, true);

        return jsonObject;
    }

    public JSONObject getArticlesBody(WxChannel channel, int endStreamId) throws JSONException {
        JSONObject jsonObject = getCommonBody();
        jsonObject.put(BODY_FIELD_NEEDCATLIST, false);
        jsonObject.put(BODY_FIELD_ACTION, 1);
        jsonObject.put(BODY_FIELD_END_STREAM_ID, endStreamId);

        JSONObject channelObj = new JSONObject();
        channelObj.put(BODY_FIELD_CHANNEL_ID, channel.id);
        channelObj.put(BODY_FIELD_CHANNEL_NAME, channel.name);
        channelObj.put(BODY_FIELD_CHANNEL_SUBID, Integer.valueOf(channel.subid));

        jsonObject.put(BODY_FIELD_CHANNEL, channelObj);

        return jsonObject;
    }

    public JSONObject getMoreArticlesBody(WxChannel channel, long startTime, int endStreamId) throws JSONException {
        JSONObject jsonObject = getCommonBody();
        jsonObject.put(BODY_FIELD_NEEDCATLIST, false);
        jsonObject.put(BODY_FIELD_ACTION, 2);
        jsonObject.put(BODY_FIELD_END_STREAM_ID, -1);
        jsonObject.put(BODY_FIELD_START_TIME, TimeUtil.longDateFormat(startTime));
        jsonObject.put(BODY_FIELD_START_STREAM_ID, endStreamId);

        JSONObject channelObj = new JSONObject();
        channelObj.put(BODY_FIELD_CHANNEL_ID, channel.id);
        channelObj.put(BODY_FIELD_CHANNEL_NAME, channel.name);
        channelObj.put(BODY_FIELD_CHANNEL_SUBID, Integer.valueOf(channel.subid));

        jsonObject.put(BODY_FIELD_CHANNEL, channelObj);

        return jsonObject;
    }
}
