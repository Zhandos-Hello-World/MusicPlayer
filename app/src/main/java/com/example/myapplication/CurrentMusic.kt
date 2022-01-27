package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast


object CurrentMusic {
    val namesOfMusics = mutableListOf<String>()
    private val raws = mutableListOf<Int>()
    private var rawsFavourite = mutableListOf<Int>()
    get() {
        field.clear()
        for (i in raws.indices) {
            if (favourite[i]) {
                field.add(raws[i])
            }
        }
        return field
    }
    private val favourite = mutableListOf<Boolean>()
    val favouriteMusicList = mutableListOf<String>()
        get() {
            field.clear()
            for (i in namesOfMusics.indices) {
                if (favourite[i]) {
                    field.add(namesOfMusics[i])
                }
            }
            return field
        }
    private var currentNameMusic = ""
    var media: MediaPlayer? = null
    var favouritePage = false
    @JvmStatic
    var id: Int = 0
    set (value) {
        field = when {
            value < 0 -> {
                namesOfMusics.size - 1
            }
            value >= raws.size - 1 -> {
                0
            }
            else -> {
                value
            }
        }
    }
    private var task: Runnable? = null
    private val handler = Handler(Looper.myLooper()!!)
    private var subject: MusicObservableDataRepository? = null

    @JvmStatic
    fun currentNameOfMusic() : String {
        Log.d("Favourite page ?", favouritePage.toString())
        return currentNameMusic
    }

    @JvmStatic
    fun currentIsFavouriteOfMusic(): Boolean {
        return if (favouritePage) {
            true
        } else {
            if (id in favourite.indices)
                favourite[id]
            else
                false
        }
    }



    @JvmStatic
    fun setCurrentFavouriteMusic(newFavourite: Boolean) {
        if (favouritePage) {
            var temp = 0
            for (i in favourite.indices) {
                if (favourite[i]) {
                    if (temp == id) {
                        favourite[temp] = false
                        break
                    }
                    temp++
                }
            }
        }
        else {
            favourite[id] = newFavourite
        }
    }

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
        if (!favouritePage) {
            media = MediaPlayer.create(context, raws[id])
            currentNameMusic = namesOfMusics[id]
        }
        else {
            media = MediaPlayer.create(context, rawsFavourite[id])
            currentNameMusic = favouriteMusicList[id]
        }
        media!!.setOnCompletionListener{
            if (favouritePage) {
                //Todo
            }
            else {
                media = MediaPlayer.create(context, raws[++id])
                currentNameMusic = namesOfMusics[id]
                media?.start()
                subject?.setUserData(namesOfMusics[id], id)
                subject?.notifyObserver()
            }
        }

        //start
        media?.start()

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
    fun size() = namesOfMusics.size


    //for get data for music list. It will be worked when program is going to run
    @JvmStatic
    fun init(context: Context) {
        namesOfMusics.clear()
        raws.clear()
        rawsFavourite.clear()
        favourite.clear()
        favouriteMusicList.clear()
        val music = MusicDataBaseHelper(context)
        try {
            val db = music.readableDatabase
            val cursor = db.query("MUSICS", arrayOf("_id", "MUSIC_NAME", "MUSIC_RES_ID", "FAVOURITE"),
            null, null, null, null, null)

            if (cursor.moveToFirst()) {
                namesOfMusics.add(cursor.getString(1));
                raws.add(cursor.getInt(2))
                favourite.add(cursor.getInt(3) == 1)
            }
            while (cursor.moveToNext()) {
                namesOfMusics.add(cursor.getString(1));
                raws.add(cursor.getInt(2))
                favourite.add(cursor.getInt(3) == 1)            }

            db.close()
            cursor.close()
        } catch (ex: SQLiteException) {
            Toast.makeText(context, "Unavailable Database", Toast.LENGTH_LONG).show()
        }
    }

    //it's when will be worked when activity is destroyed
    @JvmStatic
    fun save(context: Context) {
        val music = MusicDataBaseHelper(context)
        try {
            val db = music.readableDatabase
            val value = ContentValues()

            for (i in namesOfMusics.indices) {
                value.put("FAVOURITE", if (favourite[i]) 1 else 0)
                db.update("MUSICS", value, "MUSIC_NAME = ?", arrayOf(namesOfMusics[i]))
            }

            db.close()
        } catch (ex: SQLiteException) {
            Toast.makeText(context, "Unavailable Database", Toast.LENGTH_LONG).show()
        }
    }
}