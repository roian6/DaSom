package com.example.dasom.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;
import java.util.Locale;

public class PhoneUtil {

    @SuppressLint("MissingPermission")
    public static String getPhone(Context c){

        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {}

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {}
        };
        TedPermission.with(c)
                .setPermissionListener(listener)
                .setDeniedMessage("권한에 동의해 주세요.")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();

        TelephonyManager tMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();

        if (phoneNumber.startsWith("+82"))
            phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

        phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());
        return phoneNumber;
    }
}
