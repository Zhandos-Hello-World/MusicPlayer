package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MusicControllerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_music_controller, container, false) as ViewGroup


        val textView = root.findViewById<TextView>(R.id.name_music)
        textView?.text = "Hello World"



        return inflater.inflate(R.layout.fragment_music_controller, container, false)
    }
}