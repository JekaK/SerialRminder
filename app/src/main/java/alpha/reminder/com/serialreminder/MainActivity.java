package alpha.reminder.com.serialreminder;

import android.app.FragmentManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private FragmentManager manager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
