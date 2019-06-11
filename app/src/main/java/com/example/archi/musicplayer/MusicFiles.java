package com.example.archi.musicplayer;

import android.net.Uri;

public class MusicFiles {
    private String display_name;
    private String path;
    private String artist;
    private String album;
    private int id;
    private String duration;
    private Uri Albumart;

    public MusicFiles(String display_name, String path, String artist, String album, int id, String duration, Uri albumart) {
        this.display_name = display_name;
        this.path = path;
        this.artist = artist;
        this.album = album;
        this.id = id;
        this.duration = duration;
        Albumart = albumart;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Uri getAlbumart() {
        return Albumart;
    }

    public void setAlbumart(Uri albumart) {
        Albumart = albumart;
    }
}