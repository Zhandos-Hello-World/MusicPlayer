package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.isGone






class ListMusicController:AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var playBtn: ImageButton
    lateinit var stopBtn: ImageButton
    lateinit var previous: ImageButton
    lateinit var next: ImageButton
    lateinit var current: TextView
    lateinit var mini_controller: LinearLayout
    private var play = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_music_acitvity)
        init()


        mini_controller.isGone = true
        mini_controller.setOnClickListener {
            startActivity(Intent(this, MusicController::class.java))
        }


        listView.adapter = ArrayAdapter(this, R.layout.activity_listview, R.id.name_music, CurrentMusic.countryList)
        listView.setOnItemClickListener{ parent, v, position, id ->
            Log.d("selected item", "$id")
            CurrentMusic.id = id.toInt()
            current.setText(CurrentMusic.name())
            CurrentMusic.playMusic(this)
            mini_controller.isGone = false
            play = true
        }

        next.setOnClickListener {
            CurrentMusic.id += 1
            CurrentMusic.playMusic(this)
        }

        previous.setOnClickListener {
            CurrentMusic.id -= 1
            CurrentMusic.playMusic(this)
        }

        playBtn.setOnClickListener {
            CurrentMusic.play()
        }
        stopBtn.setOnClickListener {
            CurrentMusic.pause()
        }
    }

    private fun init() {
        listView = findViewById(R.id.list_view)
        current = findViewById(R.id.current)
        next = findViewById(R.id.next)
        previous = findViewById(R.id.previous)
        playBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        mini_controller = findViewById(R.id.mini_controller)
        mini_controller.isGone = true
    }
}