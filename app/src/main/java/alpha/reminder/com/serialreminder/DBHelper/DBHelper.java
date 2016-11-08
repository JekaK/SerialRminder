package alpha.reminder.com.serialreminder.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import alpha.reminder.com.serialreminder.Entity.Film;

/**
 * Created by Eugeniy Krukun on 27.06.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    //TODO:Add update method for adding info about film
    private SQLiteDatabase database;
    private final String MY_LOG = "LOG";
    private final String TABLE_NAME = "Films";
    private final String TITLE = "title";
    private final String YEAR = "year";
    private final String POSTER = "poster";
    private final String TYPE = "type";
    private final String FILM_ID = "film_id";
    private final String RELEASED = "released";

    private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            "id integer primary key autoincrement," +
            TITLE + " text," +
            YEAR + " text," +
            POSTER + " BLOB," +
            TYPE + " text," +
            FILM_ID + " text," +
            RELEASED + " text" +
            ");";

    public DBHelper(Context context) {
        super(context, "filmsDb", null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void insertFilm(Film film) {
        ContentValues cv = new ContentValues();
        cv.put("title", film.getTitle());
        cv.put("year", film.getYear());
        cv.put("poster", film.getPosterBytes());
        cv.put("type", film.getType());
        cv.put("film_id", film.getId());
        cv.put("released", film.getReleased());
        long rowID = database.insert(TABLE_NAME, null, cv);
        Log.d(MY_LOG, "Inserted to " + rowID);
        this.close();
    }

    public void deleteFilm(Film film) {
        int delete = database.delete(TABLE_NAME, TITLE + " = \"" + film.getTitle() + "\"", null);
        Log.d("Some Tag", "Int del = " + delete);
    }

    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> films = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int title = cursor.getColumnIndex("title");
            int year = cursor.getColumnIndex("year");
            int poster = cursor.getColumnIndex("poster");
            int type = cursor.getColumnIndex("type");
            int film_id = cursor.getColumnIndex("film_id");
            int released = cursor.getColumnIndex("released");
            do {
                Film film = new Film();
                film.setTitle(cursor.getString(title));
                film.setYear(cursor.getString(year));
                film.setPoster(cursor.getBlob(poster));
                film.setType(cursor.getString(type));
                film.setId(cursor.getString(film_id));
                film.setReleased(cursor.getString(released));
                films.add(film);
            } while (cursor.moveToNext());
            cursor.close();
            this.close();
        }
        return films;
    }
}
