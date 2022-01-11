package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

import androidx.core.view.isGone


class ListMusicController:AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var playBtn: ImageButton
    lateinit var stopBtn: ImageButton
    lateinit var previous: ImageButton
    lateinit var next: ImageButton
    lateinit var current: TextView
    lateinit var mini_controller: LinearLayout
    lateinit var seekBar: SeekBar
    lateinit var show: Button
    private var play = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_music_acitvity)
        init()


        mini_controller.isGone = true


        listView.adapter = ArrayAdapter(this, R.layout.activity_listview, R.id.name_music, CurrentMusic.countryList)
        listView.setOnItemClickListener{ parent, v, position, id ->
            CurrentMusic.id = id.toInt()
            current.text = CurrentMusic.name()
            CurrentMusic.playMusic(this, seekBar)
            mini_controller.isGone = false
            play = true
        }

        next.setOnClickListener {
            CurrentMusic.id += 1
            CurrentMusic.playMusic(this, seekBar)
            current.text = CurrentMusic.name()
        }

        previous.setOnClickListener {
            CurrentMusic.id -= 1
            CurrentMusic.playMusic(this, seekBar)
            current.text = CurrentMusic.name()
        }

        playBtn.setOnClickListener {
            CurrentMusic.play()
        }
        stopBtn.setOnClickListener {
            CurrentMusic.pause()
        }
        show.setOnClickListener {
            startActivity(Intent(this, MusicController::class.java))
        }
        mini_controller.setOnClickListener {  }
    }

    private fun init() {
        listView = findViewById(R.id.list_view)
        current = findViewById(R.id.current)
        next = findViewById(R.id.next)
        previous = findViewById(R.id.previous)
        playBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        mini_controller = findViewById(R.id.mini_controller)
        show = findViewById(R.id.show)
        mini_controller.isGone = true
        seekBar = findViewById(R.id.seekbar)
    }

    override fun onStart() {
        super.onStart()
        listView.setSelection(CurrentMusic.id)
    }
}