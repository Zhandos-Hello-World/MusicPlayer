package com.example.myapplication;

public interface RepositoryObserver {
    void onUserDataChanged(String music_name, int music_id);
}