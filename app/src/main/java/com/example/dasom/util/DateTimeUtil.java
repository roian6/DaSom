package com.example.dasom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static String toKoreanDate(String old) {
        if (old == null) return null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(old);
            return new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String toKoreanTime(String old) {
        if (old == null) return null;
        try {
            Date time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(old);
            return new SimpleDateFormat("aa hh시 mm분", Locale.KOREAN).format(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
