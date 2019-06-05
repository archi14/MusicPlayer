package com.example.archi.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    Button btn;
    ArrayList<MusicFiles> files;
    FilesAdapter adapter;
    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        btn = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.recyclerView);
        files = new ArrayList<>() ;
        String[] columns = {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DURATION};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,columns,null,null,null,null);
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
        adapter = new FilesAdapter(getApplicationContext(),files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
