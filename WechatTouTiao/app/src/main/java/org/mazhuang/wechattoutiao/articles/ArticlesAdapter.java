package org.mazhuang.wechattoutiao.articles;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mazhuang.wechattoutiao.articledetail.ArticleActivity;
import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxArticlesResult;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ArticlesAdapter extends BaseAdapter {

    private List<WxArticle> mData;

    public void setData(List<WxArticle> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ItemViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_article, null);
            holder = new ItemViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            private String mLink;

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleActivity.class);
                intent.putExtra(ArticleActivity.URL, mLink);
                view.getContext().startActivity(intent);
            }

            View.OnClickListener setLink(String link) {
                mLink = link;
                return this;
            }
        }.setLink(articleInfo.link));

        return convertView;
    }

    private static class ItemViewHolder {
        TextView mTitle;
    }
}
