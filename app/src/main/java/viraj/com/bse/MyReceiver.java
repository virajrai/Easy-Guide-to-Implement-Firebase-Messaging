package viraj.com.bse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by raisa on 021 21 07 16.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context contex, Intent intent){
        Toast.makeText(contex, "Intent received", Toast.LENGTH_SHORT).show();
    }
}
