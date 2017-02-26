package org.mazhuang.wechattoutiao.data.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxArticle implements Comparable<WxArticle> {
    String account_openid;
    String appendix;
    List<String> img_list;
    KeywordTag keyword_tag;
    public String link;
    public String open_link;
    String pub_source;
    public long pub_time;
    int read_num;
    public int stream_id;
    List<KeywordTag> subscribe_list;
    Tag tag;
    public String title;
    int type;
    List<String> unlike_list;
    int video_type;

    static class KeywordTag {
        String appendix;
        String h5_link;
        String id;
        String name;
        int source;
        int subscribe_num;
        Tag tag;
        int type;
    }

    static class Tag {
        String color;
        String icon;
        int tag_id;
    }

    @Override
    public int compareTo(WxArticle wxArticle) {
        if (this.pub_time > wxArticle.pub_time) {
            return -1;
        } else if (this.pub_time < wxArticle.pub_time) {
            return 1;
        } else {
            return 0;
        }
    }
}
