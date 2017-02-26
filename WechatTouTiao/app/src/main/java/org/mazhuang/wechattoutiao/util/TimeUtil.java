package org.mazhuang.wechattoutiao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mazhuang on 2017/2/26.
 */

public abstract class TimeUtil {
    public static String longDateFormat(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
    }
}
