package alpha.reminder.com.serialreminder.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import alpha.reminder.com.serialreminder.Comparator.CustomComparator;
import alpha.reminder.com.serialreminder.DBHelper.DBHelper;
import alpha.reminder.com.serialreminder.Entity.Film;
import alpha.reminder.com.serialreminder.MainActivity;
import alpha.reminder.com.serialreminder.R;

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
        sendNotification();
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

        for (Film i : list) {
            System.out.println(i.getReleased());
        }
        String bigText = list.get(list.size() - 1).getTitle() + " released in " + list.get(list.size() - 1).getReleased();
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.videocamera)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.videocamera))
                .setTicker("Your favourite movie almost came!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText(bigText)
                .setStyle(new Notification.BigTextStyle().bigText(bigText))
                .setContentTitle("Don't miss your favourite movie!");

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(101, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
