package com.example.archi.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    Button play,pause,reset;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    int duration;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        reset = findViewById(R.id.reset);
        seekBar  = findViewById(R.id.seekBar);
        InitializePlayer();
        handler = new Handler();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null && !mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(mediaPlayer.getCurrentPosition()<duration)
                            {
                                try{
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        seekBar.setProgress(mediaPlayer.getCurrentPosition());

                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.reset();
                }
            }
        });
    }


    private void InitializePlayer() {
        if(mediaPlayer==null)
        {
            mediaPlayer =  MediaPlayer.create(getApplicationContext(),R.raw.b);
            duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);

        }
    }
}
