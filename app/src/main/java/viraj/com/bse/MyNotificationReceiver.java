package viraj.com.bse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by raisa on 021 21 07 16.
 */
public class MyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        String title = intent.getExtras().getString("Title");
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }
}
