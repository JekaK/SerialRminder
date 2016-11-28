package alpha.reminder.com.serialreminder.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jeka on 28.11.16.
 */

public class CustomReceiver extends BroadcastReceiver {

    public CustomReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);
    }
}
