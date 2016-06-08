package alpha.reminder.com.serialreminder;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by kruku on 07.06.2016.
 */

public class SingletoneInfo {
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<Bitmap> poster = new ArrayList<>();
    private final static SingletoneInfo info = new SingletoneInfo();

    public static SingletoneInfo getInstance() {
        return info;
    }

    private SingletoneInfo() {

    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getYears() {
        return years;
    }

    public ArrayList<Bitmap> getPoster() {
        return poster;
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    public void addDescription(String description) {
        years.add(description);
    }
    public void addImage(Bitmap bitmap){
        poster.add(bitmap);
    }

    public void clearAll() {
        titles = new ArrayList<>();
        years = new ArrayList<>();
        poster = new ArrayList<>();
    }
    public void showTitles(){
        for (String s: titles) {
            System.out.println(s);
        }
    }
}
