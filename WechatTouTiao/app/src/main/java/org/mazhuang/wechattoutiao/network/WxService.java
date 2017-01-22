package org.mazhuang.wechattoutiao.network;

import org.mazhuang.wechattoutiao.model.WxChannelsResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Call<ResponseBody> getArticles(@Query("mid") String mid, @Body RequestBody body);
}
