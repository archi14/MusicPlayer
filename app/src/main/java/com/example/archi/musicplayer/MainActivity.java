package com.example.archi.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageButton play,pause,reset;
    Button btn;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    String path;
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
        handler = new Handler();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        Intent intent= getIntent();
        if(intent!=null)
        {
                Log.d("TAG", intent.toString());
            //Log.d("TAG", intent.getStringExtra("type"));
                 path = intent.getStringExtra("file");
                if(path!=null)
                {
                    Log.d("TAG", path);
                    try {
                        InitializePlayertrying(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", "here");
                    //playMusic();
                }

            }

        InitializePlayer();

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
                pauseMusic();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.reset();
                    seekBar.setProgress(0);
                    try {
                        InitializePlayertrying(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", String.valueOf(mediaPlayer));
                    //InitializePlayer();
                }
            }
        });
    }

    private void pauseMusic() {
        mediaPlayer.pause();
    }

    private void playMusic() {
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
    private void InitializePlayertrying(String path) throws IOException {
        if(mediaPlayer==null)
        {
            mediaPlayer =  new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration = mediaPlayer.getDuration();
                    Log.d("TAG", String.valueOf(duration));;
                    seekBar.setMax(duration);
                    Log.d("TAG", String.valueOf(seekBar.getMax()));
                    playMusic();
                }
            });


        }else
        {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
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
