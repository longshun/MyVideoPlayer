package com.longshun.myvideoplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.longshun.myvideoplayer.bean.Video;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longShun on 2016/9/13.
 */

/*搜索本地视频的工具类*/
public class VideoUtils {

    public VideoUtils() {
    }

    /*异步获取视频集合*/
    public static List<Video> synGetLocalVideos(Context context, Handler handler){
        return null;
    }

    /*视频数量较少可以使用这种方式在主线程直接获取，如果数量较多可能会造成阻塞导致ANR*/
    public static List<Video> getLocalVideos(Context context){
        List<Video> videoList = null;
        if (context != null) {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                videoList = new ArrayList<>();
                while (cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    Video video = new Video(id, title, album, artist, displayName, mimeType, path, size, duration);
                    videoList.add(video);
                }
                Log.d("videoList=",videoList.toString());
                Log.d("videoList-size=",videoList.size()+"");
                cursor.close();
                cursor = null;
            }
        }
        return videoList;
    }

    /*B转化为M*/
    public static String formatSize(long size){
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(size/1024*1d/1024);
    }
    /*毫秒-》分钟*/
    public static String formatDuration(long duration){
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(duration/1000*1d/60);
    }

}
