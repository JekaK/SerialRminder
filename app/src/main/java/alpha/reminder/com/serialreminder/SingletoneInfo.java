package alpha.reminder.com.serialreminder;

import java.util.ArrayList;

/**
 * Created by kruku on 07.06.2016.
 */

public class SingletoneInfo {
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private final static SingletoneInfo info = new SingletoneInfo();

    public static SingletoneInfo getInstance() {
        return info;
    }

    private SingletoneInfo() {
        addDescription("sdsad");
        addDescription("sdsad");
        addDescription("sdsad");

        addTitle("sdsad");
        addTitle("sdsad");
        addTitle("sdsad");
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    public void addDescription(String description) {
        descriptions.add(description);
    }
}
