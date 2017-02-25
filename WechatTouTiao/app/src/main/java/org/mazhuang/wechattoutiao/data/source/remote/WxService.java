package org.mazhuang.wechattoutiao.data.source.remote;

import org.mazhuang.wechattoutiao.data.model.WxArticlesResult;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mazhuang on 2017/1/22.
 */

public interface WxService {
    @POST("/channel")
    Observable<WxChannelsResult> getChannels(@Query("mid") String mid, @Body RequestBody body);

    @POST("/data")
    Observable<WxArticlesResult> getArticles(@Query("mid") String mid, @Body RequestBody body);
}
