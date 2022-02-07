package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast

class MusicList(context: Context) {
    val nameOfMusics = mutableListOf<String>()
    private val musicsID = mutableListOf<Int>()

    private val favouriteMusics = mutableListOf<Boolean>()

    val nameOfMusicsFavourite = mutableListOf<String>()
    private val musicsIDFavourite = mutableListOf<Int>()

    var isFavourite = false

    var favouritePage = false


    //for get data for music list. It will be worked when created object
    init {
        nameOfMusics.clear()
        musicsID.clear()
        favouriteMusics.clear()
        nameOfMusicsFavourite.clear()
        musicsIDFavourite.clear()


        val musicData = MusicDataBaseHelper(context)
        try {
            val db = musicData.readableDatabase
            val cursor = db.query("MUSICS",
                arrayOf("_id", "MUSIC_NAME", "MUSIC_RES_ID", "FAVOURITE"),
        null, null, null, null, null, null)

            if (cursor.isFirst) {
                nameOfMusics.add(cursor.getString(1))
                musicsID.add(cursor.getInt(2))
                favouriteMusics.add(cursor.getInt(3) == 1)

                if (favouriteMusics.last()) {
                    nameOfMusicsFavourite.add(nameOfMusics.last())
                    musicsIDFavourite.add(musicsID.last())
                }
            }

            while (cursor.moveToNext()) {
                nameOfMusics.add(cursor.getString(1))
                musicsID.add(cursor.getInt(2))
                favouriteMusics.add(cursor.getInt(3) == 1)

                if (favouriteMusics.last()) {
                    nameOfMusicsFavourite.add(nameOfMusics.last())
                    musicsIDFavourite.add(musicsID.last())
                }
            }

            db.close()
            cursor.close()
        } catch (ex: SQLiteException) {
            print("Unavailable database")
        }
    }


    fun getNameCurrentMusic(id: Int): String {
        return if (isFavourite || favouritePage)
            if (check(id))
                nameOfMusicsFavourite[id]
            else
                "NULL"
        else
            nameOfMusics[id]
    }


    fun getResIDCurrentMusic(id: Int): Int {
        return if (isFavourite || favouritePage)
            if (check(id))
                musicsIDFavourite[id]
            else
                -1
        else
            musicsID[id]
    }

    private fun check(id: Int) = id in 0 until nameOfMusicsFavourite.size

    fun isFavourite(id: Int): Boolean {
        if (favouritePage) {
            return true
        }
        return favouriteMusics[id]
    }
    fun addFavourite(id: Int) {
        if (!favouritePage) {
            favouriteMusics[id] = true

            if (!nameOfMusicsFavourite.contains(nameOfMusics[id])) {
                nameOfMusicsFavourite.add(nameOfMusics[id])
                musicsIDFavourite.add(musicsID[id])
            }
        }
    }

    fun removeFavourite(id: Int) {
        if (!favouritePage) {
            favouriteMusics[id] = false

            if (nameOfMusicsFavourite.contains(nameOfMusics[id])) {
                val index = nameOfMusicsFavourite.indexOf(nameOfMusics[id])
                nameOfMusicsFavourite.removeAt(index)
                musicsIDFavourite.removeAt(index)
            }
        }
    }


    fun save(context: Context) {
        val music = MusicDataBaseHelper(context)
        try {
            val db = music.readableDatabase
            val value = ContentValues()

            for (i in nameOfMusics.indices) {
                value.put("FAVOURITE", if (favouriteMusics[i]) 1 else 0)

                db.update("MUSICS", value, "MUSIC_NAME = ?",
                    arrayOf(nameOfMusics[i]))
            }
            db.close()
        } catch (ex: SQLiteException) {
            Toast.makeText(context, "Unavailable Database", Toast.LENGTH_LONG).show()
        }
    }
}