package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class ListMusicController : AppCompatActivity() {
    private lateinit var listView: ListView
    private var favourite_btn: ImageButton? = null
    private var play_btn:ImageButton? = null
    private var name_music_current:TextView? = null
    private var favourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_music_acitvity)

        listView = findViewById(R.id.list_view)
        name_music_current = findViewById(R.id.name_music)
        play_btn = findViewById(R.id.play_btn)
        favourite_btn = findViewById(R.id.favourite_btn)


        listView.adapter = ArrayAdapter(this, R.layout.list_component, R.id.name_music, CurrentMusic.namesOfMusics)
        listView.setOnItemClickListener{ parent, v, position, id ->
            CurrentMusic.id = id.toInt()
            CurrentMusic.startMusic(this, null)
            current()
        }
        play_btn?.setOnClickListener {
            if (CurrentMusic.isPlaying()) {
                CurrentMusic.pause()
                play_btn?.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white)
            }
            else {
                if (!CurrentMusic.initialized()) {
                    CurrentMusic.startMusic(this, null)
                }
                else {
                    CurrentMusic.play()
                }
                play_btn?.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white)
            }
        }
        favourite_btn?.setOnClickListener {
            favourite = if (favourite) {
                favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
                false
            }
            else {
                favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24_white)
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        current()
    }

    private fun current() {
        if (CurrentMusic.isPlaying()) {
            play_btn?.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white)
        }
        else {
            play_btn?.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white)
        }
        name_music_current?.text = CurrentMusic.currentNameOfMusic()
    }
}