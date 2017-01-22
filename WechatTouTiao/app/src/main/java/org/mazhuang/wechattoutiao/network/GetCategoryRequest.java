package org.mazhuang.wechattoutiao.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mazhuang on 2017/1/22.
 */

public interface GetCategoryRequest {
    @POST("/channel")
    Call<ResponseBody> getCategories(@Query("mid") String mid, @Body RequestBody body);
}
