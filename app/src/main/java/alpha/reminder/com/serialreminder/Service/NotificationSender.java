package alpha.reminder.com.serialreminder.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import alpha.reminder.com.serialreminder.Comparator.CustomComparator;
import alpha.reminder.com.serialreminder.DBHelper.DBHelper;
import alpha.reminder.com.serialreminder.Entity.Film;
import alpha.reminder.com.serialreminder.MainActivity;
import alpha.reminder.com.serialreminder.R;

/**
 * Created by jeka on 28.11.16.
 */

public class NotificationSender {

    private Context context;
    private Context appContext;

    public NotificationSender(Context context, Context appContext) {
        this.context = context;
        this.appContext = appContext;
    }

    public void sendNotification() {
        DBHelper dbHelper = new DBHelper(context);

        ArrayList<Film> list = dbHelper.getAllFilms();
        System.out.println(list.size());
        for (int j = list.size() - 1; j >= 0; j--) {
            if (list.get(j).getReleased().equals(""))
                list.remove(j);
        }

        list.trimToSize();
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<Film>() {
                @Override
                public int compare(Film lhs, Film rhs) {
                    if ((!lhs.getReleased().equals("")) || (!rhs.getReleased().equals(""))) {
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
            Context context = appContext;

            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context,
                    0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Resources res = context.getResources();
            android.app.Notification.Builder builder = new android.app.Notification.Builder(context);

            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.videocamera)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.videocamera))
                    .setTicker("Your favourite movie almost came!")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentText(bigText)
                    .setStyle(new android.app.Notification.BigTextStyle().bigText(bigText))
                    .setContentTitle("Don't miss your favourite movie!");

            android.app.Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(101, notification);
        }
    }
}
