package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar


@SuppressLint("StaticFieldLeak")
object CurrentMusic {
    var namesOfMusics = arrayOf("Мама, мы все тяжело больны", "Группа крови", "В наших глазах", "Место для шага вперед", "Кукушка", "Песня без слов",
        "Попробуй спеть вместе со мной", "Стук", "Спокойная ночь", "Звезда по имени Солнце", "Это не любовь", "Музыка волн",
        "Бездельник", "Мы хотим танцевать", "Пачка сигарет", "Сказка", "Война", "Дальше действовать будем мы", "Мама анархия", "Прохожий", "Время есть а денег нет", "Троллейбус", "Невесёлая песня", "Когда твоя девушка больна", "Электричка", "Стук", "Нам с тобой", "Бошетунмай",
        "Следи за собой", "Скоро кончится лето")
    private val raws = listOf(
        R.raw.music1,
        R.raw.music2,
        R.raw.music3,
        R.raw.music4,
        R.raw.music5,
        R.raw.music6,
        R.raw.music7,
        R.raw.music8,
        R.raw.music9,
        R.raw.music10,
        R.raw.music11,
        R.raw.music12,
        R.raw.music13,
        R.raw.music14,
        R.raw.music15,
        R.raw.music16,
        R.raw.music17,
        R.raw.music18,
        R.raw.music19,
        R.raw.music10,
        R.raw.music20,
        R.raw.music21,
        R.raw.music22,
        R.raw.music23,
        R.raw.music24,
        R.raw.music25,
        R.raw.music26,
        R.raw.music27,
        R.raw.music28,
        R.raw.music29,
        R.raw.music30)
    var media: MediaPlayer? = null
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

    @JvmStatic
    fun currentNameOfMusic() = namesOfMusics[id]
    @JvmStatic
    fun startMusic(context: Context, seekbar: SeekBar? = null) {
        if (media != null) {
            media?.stop()
        }
        media = MediaPlayer.create(context, raws[id])
        media?.start()
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
        initializeSeekBar(seekbar)
    }
    @JvmStatic
    fun initializeSeekBar(seekbar: SeekBar?) {
        seekbar?.max = media?.duration?:0

        seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    media?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

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
}