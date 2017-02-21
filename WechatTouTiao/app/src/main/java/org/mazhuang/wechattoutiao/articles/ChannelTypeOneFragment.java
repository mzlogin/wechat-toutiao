package org.mazhuang.wechattoutiao.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class ChannelTypeOneFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArticlesAdapter mAdapter;

    private SwipeRefreshLayout mRefreshLayout;
    private SwipeRefreshLayout mEmptyLayout;

    private boolean mFirstFetchFinished = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fetchArguments();

        View rootView = inflater.inflate(R.layout.fragment_channel_type_one, container, false);

        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        initSwipeLayout(mRefreshLayout);
        mEmptyLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.empty);
        initSwipeLayout(mEmptyLayout);

        ListView list = (ListView) rootView.findViewById(R.id.articles);
        mAdapter = new ArticlesAdapter();
        list.setAdapter(mAdapter);
        list.setEmptyView(mEmptyLayout);

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
                    if (getActivity() != null) {
                        Toast.makeText(getContext(), "获取文章列表失败", Toast.LENGTH_SHORT).show();
                    }
                }
                finishRefresh();
                mFirstFetchFinished = true;
            }

            @Override
            public void onFailure(Call<WxArticlesResult> call, Throwable t) {
                if (getActivity() != null) {
                    Toast.makeText(getContext(), "获取文章列表失败", Toast.LENGTH_SHORT).show();
                }
                finishRefresh();
                mFirstFetchFinished = true;
            }
        }, mChannelInfo);
    }

    private void initSwipeLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        if (mFirstFetchFinished && mAdapter.getCount() == 0) {
            fetchData();
        } else {
            finishRefresh();
        }
    }

    private void finishRefresh() {
        mRefreshLayout.setRefreshing(false);
        mEmptyLayout.setRefreshing(false);
    }
}
