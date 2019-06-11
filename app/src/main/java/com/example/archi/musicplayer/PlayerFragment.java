package com.example.archi.musicplayer;



import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {
    private ImageButton play,pause,reset;
    private Button btn;
    Uri albumart;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private String path;
    private int duration;
    private Handler handler;


    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        path = getArguments().getString("filepath");
        albumart = Uri.parse(getArguments().getString("albumart"));
        try {
            InitializePlayertrying(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        play = view.findViewById(R.id.play);
        btn = view.findViewById(R.id.btn);
        pause = view.findViewById(R.id.pause);
        reset = view.findViewById(R.id.reset);
        seekBar  = view.findViewById(R.id.seekBar);
        handler = new Handler();
       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ListActivity.class);
                startActivity(intent);
            }
        });*/


/*
        try {
            Log.d("inhere", path);
            InitializePlayertrying(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

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
            mediaPlayer =  MediaPlayer.create(getContext(),R.raw.b);
            duration = mediaPlayer.getDuration();
            Log.d("TAG", String.valueOf(duration));
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
                    Log.d("TAG", String.valueOf(duration));
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
    public void onStop() {
        super.onStop();
        mediaPlayer.release();
    }

}
