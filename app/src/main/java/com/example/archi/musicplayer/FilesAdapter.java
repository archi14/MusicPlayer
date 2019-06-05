package com.example.archi.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        MusicFiles file = musicFiles.get(i);
        holder.name.setText(file.getDisplay_name());
        holder.artist.setText(file.getArtist());
        holder.album.setText(file.getAlbum());
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name,artist,album;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            artist = itemView.findViewById(R.id.artist);
            album = itemView.findViewById(R.id.album);
        }
    }
}
