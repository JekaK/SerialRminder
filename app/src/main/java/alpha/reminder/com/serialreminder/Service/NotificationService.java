package alpha.reminder.com.serialreminder.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import alpha.reminder.com.serialreminder.Comparator.CustomComparator;
import alpha.reminder.com.serialreminder.DBHelper.DBHelper;
import alpha.reminder.com.serialreminder.Entity.Film;

/**
 * Created by jeka on 20.11.16.
 */

public class NotificationService extends Service {
    private NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendNotification() {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Film> list = dbHelper.getAllFilms();
        Collections.sort(list, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                if ((!lhs.getReleased().equals("")) && (!rhs.getReleased().equals(""))) {
                    if (lhs.getReleased().equals(rhs.getReleased())) {
                        return 0;
                    }
                }

                return new CustomComparator().compare(lhs, rhs);
            }
        });
        Notification notification = new Notification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
