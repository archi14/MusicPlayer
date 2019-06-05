package com.example.archi.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {
    Button btn;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        btn = findViewById(R.id.btn);
        String[] columns = {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.IS_RINGTONE, MediaStore.Audio.Media.IS_ALARM, MediaStore.Audio.Media.IS_NOTIFICATION, MediaStore.Audio.Media.DURATION};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,columns,null,null,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do{
                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    int song_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String fullpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String Duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    Log.d("TAG", song_name+"id is"+String.valueOf(song_id)+"path is "+fullpath+Duration);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,Intent.CATEGORY_APP_BROWSER);
                //intent.setType("video/*");
                startActivityForResult(intent,1);
            }
        });

    }
}
