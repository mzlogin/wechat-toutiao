package org.mazhuang.wechattoutiao.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxChannelsResult extends BaseResult {

    ChannelResult result;

    static class ChannelResult {
        AuxiliaryModules auxiliary_modules;
        List<WxChannel> channel_list;
        int font_size;
        int header_style;
        NewChannel new_channel;
        int showhobby;
        int stream_style;
    }

    static class AuxiliaryModules {
        List<String> comment;
        List<String> relate_article;
        List<String> relate_tag;
        List<String> share;
    }

    static class NewChannel {
        String alert;
        int id;
        String name;
    }
}
