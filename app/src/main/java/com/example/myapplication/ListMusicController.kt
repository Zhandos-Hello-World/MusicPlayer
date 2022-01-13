package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class ListMusicController:AppCompatActivity(){
    lateinit var listView: ListView
    private var play = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_music_acitvity)

        listView = findViewById(R.id.list_view)


        listView.adapter = ArrayAdapter(this, R.layout.activity_listview, R.id.name_music, CurrentMusic.countryList)
        listView.setOnItemClickListener{ parent, v, position, id ->
            CurrentMusic.id = id.toInt()
            play = true
        }
    }

    override fun onStart() {
        super.onStart()
        listView.setSelection(CurrentMusic.id)
    }
    fun onClickArrow(view: android.view.View) {
        startActivity(Intent(this, MusicController::class.java))
    }
}