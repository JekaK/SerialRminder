package alpha.reminder.com.serialreminder.Comparator;

import alpha.reminder.com.serialreminder.Entity.Film;

/**
 * Created by jeka on 20.11.16.
 */

public class CustomComparator {
    enum Months {
        Jan, Feb,
        Mar, Apr,
        May, Jun,
        Jul, Aug,
        Sep, Oct,
        Nov, Dec
    }

    public CustomComparator() {
    }

    private Months finder(String s) {
        for (Months i : Months.values()) {
            if (s.equals(i.toString())) {
                return i;
            }
        }
        return null;
    }

    private String[] divider(String s) {
        String[] mass = s.split(" ");
        return mass;
    }

    public int compare(Film lhs, Film rhs) {
        String[] mass1 = divider(lhs.getReleased()), mass2 = divider(rhs.getReleased());
        int isDay = 0, isMonth = 0, isYear = 0;

        if (Integer.parseInt(mass1[2]) == Integer.parseInt(mass2[2]) && mass1[1].equals(mass2[1]) && Integer.parseInt(mass1[0]) == Integer.parseInt(mass2[0])) {
            return 0;
        }
        if (Integer.parseInt(mass1[2]) < Integer.parseInt(mass2[2])) {
            isYear = -1;
        } else {
            if (Integer.parseInt(mass1[2]) == Integer.parseInt(mass2[2])) {
                isYear = 0;
            } else {
                isYear = 1;
            }
        }
        if (finder(mass1[1]).compareTo(finder(mass2[1]))>0) {
            isMonth = -1;
        } else {
            if (mass1[1].equals(mass2[1])) {
                isMonth = 0;
            } else {
                isMonth = 1;
            }
        }

        if (Integer.parseInt(mass1[0]) < Integer.parseInt(mass2[0])) {
            isDay = -1;
        } else {
            if (Integer.parseInt(mass1[0]) == Integer.parseInt(mass2[0])) {
                isDay = 0;
            } else {
                isDay = 1;
            }
        }
        return isYear != 0 ? isYear : isMonth != 0 ? isMonth : isDay != 0 ? isDay : 0;
    }
}
