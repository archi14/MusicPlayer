package com.example.archi.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.RecyclerViewHolder> {
    //Context conte
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
