package com.longshun.myvideoplayer.activiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longshun.myvideoplayer.R;
import com.longshun.myvideoplayer.adapter.DividerItemDecoration;
import com.longshun.myvideoplayer.adapter.VideosAdapter;
import com.longshun.myvideoplayer.bean.Video;
import com.longshun.myvideoplayer.util.VideoUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements VideosAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView recyclerView;
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
        recyclerView = (RecyclerView) findViewById(R.id.list_videos);
        videosAdapter = new VideosAdapter(this,videoList);
        videosAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(videosAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PlayVideoActivity.class);
        //// TODO: 2016/9/13 传递数据

        startActivity(intent);
    }
}
