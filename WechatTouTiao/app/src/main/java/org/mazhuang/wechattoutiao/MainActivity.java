package org.mazhuang.wechattoutiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;
import org.mazhuang.wechattoutiao.network.GetCategoryRequest;
import org.mazhuang.wechattoutiao.utils.Security;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mid", "ed2b99000712042888");
                    jsonObject.put("xid", "303b00c0881f01da1148bc8d8cc7c930387a");
                    jsonObject.put("imsi", "460030971693100");
                    jsonObject.put("timeMillis", "1485095452350");
                    jsonObject.put("os", "android");
                    jsonObject.put("req_ver", 3);
                    jsonObject.put("ver", "5101");
                    jsonObject.put("os_ver", 23);
                    jsonObject.put("needcatlist", true);

                    JSONObject user = new JSONObject();
                    user.put("network", 1);
                    user.put("model", "MI 5");
                    user.put("screen_width", 1080);
                    user.put("distribution", "3028");

                    jsonObject.put("userinfo", user);

                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "req=" + Security.enc(jsonObject.toString()));

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://weixin.sa.sogou.com/")
                            .build();

                    GetCategoryRequest request = retrofit.create(GetCategoryRequest.class);

                    Call<ResponseBody> response = request.getCategories("ed2b99000712042888", body);

                    response.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Log.d("MainActivity", response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
