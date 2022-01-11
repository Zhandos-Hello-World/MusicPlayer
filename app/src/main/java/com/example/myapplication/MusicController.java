package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MusicController extends AppCompatActivity {
    private TextView current;
    private SeekBar seekBar;
    private ImageButton previous;
    private ImageButton play;
    private ImageButton next;
    private ImageButton pause;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_controller);
        init();


        current.setText(CurrentMusic.name());

        previous.setOnClickListener(key -> {
            CurrentMusic.setId(CurrentMusic.getId() - 1);
            CurrentMusic.playMusic(this, seekBar);
            current.setText(CurrentMusic.name());
        });

        next.setOnClickListener(key -> {
            CurrentMusic.setId(CurrentMusic.getId() + 1);
            CurrentMusic.playMusic(this, seekBar);
            current.setText(CurrentMusic.name() );
        });

        play.setOnClickListener(key -> {
            CurrentMusic.play();
        });
        pause.setOnClickListener(key -> {
            CurrentMusic.pause();
        });
    }
    private void init() {
        current = findViewById(R.id.current);
        seekBar = findViewById(R.id.seekbar);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        pause = findViewById(R.id.stopBtn);
    }
}
