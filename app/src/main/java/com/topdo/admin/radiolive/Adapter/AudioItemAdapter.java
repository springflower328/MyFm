package com.topdo.admin.radiolive.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.topdo.admin.radiolive.AudioAdapterModel;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioItemAdapter extends RecyclerView.Adapter<AudioItemAdapter.AudioItemsViewHolder> {

    private JcPlayerView jcplayerView;
    private MediaPlayer mediaPlayer;
    private List<AudioAdapterModel> items;
    private int playingPosition;
    private AudioItemsViewHolder playingHolder;
    private Context context;



    public interface AudioListener {
        void onStopPlayer();
    }

    public AudioItemAdapter(Context context, List<AudioAdapterModel> audioItems) {
        this.items = audioItems;
        this.playingPosition = -1;
        this.context = context;
    }

    @Override
    public AudioItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AudioItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(AudioItemsViewHolder holder, int position) {
        AudioAdapterModel model = items.get(position);

        Glide
                .with(context)
                .load(Uri.parse(model.getUserPhoto()))
                .into(holder.ivUserImage);

        holder.tvDescription.setText(model.getAudioDescription());

        if (position == playingPosition) {
            playingHolder = holder;
            updatePlayingView();
        } else {
            updateNonPlayingView(holder);
        }
    }



    @Override
    public void onViewRecycled(AudioItemsViewHolder holder) {
        super.onViewRecycled(holder);
        if (playingPosition == holder.getAdapterPosition()) {
            updateNonPlayingView(playingHolder);
            playingHolder = null;
        }
    }

    private void updateNonPlayingView(AudioItemsViewHolder holder) {
        holder.ivPlayPause.setImageResource(R.drawable.ic_play_button);
    }

    private void updatePlayingView() {
        if (mediaPlayer.isPlaying()) {
            playingHolder.ivPlayPause.setImageResource(R.drawable.ic_stop_circular_button);
        } else {
            playingHolder.ivPlayPause.setImageResource(R.drawable.ic_play_button);
        }
    }

    public void stopPlayer() {
        if (null != mediaPlayer) {
            releaseMediaPlayer();
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    class AudioItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AudioListener {
        ImageView ivPlayPause, ivUserImage;
        TextView tvDescription;

        AudioItemsViewHolder(View itemView) {
            super(itemView);
            ivPlayPause = (ImageView) itemView.findViewById(R.id.playbtn);
            ivPlayPause.setOnClickListener(this);
            ivUserImage = (ImageView) itemView.findViewById(R.id.userImage);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == playingPosition) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            } else {
                playingPosition = getAdapterPosition();
                if (mediaPlayer != null) {
                    if (null != playingHolder) {
                        updateNonPlayingView(playingHolder);
                    }
                    mediaPlayer.release();
                }
                playingHolder = this;
                startMediaPlayer(items.get(playingPosition).getAudioId());
            }
            updatePlayingView();
        }

        @Override
        public void onStopPlayer() {
            stopPlayer();
        }
    }

    private void startMediaPlayer(String url) {
        Uri uri = Uri.parse(url);
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            });
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        if (null != playingHolder) {
            updateNonPlayingView(playingHolder);
        }
        mediaPlayer.release();
        mediaPlayer = null;
        playingPosition = -1;
    }


}
