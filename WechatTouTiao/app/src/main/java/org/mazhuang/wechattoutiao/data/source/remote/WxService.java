package org.mazhuang.wechattoutiao.data.source.remote;

import org.mazhuang.wechattoutiao.data.model.WxArticlesResult;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;

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
    Call<WxChannelsResult> getChannels(@Query("mid") String mid, @Body RequestBody body);

    @POST("/data")
    Call<WxArticlesResult> getArticles(@Query("mid") String mid, @Body RequestBody body);
}
