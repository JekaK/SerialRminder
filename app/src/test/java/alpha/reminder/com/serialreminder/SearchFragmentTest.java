package alpha.reminder.com.serialreminder;

import android.os.AsyncTask;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import alpha.reminder.com.serialreminder.Fragments.SearchFragment;

import static org.junit.Assert.assertNotNull;
import static org.robolectric.util.FragmentTestUtil.startFragment;

/**
 * Created by Eugeniy Krukun on 23.06.2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)

public class SearchFragmentTest {

    private SearchFragment fragment;

    @Before
    public void setUp() throws Exception {
        fragment = new SearchFragment();
        startFragment(fragment);
    }

    @Test
    public void testNotNullFragment() throws Exception {
        assertNotNull(fragment);
    }

    @Test
    public void testSearch() {
        MainActivity activity = new MainActivity();
        SearchFragment.SearchAsynkTask asynkTask = fragment.new SearchAsynkTask("Flash", activity.getResources());

        assertNotNull(asynkTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR));

    }

    @Test
    public void menuCreateAndSelectTest() {
        MenuItem menuItem = new RoboMenuItem(R.id.search);
        assertNotNull(menuItem);
    }
}
