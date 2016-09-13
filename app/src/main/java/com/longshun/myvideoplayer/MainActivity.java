package com.longshun.myvideoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.longshun.myvideoplayer.adapter.VideosAdapter;
import com.longshun.myvideoplayer.bean.Video;
import com.longshun.myvideoplayer.util.VideoUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Video> videoList;
    private VideosAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        videoList = VideoUtils.getLocalVideos(this);
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.list_videos);
        videosAdapter = new VideosAdapter(this,videoList);
        listView.setAdapter(videosAdapter);
    }
}
