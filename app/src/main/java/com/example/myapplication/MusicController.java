package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MusicController extends AppCompatActivity implements RepositoryObserver {
    private TextView current;
    private SeekBar seekBar;
    private ImageButton previous;
    private ImageButton play;
    private ImageButton next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_controller_activity);


        current = findViewById(R.id.current);
        seekBar = findViewById(R.id.seekbar);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);


        current.setText(CurrentMusic.currentNameOfMusic());

        previous.setOnClickListener(key -> {
            CurrentMusic.setId(CurrentMusic.getId() - 1);
            CurrentMusic.startMusic(this, seekBar);
            current.setText(CurrentMusic.currentNameOfMusic());
            if (!CurrentMusic.initialized()) {
                CurrentMusic.startMusic(this, seekBar);
            }
            else {
                CurrentMusic.play();
            }
            play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
        });

        next.setOnClickListener(key -> {
            CurrentMusic.setId(CurrentMusic.getId() + 1);
            CurrentMusic.startMusic(this, seekBar);
            current.setText(CurrentMusic.currentNameOfMusic() );
            if (!CurrentMusic.initialized()) {
                CurrentMusic.startMusic(this, seekBar);
            }
            else {
                CurrentMusic.play();
            }
            play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
        });

        play.setOnClickListener(key -> {
            if (!CurrentMusic.initialized()) {
                play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
                CurrentMusic.startMusic(this, seekBar);
            }
            else {
                if (CurrentMusic.isPlaying()) {
                    play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white);
                    CurrentMusic.pause();
                }
                else {
                    play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
                    CurrentMusic.play();
                }
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        if (!CurrentMusic.initialized()) {
            play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white);
        }
        else {
            if (CurrentMusic.isPlaying()) {
                CurrentMusic.initializeSeekBar(seekBar);
                play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
            }
            else {
                play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white);
            }
        }
        super.onStart();
    }
    @Override
    protected void onResume() {
        if (!CurrentMusic.initialized()) {
            play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white);
        }
        else {
            if (CurrentMusic.isPlaying()) {
                CurrentMusic.initializeSeekBar(seekBar);
                play.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white);
            }
            else {
                play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white);
            }
        }
        super.onResume();
    }

    @Override
    public void onUserDataChanged(String music_name, int music_id) {
        current.setText(CurrentMusic.currentNameOfMusic());

    }
}
