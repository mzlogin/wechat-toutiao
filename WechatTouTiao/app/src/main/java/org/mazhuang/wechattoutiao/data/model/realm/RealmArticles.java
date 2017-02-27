package org.mazhuang.wechattoutiao.data.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mazhuang on 2017/2/28.
 */

public class RealmArticles extends RealmObject {
    @PrimaryKey
    public int channelId;
    public String gsonContent;
}
