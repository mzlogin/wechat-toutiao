package org.mazhuang.wechattoutiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.mazhuang.wechattoutiao.model.WxChannelsResult;
import org.mazhuang.wechattoutiao.network.WxClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WxClient.getChannels(new Callback<WxChannelsResult>() {
            @Override
            public void onResponse(Call<WxChannelsResult> call, Response<WxChannelsResult> response) {
                Log.d("MainActivity", response.body().toString());
            }

            @Override
            public void onFailure(Call<WxChannelsResult> call, Throwable t) {

            }
        });
    }
}
