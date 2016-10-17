package viraj.com.bse;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by raisa on 021 21 07 16.
 */
public class NotificationService extends NotificationListenerService {
    Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public static boolean checkPermission( Context context) {
        return ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE) == PackageManager.PERMISSION_GRANTED;
    }

    public void onNotificationPosted(StatusBarNotification sbn){
        Log.d(App_Constants.TESTING,"New Notification detected");
        String pack = sbn.getPackageName();
        String ticker = sbn.getNotification().tickerText.toString();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        Toast.makeText(NotificationService.this, "New Notification detected", Toast.LENGTH_SHORT).show();


        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",text);

        Intent msgrcv = new Intent("com.viraj.bse.NotificationIntent");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }

    public void onNotificationRemoved(StatusBarNotification sbn){
        Log.i("Msg","Notification removed");
    }
}
