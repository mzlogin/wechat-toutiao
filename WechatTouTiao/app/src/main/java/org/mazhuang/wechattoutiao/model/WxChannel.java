package org.mazhuang.wechattoutiao.model;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxChannel {
        String channel_type;
        String dotnotify;
        String editable;
        Entrance h5_entrance;
        int id;
        String name;
        String selected;
        String subid;

    static class Entrance {
        String target_url;
        String title;
    }
}
