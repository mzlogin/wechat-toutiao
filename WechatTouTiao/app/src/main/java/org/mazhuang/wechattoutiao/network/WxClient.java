package org.mazhuang.wechattoutiao.network;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;
import org.mazhuang.wechattoutiao.model.WxChannelsResult;
import org.mazhuang.wechattoutiao.utils.Security;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxClient {
    public static String BASE_URL = "http://weixin.sa.sogou.com/";

    public static void getChannels(Callback<WxChannelsResult> callback) {
        try {
            JSONObject jsonObject = getCommonBody();

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "req=" + Security.aesEnc(jsonObject.toString()));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WxService request = retrofit.create(WxService.class);

            Call<WxChannelsResult> response = request.getChannels("ed2b99000712042888", body);

            response.enqueue(callback);
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
        jsonObject.put("needcatlist", true);

        JSONObject user = new JSONObject();
        user.put("network", 1);
        user.put("model", "MI 5");
        user.put("screen_width", 1080);
        user.put("distribution", "3028");

        jsonObject.put("userinfo", user);

        return jsonObject;
    }
}
