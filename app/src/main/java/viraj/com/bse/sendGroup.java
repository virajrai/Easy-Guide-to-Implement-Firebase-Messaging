package viraj.com.bse;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class sendGroup  extends AsyncTask<JSONObject,Void,Void>{
    JSONObject data = new JSONObject();
    JSONObject notification = new JSONObject();
    JSONObject mainJson = new JSONObject();
    String response;
    String TESTING = "Testing";


    @Override
    public  Void doInBackground(JSONObject... params){
        try {
            Log.d(TESTING,"sendGroup initiated");
            mainJson = params[0];

            URL url  = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Authorization","key=AIzaSyBLFO8Bl0KxdWcraIdA1rEKPLCdc6KpmR0");
            Log.d(TESTING,"Property set");

            conn.getOutputStream().write(mainJson.toString().getBytes("UTF-8"));

            //conn.connect();
            response = conn.getResponseMessage();
            Log.d(TESTING,String.valueOf(conn.getResponseCode()));
            Log.d(TESTING,"response: "+ response);


        } catch (MalformedURLException e) {
            Log.d(TESTING,"Malformed URL");
        } catch (IOException e) {
            Log.d(TESTING,"IO exception");
        }
        catch (Exception e){
            Log.d(TESTING,e.getClass().toString());
        }

        return null;

    }

}
