package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar

object CurrentMusic {
    @JvmStatic
    var currentNameMusic = ""
    private var mList:MusicList? = null
    private var media: MediaPlayer? = null
    @JvmStatic
    var id: Int = 0
    set (value) {
        field = if (isFavouriteOption()) {
            val max = mList?.nameOfMusicsFavourite?.size?:-1
            if (value !in 0 until max) {
                0
            } else {
                value
            }
        } else {
            if (value in 0 until (mList?.nameOfMusics?.size?:1)) {
                value
            } else {
                0
            }
        }
    }

    private var task: Runnable? = null
    private val handler = Handler(Looper.myLooper()!!)
    private var subject: MusicObservableDataRepository? = null

    @JvmStatic
    fun startMusic(context: Context, seekbar: SeekBar? = null) {
        //For getting Observer and putting to Observable
        if (subject == null) {
            subject = MusicObservableDataRepository.getINSTANCE()
        }
        if (context is RepositoryObserver) {
            subject!!.registerObserver(context)
        }

        //if music is playing and user want listen next music we need to stop current
        if (media != null) {
            media?.stop()
        }

        //Initializing media and connect next music if current is ended
        media = MediaPlayer.create(context, mList?.getResIDCurrentMusic(id)!!)
        //start
        media?.start()
        currentNameMusic = mList?.getNameCurrentMusic(id)!!

        media!!.setOnCompletionListener {
            id++
            if (mList?.isFavourite!!) {
                if (id >= mList!!.nameOfMusicsFavourite.size) {
                    id = 0
                }
            }
            else {
                if (id >= mList!!.nameOfMusics.size) {
                    id = 0
                }
            }
            media = MediaPlayer.create(context, mList?.getResIDCurrentMusic(id)!!)
            currentNameMusic = mList?.getNameCurrentMusic(id)!!
            media?.start()

            subject?.setUserData(mList?.getNameCurrentMusic(id)!!, id)
            subject?.notifyObserver()

        }
        //if layout has seekbar
        if (seekbar != null) {
            initializeSeekBar(seekbar)
        }
    }

    //if layout has Seekbar
    @JvmStatic
    fun initializeSeekBar(seekbar: SeekBar?) {
        seekbar?.max = media?.duration?:0
        seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    //if user want to seek the music
                    media?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //thread for controlling
        task = object : Runnable {
            override fun run() {
                try {
                    seekbar?.progress = media!!.currentPosition
                    handler.postDelayed(this, 1000)
                }
                catch (ex: Exception) {
                    seekbar!!.progress = 0
                }
            }
        }
        handler.post(task!!)
    }

    @JvmStatic
    fun isPlaying() = media?.isPlaying ?: false

    @JvmStatic
    fun initialized() = media != null

    @JvmStatic
    fun pause() {
        if (media != null) {
            media?.pause()
        }
    }
    @JvmStatic
    fun play() {
        if (media != null) {
            media?.start()
        }
    }

    @JvmStatic
    fun getMusics() = mList?.nameOfMusics ?: mutableListOf()

    @JvmStatic
    fun getFavouriteMusic() = mList?.nameOfMusicsFavourite ?: mutableListOf()

    @JvmStatic
    fun init(context: Context) {
        if (mList == null) {
            mList = MusicList(context)
        }
    }
    @JvmStatic
    fun setFavourite(fav: Boolean) {
        if (fav) {
            mList?.addFavourite(id)
        }
        else {
            mList?.removeFavourite(id)
        }
    }

    @JvmStatic
    fun setFavouriteOption(fav: Boolean) {
        mList?.isFavourite = fav
    }
    @JvmStatic
    fun isFavouriteOption(): Boolean {
        return mList?.isFavourite?:false
    }
    @JvmStatic
    fun inFavourite(): Boolean {
        val max = mList?.nameOfMusicsFavourite?.size?:-1
        if (max > 0) {
            return id in 0 until max
        }
        return false
    }
    @JvmStatic
    fun favouriteIsEmpty() = mList?.nameOfMusicsFavourite?.size == 0

    @JvmStatic
    fun isFavourite() = mList?.isFavourite(id) ?: false

    @JvmStatic
    fun save(context: Context) {
        mList?.save(context)
    }

    fun setFavoritePage(page: Boolean) {
        mList?.favouritePage = page
    }
}