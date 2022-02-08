package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MusicDataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MusicTable";

    public static String[] namesOfMusics = {
        "Мама, мы все тяжело больны", "Группа крови", "В наших глазах", "Место для шага вперед",
        "Кукушка", "Песня без слов", "Попробуй спеть вместе со мной", "Стук", "Спокойная ночь",
        "Звезда по имени Солнце", "Это не любовь", "Музыка волн", "Бездельник",
        "Мы хотим танцевать", "Пачка сигарет", "Сказка", "Война", "Дальше действовать будем мы",
        "Мама анархия", "Прохожий", "Время есть а денег нет", "Троллейбус", "Невесёлая песня",
        "Когда твоя девушка больна", "Электричка", "Нам с тобой", "Бошетунмай",
        "Следи за собой", "Сосны на морском берегу", "Хочу перемен", "Красно-жёлтые дни", "Апрель",
            "Закрой за мной дверь, я ухожу", "Последний герой"
    };
    public static int[] music_id = {
            R.raw.music1, R.raw.music2, R.raw.music3,
            R.raw.music4, R.raw.music5, R.raw.music6,
            R.raw.music7, R.raw.music8, R.raw.music9,
            R.raw.music10, R.raw.music11, R.raw.music12,
            R.raw.music13, R.raw.music14, R.raw.music15,
            R.raw.music16, R.raw.music17, R.raw.music18,
            R.raw.music19, R.raw.music10, R.raw.music20,
            R.raw.music21, R.raw.music22, R.raw.music23,
            R.raw.music24, R.raw.music25, R.raw.music26,
            R.raw.music27, R.raw.music28, R.raw.music29,
            R.raw.music30, R.raw.music31, R.raw.music32,
            R.raw.music33, R.raw.music34};

    public static int size() {
        return music_id.length;
    }

    public MusicDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpdate(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpdate(db, oldVersion, newVersion);
    }

    public void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE MUSICS"
                    + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " MUSIC_NAME TEXT, "
                    + " MUSIC_RES_ID INTEGER,"
                    + " FAVOURITE NUMERIC);");

            for (int i = 0; i < namesOfMusics.length; i++) {
                insertDrink(db, namesOfMusics[i], music_id[i], 0);
            }
        }
    }
    private static void insertDrink(SQLiteDatabase db,
                                    String music_name,
                                    int music_res_id,
                                    int favourite) {
        ContentValues values = new ContentValues();
        values.put("MUSIC_NAME", music_name);
        values.put("MUSIC_RES_ID", music_res_id);
        values.put("FAVOURITE", favourite);
        db.insert("MUSICS", null, values);
    }

}
