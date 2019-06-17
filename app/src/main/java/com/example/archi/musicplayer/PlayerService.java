package com.example.archi.musicplayer;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public int duration;
    private Handler handler;
    String path;
    private final IBinder binder = new LocalBinder();
    //SeekBar seekBar;

    public class LocalBinder extends Binder{
        PlayerService getService(){
            return PlayerService.this;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        path = intent.getStringExtra("path");
        Log.d("super", path);
        handler = new Handler();
        try {
            InitializePlayertrying(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return binder;
    }
    public void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            Log.d("TAG", "onClick: ");
            mediaPlayer.start();
            /*final Runnable r = new Runnable() {
                @Override
                public void run() {
                    Log.d("here", String.valueOf(mediaPlayer.getCurrentPosition()));
                    sendMessage(mediaPlayer.getCurrentPosition());
                    while(mediaPlayer.getCurrentPosition()<mediaPlayer.getDuration())
                    {
                        handler.postDelayed(this,1000);
                    }
                }
            };
            handler.postDelayed(r,1000);*/


        }
    }

    private void sendMessage(int progress) {
        Intent intent = new Intent("updating seekbar");
        intent.putExtra("progress",progress);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void InitializePlayertrying(String path) throws IOException {
        if(mediaPlayer==null)
        {
           // album.setImageURI(albumart);
            mediaPlayer =  new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration = mediaPlayer.getDuration();
                    Log.d("TAG", String.valueOf(duration));
                    playMusic();

                }
            });


        }else
        {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        }
    }

    public int getDuration()
    {
        return mediaPlayer.getDuration();
    }
    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public boolean reset()
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.reset();
            return true;
        }
        return false;
    }

    public boolean checkPlayer()
    {
        if(mediaPlayer==null)
        {
            return false;
        }
        return true;
    }
    public void seekBarChange(int progress)
    {
        mediaPlayer.seekTo(progress);
    }

    public int getCurrentPos()
    {
       return mediaPlayer.getCurrentPosition();
    }
}
