package viraj.com.bse;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Send HTTP post directly with one or many users, using their respective tokens.
 * It is an async task seperate from the main thread.
 */

public class sendHTTPPost  extends AsyncTask<JSONObject,Void,Void>{
    JSONObject mainJson = new JSONObject();
    String response;
    String TESTING = App_Constants.TESTING;
    boolean post_sent;
    Context context;

    /**
     *Contains the algorithm to be executed asynchronously
     * @param params Is an array of JSON Objects. The first item is a JSON object containing data
     *               of the HTTP post such as who it is targetted to, its notification title and
     *               body, and its data message. It also contains other parameters of the payload.
     * @return Void
     */

    @Override
    public  Void doInBackground(JSONObject... params){
        try {
            mainJson = params[0];
            URL url  = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Authorization",App_Constants.API_KEY);
            Log.d(TESTING,"Property set");

            conn.getOutputStream().write(mainJson.toString().getBytes("UTF-8"));

            // Extract the input stream if the group creation was successful, else extract the error
            // message
            InputStream is;
            if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST){
                is = conn.getErrorStream();
            }
            else {
                is = conn.getInputStream();
            }
            is = conn.getInputStream();
            response = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
            is.close();
            Log.d(TESTING,"response: "+ response);
            JSONObject response_JSON = new JSONObject(response);

            if (response_JSON.getInt("success")!=0) post_sent = true;
            else post_sent = false;

        } catch (MalformedURLException e) {
            Log.d(TESTING,"Malformed URL");
        } catch (IOException e){
            Log.d(TESTING,"IO exception");
        }
        catch (Exception e){
            Log.d(TESTING,e.getClass().toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(Void result){
        if (post_sent) {
            Log.d(TESTING,"sendHTTPPost Successful");
            Toast.makeText(context, "Message sent successfully", Toast.LENGTH_SHORT).show();
        }
        else Log.d(TESTING,"Error in executing sendHTTPPost");
    }

}