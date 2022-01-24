package com.example.myapplication;

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObserver();
}
