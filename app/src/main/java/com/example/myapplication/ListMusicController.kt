package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class ListMusicController : AppCompatActivity(), MusicParentListFragment.Companion.Listener, RepositoryObserver {
    private var favourite_btn: ImageButton? = null
    private var play_btn:ImageButton? = null
    private var name_music_current: TextView? = null
    private var favourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CurrentMusic.init(this)

        setContentView(R.layout.list_music_acitvity)

        name_music_current = findViewById(R.id.name_music)
        play_btn = findViewById(R.id.play_btn)
        favourite_btn = findViewById(R.id.favourite_btn)

        setSupportActionBar(findViewById(R.id.toolbar))
        val sectionBar = SectionsPagerAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.pager)
        pager.adapter = sectionBar

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(pager)

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
                favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24_white)
                false
            }
            else {
                favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
                true
            }
            Log.d("Install", "Favourite")
            CurrentMusic.setCurrentFavouriteMusic(favourite)
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

        favourite = CurrentMusic.currentIsFavouriteOfMusic()

        if (favourite) {
            favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
        }
        else {
            favourite_btn?.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24_white)
        }

    }

    fun onClickArrow(view: View) {
        startActivity(Intent(this, MusicController::class.java))
    }

    override fun selected(id: Int) {
        CurrentMusic.id = id
        CurrentMusic.startMusic(this, null)
        current()
    }

    private class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

        override fun getCount() = 2
        override fun getItem(position: Int) =
            when (position) {
                0 -> MusicListFragment()
                else -> FavouriteListFragment()
            }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Альбом"
                1 -> return "Любимые"
            }
            return super.getPageTitle(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("Destroyed", "true")
        Toast.makeText(this, "hELLO", Toast.LENGTH_LONG).show()
        CurrentMusic.save(this)
        super.onSaveInstanceState(outState)
    }

    override fun onUserDataChanged(music_name: String?, music_id: Int) {
        name_music_current?.text = CurrentMusic.namesOfMusics[music_id]
    }
}