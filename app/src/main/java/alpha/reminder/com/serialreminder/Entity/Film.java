package alpha.reminder.com.serialreminder.Entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Eugeniy Krukun on 27.06.2016.
 */

public class Film {
    private String title;
    private String year;
    private Bitmap poster;
    private String type;
    private String id;
    private String description;

    public Film() {
    }

    public Film(String title, String year, Bitmap poster, String type) {
        this.title = title;
        this.year = year;
        this.poster = poster;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public byte[] getPosterBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        poster.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = BitmapFactory.decodeByteArray(poster, 0, poster.length);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
