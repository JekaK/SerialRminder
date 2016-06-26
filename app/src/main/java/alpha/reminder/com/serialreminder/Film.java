package alpha.reminder.com.serialreminder;

/**
 * Created by Eugeniy Krukun on 27.06.2016.
 */

public class Film {
    private String title;
    private String year;
    private String poster;

    public Film() {
    }

    public Film(String title, String year, String poster) {
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
