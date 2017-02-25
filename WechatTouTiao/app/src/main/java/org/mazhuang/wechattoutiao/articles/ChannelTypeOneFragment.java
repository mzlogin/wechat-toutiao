package org.mazhuang.wechattoutiao.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ChannelTypeOneFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        ArticlesContract.View {

    private ArticlesAdapter mAdapter;

    private SwipeRefreshLayout mRefreshLayout;
    private SwipeRefreshLayout mEmptyLayout;

    private boolean mFirstFetchFinished = false;

    private ArticlesContract.Presenter mPresenter;

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

        mPresenter = new ArticlesPresenter(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.start();
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
        if (!mFirstFetchFinished) {
            return;
        }

        if (mAdapter.getCount() == 0) {
            mPresenter.loadArticles();
        } else {
            mPresenter.loadMoreArticles();
        }
    }

    private void finishRefresh() {
        mRefreshLayout.setRefreshing(false);
        mEmptyLayout.setRefreshing(false);
        if (!mFirstFetchFinished) {
            mFirstFetchFinished = true;
        }
    }

    @Override
    public void showArticles(List<WxArticle> articles) {
        mAdapter.setData(articles);
        finishRefresh();
    }

    @Override
    public void showNoArticles() {
        mAdapter.setData(null);
        finishRefresh();
    }

    @Override
    public void showLoadingArticlesError() {
        mAdapter.setData(null);
        finishRefresh();
    }

    @Override
    public void showMoreArticles(List<WxArticle> articles) {
        mAdapter.setData(articles);
        finishRefresh();
    }

    @Override
    public void showNoMoreArticles() {
        finishRefresh();
    }

    @Override
    public void showLoadingMoreArticlesError() {
        finishRefresh();
    }

    @Override
    public WxChannel getChannelInfo() {
        return mChannelInfo;
    }

    @Override
    public void setPresenter(ArticlesContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
