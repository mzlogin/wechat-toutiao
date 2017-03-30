package org.mazhuang.wechattoutiao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.channels.ChannelsActivity;
import org.mazhuang.wechattoutiao.util.DeviceInfo;

public class SplashActivity extends BaseActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private static final String[] sPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startHome();
        } else {
            boolean isAllPermissionsGranted = true;
            for (String permission : sPermissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    isAllPermissionsGranted = false;
                    break;
                }
            }

            if (!isAllPermissionsGranted) {
                requestPermissions(sPermissions, PERMISSIONS_REQUEST_CODE);
            } else {
                startHome();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            boolean isAllPermissionsGranted = true;
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        isAllPermissionsGranted = false;
                        break;
                    }
                }
            } else {
                isAllPermissionsGranted = false;
            }

            if (!isAllPermissionsGranted) {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }

            startHome();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void startHome() {
        DeviceInfo.getInstance().init(this);

        showChannelsPage();
    }

    private void showChannelsPage() {
        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
        finish();
    }
}
