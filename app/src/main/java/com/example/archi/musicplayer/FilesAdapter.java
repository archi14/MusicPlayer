package com.example.archi.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<MusicFiles> musicFiles;
    Activity activity;
    FilesAdapter(Activity activity,Context context,ArrayList<MusicFiles> musicFiles)
    {
        this.activity = activity;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =  new Bundle();
                bundle.putString("filepath",file.getPath());
                bundle.putString("albumart",file.getAlbumart().toString());
                Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.playerFragment3,bundle);
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
