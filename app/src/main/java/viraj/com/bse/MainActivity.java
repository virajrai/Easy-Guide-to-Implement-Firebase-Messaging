package viraj.com.bse;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.Permission;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //static ArrayList<String> tokens = new ArrayList<String>();
    //static JSONObject notification_key_JSON = new JSONObject();
    createGroup sender;
    sendHTTPPost send_POST;
    String TESTING = App_Constants.TESTING;


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults){
        switch (requestCode){
            case 120:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        JSONArray group_tokens = new JSONArray();
        group_tokens.put("cDLYZymtMNY:APA91bGH0jo8DCcaCtjYFT2eJq4x4kD1Qqp-68NL0-lSB87Cky-XefnSJWXGzt1Grz9ZR0GtKVRUYXFD9HIDLYUt9yJJINlux7zPnApGEkHrywopgyfQlIDrKJ_z1O8qBa6YDGTRZTvs");
        group_tokens.put("cRnMa-aFztE:APA91bFwIixFYUVpsfd6D92zLyL3junNu7YIVKtEvBJiX5CpK29_8pKYKbHSMvYYek58Z2RXO51O1_zPXk52KRbdlwYpvHQUXxeP6Qv2cCEp54BHQqt4DX5egZNsqCJP7C4APTSjjzjs");

        App_Constants.new_group("temp","APA91bHKE2qnLf_cW57qHs2k64dHtuNgPlip3b1OAhmFuIzsiBAabvETL1BN0NmILx6ILFQetUw632DFHGe25HlYJmzxoIWhUoW8SB4k6AlR-espzQ3HxV0",group_tokens);
        App_Constants.new_group("temp2","APA91bHSzVujxESEdAPdD6ZsH42GEGMTbPO6wQq4g_AeixYnhC_lvyT2IoeLufdVDytOk4myKyhFGnhC-H4CTkz2yZYeDXGpo8iqUp1Na-Sgc9r_VIqPwCY",group_tokens);
        App_Constants.new_group("new","APA91bEnmOgnW4d3BEaMiyxlgfCYB_QxBz2M3bNblA7OLs_Ic7-xngG5wmPOFv1y3jOxFcgzixh3YG9AAcv3hCPvWXGEPQLqgflXWt3x8sfJ7CdC_gqrWII",group_tokens);
        App_Constants.new_group("test1","APA91bEEma6zmwN5VcTR0zLmxQUcwPLT8S0Bz1XOKdPuMGO-cJMVgAhlAbpuoXaz6VXq3S2AYJ7fPoXUmuihEBdd5zoGhMosLaWXemNzGr037gwspxArRU0",group_tokens);
        App_Constants.new_group("ha","APA91bFP-V2I4XFwyUqihNNWodFtd7wSXVMuzVHKMuklSLckQWjz1MET39A3l-VzNXghIiSTKKAB8_F8FVyom0SLnC9hZos5b3SLrmJ-EKvQxOGlRKKldLQ",group_tokens);
        App_Constants.new_group("haha","APA91bGnmpPjmw0sWlXz3YNaRdIWlC5Vn23uSuH_C3EQjMsX9NePiwdd0N3CygYQkwdMSarm754vk40VO-Yy9EeLtF7HgEfnkAEMpHwHovTZ6aUEfpE8vB0", group_tokens);

        App_Constants.add_token("cDLYZymtMNY:APA91bGH0jo8DCcaCtjYFT2eJq4x4kD1Qqp-68NL0-lSB87Cky-XefnSJWXGzt1Grz9ZR0GtKVRUYXFD9HIDLYUt9yJJINlux7zPnApGEkHrywopgyfQlIDrKJ_z1O8qBa6YDGTRZTvs");
        App_Constants.add_token("cRnMa-aFztE:APA91bFwIixFYUVpsfd6D92zLyL3junNu7YIVKtEvBJiX5CpK29_8pKYKbHSMvYYek58Z2RXO51O1_zPXk52KRbdlwYpvHQUXxeP6Qv2cCEp54BHQqt4DX5egZNsqCJP7C4APTSjjzjs");



        try {
            Button button = (Button) findViewById(R.id.token);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(FirebaseInstanceId.getInstance().getToken());
                    Log.d(TESTING,FirebaseInstanceId.getInstance().getToken());
                    App_Constants.add_token(FirebaseInstanceId.getInstance().getToken());
                }
            });

            Button send = (Button) findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try {
                        JSONObject data = new JSONObject();
                        JSONObject notification = new JSONObject();
                        JSONObject send_JSON = new JSONObject();
                        data.put("message", "This is the test data message");
                        notification.put("title", "Test Title");
                        notification.put("body", "Test Notification Body");
                        send_JSON.put("data", data);
                        send_JSON.put("notification", notification);
                        send_JSON.put("registration_ids", new JSONArray(App_Constants.all_tokens));

                        send_POST = new sendHTTPPost();
                        send_POST.context = MainActivity.this;
                        send_POST.execute(send_JSON);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            Button create_group = (Button) findViewById(R.id.createGroupButton);
            create_group.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try{
                        EditText editText = (EditText) findViewById(R.id.createGroupName);
                        String name = editText.getText().toString();
                        if (!name.equals("") && !notificationGroup.hasName(name)) {
                            JSONObject data = new JSONObject();
                            JSONObject notification = new JSONObject();
                            JSONObject send_JSON = new JSONObject();
                            data.put("message", "This is the test data message");
                            notification.put("title", "Test Title");
                            notification.put("body", "Test Notification Body");
                            send_JSON.put("data", data);
                            send_JSON.put("notification", notification);

                            String[] name_array = {name};
                            String[] send_JSON_array = {send_JSON.toString()};

                            //If there are any tokens to
                            if (App_Constants.all_tokens.size() > 0) {
                                String[] tokens_array = new String[App_Constants.all_tokens.size()];
                                for (int i = 0; i < App_Constants.all_tokens.size(); i++) {
                                    tokens_array[i] = App_Constants.all_tokens.get(i);
                                }
                                sender = new createGroup();
                                sender.context = MainActivity.this;
                                sender.execute(name_array,tokens_array);



                            }
                        }
                        else{
                            if (name.equals("")) Toast.makeText(getApplicationContext(),"Enter name",Toast.LENGTH_SHORT).show();
                            else if (notificationGroup.hasName(name)) Toast.makeText(getApplicationContext(),"Group already Exists",Toast.LENGTH_SHORT).show();
                            else Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch ( JSONException e){
                        e.printStackTrace();
                    }
                }

            });

            Button send_group = (Button) findViewById(R.id.sendGroupButton);
            send_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name;
                    try {
                        JSONObject data = new JSONObject();
                        JSONObject notification = new JSONObject();
                        JSONObject send_JSON = new JSONObject();
                        EditText editText = (EditText) findViewById(R.id.sendGroupName);
                        name = editText.getText().toString();

                        if (!App_Constants.getNotificationKey(name).equals("error") || App_Constants.all_tokens.contains(name)) {
                            send_JSON = payloadBuilder();
                            send_JSON.put("to",App_Constants.getNotificationKey(name));

                            send_POST = new sendHTTPPost();
                            send_POST.context = MainActivity.this;
                            send_POST.execute(send_JSON);


                        }
                        else{
                            Toast.makeText(MainActivity.this, "Group does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
        }
        final int REQUEST = 120;
        Button sendIntent = (Button) findViewById(R.id.sendIntentButton);
        sendIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission();
                /*
                Intent intent = new Intent();
                intent.setAction("com.viraj.bse.IntentService");
                sendBroadcast(intent);
                */
            }
        });
    }

    @TargetApi(23)
    public void getPermission(){
        Toast.makeText(MainActivity.this, "getPermission called", Toast.LENGTH_SHORT).show();
        Log.d(TESTING,String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)!= PackageManager.PERMISSION_GRANTED));
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {
                    Log.d(TESTING, "Should request permission");
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},
                        120);
                Log.d(TESTING,"in checkSelfPermission");
            }
        }
        Toast.makeText(MainActivity.this, "get permission done", Toast.LENGTH_SHORT).show();
    }

    /**
     * Helps create the payload which will be sent via HTTP Post. This will contain all the data of
     * the notification such as data message, notification title and body, any click_action along
     * with any custom fields you want to send with the notification. This method does not add the
     * to: field to the JSON. This field must be specified at the time of execution of the HTTP
     * Post. This method is only intended to generate data of a push notification and not target
     * them.
     *
     * @return Returns a JSON Object which acts as the payload for the HTTP Post
     */

    public JSONObject payloadBuilder(){
        JSONObject data = new JSONObject();
        JSONObject notification = new JSONObject();
        JSONObject send_JSON = new JSONObject();

        try {
            data.put("message", "This is the test data message");
            notification.put("title", "Test Title");
            notification.put("body", "Test Notification Body");
            send_JSON.put("data", data);
            send_JSON.put("notification", notification);
            return send_JSON;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TESTING,"On resume called");
        if (sender!=null) {
            if (sender.groupCreated ) {
                Toast.makeText(MainActivity.this, "Group Created Successfully!", Toast.LENGTH_SHORT).show();
                sender.groupCreated = false;
            }
        }

        else if (send_POST!=null) {
            if (send_POST.post_sent ) {
                Toast.makeText(MainActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
                send_POST.post_sent = false;
            }
        }

    }

/*
    public void add_token(String token){
        if (!App_Constants.tokens.contains(token)){
            App_Constants.tokens.add(token);
        }
    }

    public static void add_group(String name, String key){
        if (!App_Constants.notification_key_JSON.has(name)) {
            try {
                App_Constants.notification_key_JSON.put(name, key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    */



}
