package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.widget.SeekBar


object CurrentMusic {
    var countryList = arrayOf("Мама, мы все тяжело больны", "Группа крови", "В наших глазах", "Место для шага вперед", "Кукушка", "Песня без слов",
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
        if (value < 0) {
            field = countryList.size - 1
        }
        else if (value >= raws.size - 1) {
            field = 0
        }
        else {
            field = value
        }
    }
    private var duration:Long = -1


    @JvmStatic
    fun name() = countryList[id]
    @JvmStatic
    fun playMusic(context: Context, seekbar: SeekBar? = null) {
        if (media != null) {
            media?.stop()
        }
        media = MediaPlayer.create(context, raws[id])
        media?.start()

    }
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