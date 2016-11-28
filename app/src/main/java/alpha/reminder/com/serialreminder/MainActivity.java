package alpha.reminder.com.serialreminder;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import alpha.reminder.com.serialreminder.Fragments.FavouriteFragment;
import alpha.reminder.com.serialreminder.Fragments.SearchFragment;
import alpha.reminder.com.serialreminder.Service.CustomReceiver;
import alpha.reminder.com.serialreminder.Service.NotificationService;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container, new FavouriteFragment()).commit();

        initBottomBar();
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switcher(position);
            }
        });

       startAlarming();
    }
    public void startAlarming(){
        Intent notifyIntent = new Intent(this,CustomReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    private void switcher(int position) {
        switch (position) {
            case 0: {
                manager.beginTransaction().replace(R.id.container, new FavouriteFragment()).commit();

                break;
            }
            case 1: {
                manager.beginTransaction().replace(R.id.container, new SearchFragment()).commit();
                break;
            }
        }
    }

    public void initBottomBar() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_star_black_48dp, R.color.test_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_magnify_black_48dp, R.color.test_2);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setForceTint(true);

        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, NotificationService.class));
    }
}
