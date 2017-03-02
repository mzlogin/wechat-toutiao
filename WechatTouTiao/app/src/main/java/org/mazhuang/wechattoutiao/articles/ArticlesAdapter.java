package org.mazhuang.wechattoutiao.articles;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articledetail.ArticleActivity;
import org.mazhuang.wechattoutiao.articledetail.VideoActivity;
import org.mazhuang.wechattoutiao.data.model.WxArticle;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ArticlesAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private List<WxArticle> mData;

    private int[] mItemLayouts = {
            R.layout.list_item_article,
            R.layout.list_item_article_1,
            R.layout.list_item_article_2,
            R.layout.list_item_article_3,
            R.layout.list_item_article_4
    };

    public void setData(List<WxArticle> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Class<?> cls;
        if (getItemViewType(position) == 3) { // video
            cls = VideoActivity.class;
        } else {
            cls = ArticleActivity.class;
        }

        Intent intent = new Intent(view.getContext(), cls);
        intent.putExtra(ArticleActivity.PARAM_URL, getLink(position));
        view.getContext().startActivity(intent);
    }

    public String getLink(int position) {
        return mData.get(position).link;
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
    public int getItemViewType(int position) {
        WxArticle article = mData.get(position);
        if (article.type == 1 && article.video_type == 0) {
            return 1;
        } else if (article.type == 3 && article.video_type == 0) {
            return 2;
        } else if (article.video_type == 1) {
            return 3;
        } else if (article.type == 2 && article.video_type == 0) {
            return 4;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return mItemLayouts.length;
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
        int layoutResId = mItemLayouts[getItemViewType(position)];
        switch (getItemViewType(position)) {
            case 1:
                return getView1(position, convertView, viewGroup, layoutResId);
            case 2:
                return getView2(position, convertView, viewGroup, layoutResId);
            case 3:
                return getView3(position, convertView, viewGroup, layoutResId);
            case 4:
                return getView4(position, convertView, viewGroup, layoutResId);
            default:
                return getView0(position, convertView, viewGroup, layoutResId);
        }
    }

    private View getView0(int position, View convertView, ViewGroup viewGroup, int layoutResId) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ViewHolder0 holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layoutResId, null);
            holder = new ViewHolder0();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder0) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);

        return convertView;
    }

    private View getView1(int position, View convertView, ViewGroup viewGroup, int layoutResId) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ViewHolder1 holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layoutResId, null);
            holder = new ViewHolder1();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mImg1 = (ImageView) convertView.findViewById(R.id.img1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);
        if (articleInfo.img_list != null && articleInfo.img_list.size() > 0) {
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(0))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg1);
        }

        return convertView;
    }

    private View getView2(int position, View convertView, ViewGroup viewGroup, int layoutResId) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ViewHolder2 holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layoutResId, null);
            holder = new ViewHolder2();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mImg1 = (ImageView) convertView.findViewById(R.id.img1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder2) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);
        if (articleInfo.img_list != null && articleInfo.img_list.size() > 0) {
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(0))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg1);
        }

        return convertView;
    }

    private View getView3(int position, View convertView, ViewGroup viewGroup, int layoutResId) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ViewHolder3 holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layoutResId, null);
            holder = new ViewHolder3();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mImg1 = (ImageView) convertView.findViewById(R.id.img1);
            holder.mPlay = (ImageView) convertView.findViewById(R.id.play);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder3) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);
        if (articleInfo.img_list != null && articleInfo.img_list.size() > 0) {
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(0))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg1);
        }

        return convertView;
    }

    private View getView4(int position, View convertView, ViewGroup viewGroup, int layoutResId) {
        final WxArticle articleInfo = (WxArticle) getItem(position);

        ViewHolder4 holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layoutResId, null);
            holder = new ViewHolder4();
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mImg1 = (ImageView) convertView.findViewById(R.id.img1);
            holder.mImg2 = (ImageView) convertView.findViewById(R.id.img2);
            holder.mImg3 = (ImageView) convertView.findViewById(R.id.img3);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder4) convertView.getTag();
        }

        holder.mTitle.setText(articleInfo.title);
        if (articleInfo.img_list != null && articleInfo.img_list.size() >=3 ) {
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(0))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg1);
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(1))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg2);
            Glide.with(convertView.getContext())
                    .load(articleInfo.img_list.get(2))
                    .centerCrop()
                    .crossFade()
                    .into(holder.mImg3);
        }

        return convertView;
    }

    private static class ViewHolder0 {
        TextView mTitle;
    }

    private static class ViewHolder1 {
        TextView mTitle;
        ImageView mImg1;
    }
    private static class ViewHolder2 {
        TextView mTitle;
        ImageView mImg1;
    }

    private static class ViewHolder3 {
        TextView mTitle;
        ImageView mImg1;
        ImageView mPlay;
    }

    private static class ViewHolder4 {
        TextView mTitle;
        ImageView mImg1;
        ImageView mImg2;
        ImageView mImg3;
    }
}
