package com.wgaham.secretsharing;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.util.Calendar;
import java.util.Random;

/**
 * 一些工具函数
 *
 * @author Wgaham
 *         Created by zh on 2018/3/13.
 */

public final class Tool {
    private static final int MINUTE = 60;

    private Tool() {

    }

    /**
     * 检测目前的时间是否符合重构时间
     * startTime和endTime是从库中提取的字符串形式开始时间和结束时间
     */
    static boolean restructureTimeJudgment(String startTime, String endTime) {


        int startMinutes, endMinutes, nowMinutes, startHour, endHour, startMinute, endMinute, nowHour, nowMinute;
        if (startTime.equals(endTime)) {
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == 0) {
            nowHour = calendar.get(Calendar.HOUR);
        } else {
            nowHour = calendar.get(Calendar.HOUR) + 12;
        }

        nowMinute = calendar.get(Calendar.MINUTE);
        nowMinutes = nowHour * MINUTE + nowMinute;
        try {
            String[] start = startTime.split(":");
            startHour = Integer.parseInt(start[0]);
            startMinute = Integer.parseInt(start[1]);
            String[] end = endTime.split(":");
            endHour = Integer.parseInt(end[0]);
            endMinute = Integer.parseInt(end[1]);
            startMinutes = startHour * MINUTE + startMinute;
            endMinutes = endHour * MINUTE + endMinute;
            if (startMinutes < endMinutes) {
                return startMinutes <= nowMinutes && nowMinutes < endMinutes;
            } else {
                return startMinutes <= nowMinutes || nowMinutes < endMinutes;
            }
        } catch (ExceptionInInitializerError eiie) {
            eiie.printStackTrace();
            return false;
        }
    }

    /**
     * 检查外置存储是否可以访问
     */
    static boolean externalStorageWritable() {

        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡根目录
     */
    static String getSdcardPath() {
        String sdPath = "";
        if (externalStorageWritable()) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdPath = "-1";
        }
        return sdPath;
    }


    static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 适配4.4以上版本的通过URL获取文件路径获取
     */
    @TargetApi(19)
    static String getPathAfterKitKat(final Context context, final Uri uri) {
        //判断Document对象
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //普通对象
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] strings = docId.split(":");
                final String type = strings[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return getSdcardPath() + "/" + strings[1];
                }
            }
            //Downloads类型处理
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] strings = docId.split(":");
                final String type = strings[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{strings[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * 生成随机长度随机内容的字符串
     */
    static String randomIndexString() {
        Random random = new Random();
        int l = random.nextInt(10) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        String[] lowerCaseLetters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int i = 0; i < l; i++) {
            stringBuilder.append(lowerCaseLetters[random.nextInt(26)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 4.4版以下的适配类
     */

}
