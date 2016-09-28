package alpha.reminder.com.serialreminder.Singletone;

import java.util.ArrayList;

import alpha.reminder.com.serialreminder.Entity.Film;

/**
 * Created by kruku on 07.06.2016.
 */

public class SingletoneInfo {
    private ArrayList<Film> films;
    private final static SingletoneInfo info = new SingletoneInfo();

    public static SingletoneInfo getInstance() {
        return info;
    }

    private SingletoneInfo() {
        films = new ArrayList<>();
    }

    public void clearAll() {
       films = new ArrayList<>();
    }
    public void showTitles(){
        for (Film s: films) {
            System.out.println(s.getTitle());
        }
    }
    public void addFilm(Film film){
        films.add(film);
    }
    public ArrayList<Film> getFilms() {
        return films;
    }
}
