package alpha.reminder.com.serialreminder;

import android.app.FragmentManager;
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

import alpha.reminder.com.serialreminder.Fragments.FavouriteFragment;
import alpha.reminder.com.serialreminder.Fragments.SearchFragment;
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
