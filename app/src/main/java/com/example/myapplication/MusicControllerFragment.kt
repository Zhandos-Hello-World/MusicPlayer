package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class MusicControllerFragment : Fragment() {
    private var favourite_btn: ImageButton? = null
    private var favourite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_controller, container, false)
    }

    override fun onStart() {
        super.onStart()

        play_btn = view?.findViewById(R.id.play_btn)

        onStartPlayButton()
        play_btn?.setOnClickListener {
            if (CurrentMusic.isPlaying()) {
                CurrentMusic.pause()
                play_btn?.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white)
            }
            else {
                CurrentMusic.play()
                play_btn?.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white)
            }
        }
        favourite_btn = view?.findViewById(R.id.favourite_btn)

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


        textView = view?.findViewById(R.id.name_music)
        textView?.text = CurrentMusic.name()
    }


    companion object {
        private var play_btn:ImageButton? = null
        private var textView:TextView? = null
        fun change_name() {
            textView?.text = CurrentMusic.name()
            onStartPlayButton()
        }
        private fun onStartPlayButton() {
            if (CurrentMusic.isPlaying()) {
                play_btn?.setBackgroundResource(R.drawable.ic_sharp_pause_circle_outline_24_white)
            }
            else {
                play_btn?.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_white)
            }
        }
    }
}