package com.example.archi.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<MusicFiles> musicFiles;

    FilesAdapter(Context context,ArrayList<MusicFiles> musicFiles)
    {
        this.context = context;
        this.musicFiles = musicFiles;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.musiclist,viewGroup,false);
        return  new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        final MusicFiles file = musicFiles.get(i);
        String name = file.getDisplay_name();
        if(name.lastIndexOf(".")>0)
        {
            name = name.substring(0,name.lastIndexOf("."));
        }
        holder.name.setText(name);
        holder.artist.setText(file.getArtist());
        holder.album.setText(file.getAlbum());
        Glide.with(context).asBitmap().load(file.getAlbumart()).placeholder(R.drawable.placeholder).into(holder.albumart);
        //holder.albumart.setText(file.getAlbumart());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("file",file.getPath());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","adapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name,artist,album;
        ImageView albumart;
         public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            artist = itemView.findViewById(R.id.artist);
            album = itemView.findViewById(R.id.album);
            albumart = itemView.findViewById(R.id.albumart);
        }
    }
}
