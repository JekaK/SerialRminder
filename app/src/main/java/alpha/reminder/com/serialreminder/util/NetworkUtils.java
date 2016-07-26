package alpha.reminder.com.serialreminder.util;

/**
 * Created by piekie on 26.07.2016.
 */

public class NetworkUtils {

    public static String validateURL(String url) {
        return ((!url.isEmpty()) && !url.startsWith("http://") && !url.startsWith("https://")) ? "http://" + url : url;
    }
}
