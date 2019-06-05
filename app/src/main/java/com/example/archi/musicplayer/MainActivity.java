package com.example.archi.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    Button play,pause,reset,btn;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    int duration;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        btn = findViewById(R.id.btn);
        pause = findViewById(R.id.pause);
        reset = findViewById(R.id.reset);
        seekBar  = findViewById(R.id.seekBar);
        InitializePlayer();
        handler = new Handler();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser)
                {
                    mediaPlayer.seekTo(progress);
                }

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

                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    Log.d("TAG", "onClick: ");
                    mediaPlayer.start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(mediaPlayer.getCurrentPosition()<duration)
                            {
                               handler.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                   }
                               }) ;
                               try {
                                   Thread.sleep(1000);
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
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
                    Log.d("TAG", String.valueOf(mediaPlayer));
                    InitializePlayer();
                }
            }
        });
    }


    private void InitializePlayer() {
        if(mediaPlayer==null)
        {
            mediaPlayer =  MediaPlayer.create(getApplicationContext(),R.raw.b);
            duration = mediaPlayer.getDuration();
            Log.d("TAG", String.valueOf(duration));;
            seekBar.setMax(duration);
            Log.d("TAG", String.valueOf(seekBar.getMax()));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }
}
