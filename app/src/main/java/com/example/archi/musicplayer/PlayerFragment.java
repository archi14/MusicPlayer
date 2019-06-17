package com.example.archi.musicplayer;



import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {
    private ImageButton play,pause,reset;
    private Button btn;
    ImageView album;
    Uri albumart;
    private SeekBar seekBar;
    private String path;
    private int duration;
    private  int currentPos;
    PlayerService playerService;
    private boolean mBound = false;
    private Handler handler;
    Intent intent;
    PlayerViewModel model;
    public PlayerFragment() {
        // Required empty public constructor
    }

    private ServiceConnection connect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            playerService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                mBound =false;
          }
    };
    @Override
    public void onStart() {
        super.onStart();
        Log.d("hello", "onStart: ");
        intent = new Intent(getContext(),PlayerService.class);
        if(path!=null)
        {
            intent.putExtra("path",path);

        }
        getContext().bindService(intent,connect, Context.BIND_AUTO_CREATE);
        final Handler musicHandler = new Handler();
        Runnable musicRun = new Runnable() {
            @Override
            public void run() {
                if(mBound==true)
                {
                    if(duration==0)
                    {
                        duration = playerService.getDuration();
                        seekBar.setMax(duration);
                    }
                    //Log.d("here", String.valueOf(playerService.getCurrentPos()));
                    currentPos = playerService.getCurrentPos();
                    seekBar.setProgress(currentPos);

                }
                if(currentPos<duration)
                {
                    musicHandler.postDelayed(this,1000);
                }else
                {
                    musicHandler.removeMessages(0);
                }
            }
        };
        musicHandler.postDelayed(musicRun,1000);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            Log.d("hello", "onCreateView: ");
        }
        return inflater.inflate(R.layout.fragment_player, container, false);
    }






    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
         model.path= getArguments().getString("filepath");
        if(model.path!=null)
        {
            model.albumart = Uri.parse(getArguments().getString("albumart"));
            album.setImageURI(model.albumart);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        play = view.findViewById(R.id.play);
        //btn = view.findViewById(R.id.btn);
        pause = view.findViewById(R.id.pause);
        album = view.findViewById(R.id.albumart);
        reset = view.findViewById(R.id.reset);
        seekBar  = view.findViewById(R.id.seekBar);
        handler = new Handler();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(playerService.checkPlayer() && fromUser)
                {
                    playerService.seekBarChange(progress);
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
                playerService.pauseMusic();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(),PlayerService.class);
                //getContext().startService(intent);
                if(!playerService.isPlaying())
                {
                    seekBar.setMax(duration);
                    playerService.playMusic();
                }


            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerService.reset())
                {
                    seekBar.setProgress(0);
                    try {
                        playerService.InitializePlayertrying(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("hello", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hello", "onDestroy: ");
    }
}
