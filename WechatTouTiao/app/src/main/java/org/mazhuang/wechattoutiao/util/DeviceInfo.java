package org.mazhuang.wechattoutiao.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import org.mazhuang.wechattoutiao.R;

import java.util.UUID;

/**
 * Created by mazhuang on 2017/3/8.
 */

public class DeviceInfo {
    private static class LazyHolder {
        private static DeviceInfo INSTANCE = new DeviceInfo();
    }

    public static DeviceInfo getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Context mApplicationContext;
    private int mScreenWidthPixels;
    private String mIMSI = "";
    private String mMid = "";
    private String mXid = "";
    private String mCoordinate = "";

    public void init(Activity activity) {
        mApplicationContext = activity.getApplicationContext();

        initScreenSize(activity);

        initIds(activity);

        initLocation(activity);
    }

    private void initLocation(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null &&
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000*60*30,
                        1000,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if (location != null) {
                                    mCoordinate = mApplicationContext.getString(R.string.coordinate,
                                            location.getLongitude(),
                                            location.getLatitude());
                                }
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            @Override
                            public void onProviderEnabled(String provider) {
                            }

                            @Override
                            public void onProviderDisabled(String provider) {
                            }
                });
            }
        }
    }

    private void initScreenSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidthPixels = displayMetrics.widthPixels;
    }

    private void initIds(Activity activity) {
        // imsi
        String id = null;
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                id = telephonyManager.getDeviceId();
            }

            if (!TextUtils.isEmpty(id)) {
                mIMSI = id;
            }
        }

        // mid
        if (!TextUtils.isEmpty(id) && id.equals("0")) {
            id = null;
        }

        if (TextUtils.isEmpty(id)) {
            id = PrefsUtil.getString(activity, "mid", "");
            if (TextUtils.isEmpty(id)) {
                id = System.currentTimeMillis() + "";
                PrefsUtil.putString(activity, "mid", id);
            }
        }

        if (!TextUtils.isEmpty(id)) {
            if (id.length() > 16 || id.length() < 14) {
                if (id.length() > 13) {
                    id = id.substring(0, 13);
                }
                String uid = PrefsUtil.getString(activity, "uid", "");
                if (TextUtils.isEmpty(uid)) {
                    uid = UUID.randomUUID().toString();
                    if (uid.length() > 6) {
                        uid = uid.substring(0, 6);
                    }
                    PrefsUtil.putString(activity, "uid", uid);
                }
                id = id + "|" + uid;
            }
        }

        String md5 = Security.encMethod12(id);
        if (md5 != null && md5.length() > 4) {
            md5 = md5.substring(0, 4);
        } else {
            md5 = "eae2";
        }

        mMid = md5 + id;

        // xid
        String xid = Security.encMethod11(Long.toString(System.currentTimeMillis()) + mIMSI);
        if (xid != null) {
            xid = Security.encMethod13(xid);
            if (xid != null && xid.length() > 4) {
                xid = xid.substring(0, 4) + xid;
                if (xid.length() == 36) {
                    mXid = xid;
                }
            }
        }
    }

    public String getModel() {
        return Build.MODEL;
    }

    public int getScreenWidth() {
        return mScreenWidthPixels;
    }

    public String getDistribution() {
        return "3053";  // meta info UMENG_CHANNEL in AndroidManifest.xml
    }

    public String getMid() {
        return mMid;
    }

    public String getXid() {
        return mXid;
    }

    public String getIMSI() {
        return mIMSI;
    }

    public String getLocation() {
        return mCoordinate;
    }

    public int getNetworkType() {
        int type = 1;

        NetworkInfo networkInfo = ((ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            type = 0;
        } else {
            String typeName = networkInfo.getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                type = 1;
            } else if (typeName.equalsIgnoreCase("MOBILE")) {
                int networkType = ((TelephonyManager) mApplicationContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        type = 3;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        type = 4;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        type = 5;
                        break;
                }
            }
        }

        return type;
    }
}
