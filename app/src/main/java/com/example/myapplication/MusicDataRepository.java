package com.example.myapplication;

import java.util.ArrayList;

public class MusicDataRepository implements Subject{
    private String music_name;
    private int music_id;
    private static MusicDataRepository INSTANCE;

    private ArrayList<RepositoryObserver> mObserver;

    public MusicDataRepository() {
        mObserver = new ArrayList<>();
    }

    public static MusicDataRepository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MusicDataRepository();
        }
        return INSTANCE;
    }
    public void setUserData(String music_name, int music_id) {
        this.music_id = music_id;
        this.music_name = music_name;
    }


    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if (!mObserver.contains(repositoryObserver)) {
            mObserver.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        mObserver.remove(repositoryObserver);
    }

    @Override
    public void notifyObserver() {
        for (RepositoryObserver observer:mObserver) {
            observer.onUserDataChanged(music_name, music_id);
        }
    }
}
