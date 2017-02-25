package org.mazhuang.wechattoutiao.data.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxArticle {
    String account_openid;
    String appendix;
    List<String> img_list;
    KeywordTag keyword_tag;
    public String link;
    public String open_link;
    String pub_source;
    long pub_time;
    int read_num;
    int stream_id;
    List<KeywordTag> subscribe_list;
    Tag tag; // TODO: 2017/1/23
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
}
