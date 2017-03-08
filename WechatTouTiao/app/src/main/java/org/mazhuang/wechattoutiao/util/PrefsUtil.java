package org.mazhuang.wechattoutiao.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mazhuang on 2017/3/8.
 */

public abstract class PrefsUtil {
    private static final String APP_PREFERENCE = "general_prefs";

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static void putDouble(Context context, String key, double value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putLong(key, Double.doubleToLongBits(value));
        editor.commit();
    }

    public static double getDouble(Context context, String key, double defValue) {
        return Double.longBitsToDouble(getPreferences(context).getLong(key, Double.doubleToLongBits(defValue)));
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
    }
}
