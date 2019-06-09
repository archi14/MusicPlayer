package com.example.archi.musicplayer;


import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    Button btn;
    ArrayList<MusicFiles> files;
    FilesAdapter adapter;
    RecyclerView recyclerView;
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.btn);
        recyclerView = view.findViewById(R.id.recyclerView);
        files = new ArrayList<>() ;
        String[] columns = {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DURATION};
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,columns,null,null,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do{
                    String display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String fullpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    int song_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String Duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    //Log.d("TAG", display_name+"id is"+String.valueOf(song_id)+"path is "+fullpath+Duration);
                    MusicFiles musicFile = new MusicFiles(display_name,fullpath,artist,album,song_id,Duration);
                    files.add(musicFile);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        adapter = new FilesAdapter(getContext(),files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Log.d("TAG", String.valueOf(files.size()));

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,Intent.CATEGORY_APP_BROWSER);
                //intent.setType("video/*");
                startActivityForResult(intent,1);
            }
        });*/
    }
}
