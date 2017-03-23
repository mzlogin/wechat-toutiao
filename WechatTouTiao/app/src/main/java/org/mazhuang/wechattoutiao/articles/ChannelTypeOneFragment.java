package org.mazhuang.wechattoutiao.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    private Button mLoadMoreButton;
    private TextView mNoMoreTextView;

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
        initFootView(list, inflater);
        mAdapter = new ArticlesAdapter();
        list.setAdapter(mAdapter);
        list.setEmptyView(mEmptyLayout);
        list.setOnItemClickListener(mAdapter);

        mPresenter = new ArticlesPresenter(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.start();

        if (needForceRefresh()) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (!mFirstFetchFinished) {
                        mRefreshLayout.setRefreshing(true);
                    }
                }
            });
        }
        mChannelInfo.last_showTime = System.currentTimeMillis();
    }

    private void initSwipeLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initFootView(ListView list, LayoutInflater inflater) {
        View footView = inflater.inflate(R.layout.list_load_more_footer, null, false);
        list.addFooterView(footView);
        mLoadMoreButton = (Button) footView.findViewById(R.id.load_more);
        mLoadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadMoreButton.setEnabled(false);
                mPresenter.loadMoreArticles();
            }
        });
        mNoMoreTextView = (TextView) footView.findViewById(R.id.no_more);
    }

    @Override
    public void onRefresh() {
        if (!mFirstFetchFinished) {
            return;
        }

        if (mAdapter.getCount() == 0) {
            mPresenter.loadArticles(false);
        } else {
            mPresenter.loadArticles(true);
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
        mLoadMoreButton.setEnabled(true);
    }

    @Override
    public void showNoMoreArticles() {
        finishRefresh();
        mLoadMoreButton.setVisibility(View.GONE);
        mNoMoreTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingMoreArticlesError() {
        finishRefresh();
        mLoadMoreButton.setEnabled(true);
    }

    @Override
    public boolean needForceRefresh() {
        return (System.currentTimeMillis() - mChannelInfo.last_showTime > 30*60*1000);
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
