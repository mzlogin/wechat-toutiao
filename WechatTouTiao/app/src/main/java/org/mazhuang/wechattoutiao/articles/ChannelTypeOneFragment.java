package org.mazhuang.wechattoutiao.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.data.WxArticlesResult;
import org.mazhuang.wechattoutiao.data.source.remote.WxClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ChannelTypeOneFragment extends BaseFragment {

    private ArticlesAdapter mAdapter;

    private Button mRetryBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fetchArguments();

        View rootView = inflater.inflate(R.layout.fragment_channel_type_one, container, false);

        ListView list = (ListView) rootView.findViewById(R.id.articles);
        mAdapter = new ArticlesAdapter();
        list.setAdapter(mAdapter);
        list.setEmptyView(rootView.findViewById(R.id.empty));

        mRetryBtn = (Button) rootView.findViewById(R.id.retry);
        mRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRetryBtn.setEnabled(false);
                fetchData();
            }
        });

        fetchData();

        return rootView;
    }

    private void fetchData() {
        WxClient.getArticles(new Callback<WxArticlesResult>() {
            @Override
            public void onResponse(Call<WxArticlesResult> call, Response<WxArticlesResult> response) {
                if (response.body() != null && response.body().isSuccessful()) {
                    mAdapter.setData(response.body());
                } else {
                    Toast.makeText(getContext(), "获取文章列表失败", Toast.LENGTH_SHORT).show();
                }
                if (mRetryBtn.getVisibility() != View.VISIBLE) {
                    mRetryBtn.setVisibility(View.VISIBLE);
                }
                mRetryBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<WxArticlesResult> call, Throwable t) {
                Toast.makeText(getContext(), "获取文章列表失败", Toast.LENGTH_SHORT).show();
                if (mRetryBtn.getVisibility() != View.VISIBLE) {
                    mRetryBtn.setVisibility(View.VISIBLE);
                }
                mRetryBtn.setEnabled(true);
            }
        }, mChannelInfo);
    }
}
