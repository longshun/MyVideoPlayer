package com.longshun.myvideoplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longshun.myvideoplayer.R;
import com.longshun.myvideoplayer.bean.Video;
import com.longshun.myvideoplayer.util.VideoUtils;

import java.util.List;

/**
 * Created by longShun on 2016/9/13.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder>{

    private Context context;
    private List<Video> videos;

    private OnRecyclerViewItemClickListener onItemClickListener;

    public VideosAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_list, viewGroup, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder viewHolder, int i) {
        Video video = videos.get(i);
        long duration = video.getDuration();
        long hour = duration/1000/60/60;
        long min = duration/1000/60;
        long sec = duration/1000%60;
        viewHolder.tvDisPlayName.setText(video.getDisplayName());
        viewHolder.tvSize.setText("大小:"+VideoUtils.formatSize(video.getSize())+"M");
        viewHolder.tvDuration.setText("时间:"+hour+":"+min+":"+sec);
        viewHolder.tvDisPlayName.setTag(1);
        //生成视频缩略图
        viewHolder.ivThumbnail.setImageResource(R.mipmap.ic_launcher);
        viewHolder.ivThumbnail.setTag(video.getPath());//解决图片错位问题
        new LoadThumbnailTask(viewHolder.ivThumbnail).execute(video);
    }

    /*可以在其他地方生成并保存缩略图，适配器中直接设置，提高RecyclerView的流畅性*/
    static class LoadThumbnailTask extends AsyncTask<Video,Void,Bitmap>{

        private ImageView ivThumbnail;
        private Video video;

        public LoadThumbnailTask(ImageView ivThumbnail) {
            this.ivThumbnail = ivThumbnail;
        }

        @Override
        protected Bitmap doInBackground(Video[] objects) {
            video = objects[0];
            Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(video.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,120,120,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap o) {
            String path = (String) ivThumbnail.getTag();
            if (video.getPath().equals(path)){
                ivThumbnail.setImageBitmap((Bitmap) o);
            }
        }
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDisPlayName;
        TextView tvSize;
        TextView tvDuration;
        ImageView ivThumbnail;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tvDisPlayName = (TextView) itemView.findViewById(R.id.tv_display_name);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }


    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
}

/*
//listView的写法
private Context context;

    private List<Video> videoList;

    public VideosAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return videoList == null ? 0 : videoList.size();
    }

    @Override
    public Object getItem(int i) {
        return videoList == null ? null : videoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return videoList == null ? 0 : i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView != null) {
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_video_list,null);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.tv_displayName = (TextView) view.findViewById(R.id.tv_display_name);
            holder.tv_size = (TextView) view.findViewById(R.id.tv_display_size);
            holder.tv_duration = (TextView) view.findViewById(R.id.tv_display_duration);
            view.setTag(holder);
        }
        //----------数据赋值
        Video video = videoList.get(i);
        holder.tv_displayName.setText(video.getDisplayName());
        holder.tv_displayName.setSelected(true);
        holder.tv_size.setText("大小："+ VideoUtils.formatSize(video.getSize())+"M");
        holder.tv_duration.setText("时长："+VideoUtils.formatDuration(video.getDuration())+"分");

        return view;
    }
    static class ViewHolder {
        TextView tv_displayName;
        TextView tv_size;
        TextView tv_duration;
    }
    */
