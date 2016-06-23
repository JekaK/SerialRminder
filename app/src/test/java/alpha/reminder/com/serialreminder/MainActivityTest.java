package alpha.reminder.com.serialreminder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import static org.junit.Assert.assertNotNull;

/**
 * Created by Eugeniy Krukun on 23.06.2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 18)

public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }
    @Test
    public void shouldNotBeNull(){
        assertNotNull(activity);
    }
    @Test
    public void shouldHaveBottomNavigation(){
        assertNotNull(activity.findViewById(R.id.bottom_navigation));
    }
}
