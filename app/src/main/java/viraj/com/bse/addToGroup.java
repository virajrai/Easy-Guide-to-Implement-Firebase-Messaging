package viraj.com.bse;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * This class helps register new token ids to an existing group
 */
public class addToGroup  extends AsyncTask<String[],Void,JSONObject>{
    JSONObject mainJson = new JSONObject();
    String response;
    String TESTING = App_Constants.TESTING;
    String notif_key_return;
    boolean success;
    String name;
    JSONObject return_JSON = new JSONObject();

    /**
     * Takes in multiple arguments of String Arrays
     * @param params First index is group name, second index is an Array of tokens needed to be
     *               registered on the group
     * @return JSON Object with newly registered tokens, and the response received fro the HTTP Post
     */
    @Override
    public  JSONObject doInBackground(String[]... params){
        try {
            //Ensure the group exists
            if (App_Constants.getNotificationKey(name).equals("error")) throw new Exception();
            name = params[0][0];
            mainJson.put("operation", "add");
            mainJson.put("notification_key_name", name);
            mainJson.put("notification_key",App_Constants.getNotificationKey(name));
            mainJson.put("registration_ids",new JSONArray(params[1]));

            return_JSON.put("registration_ids",new JSONArray(params[1]));

            URL url  = new URL("https://android.googleapis.com/gcm/notification");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Authorization",App_Constants.API_KEY);
            conn.setRequestProperty("project_id",App_Constants.PROJECT_ID);


            conn.getOutputStream().write(mainJson.toString().getBytes("UTF-8"));
            InputStream is;

            if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST){
                is = conn.getErrorStream();
            }
            else {
                is = conn.getInputStream();
            }
            response = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
            is.close();

            Log.d(TESTING,"response: "+ response);
            JSONObject response_JSON = new JSONObject(response);

            if (response_JSON.has("notification_key")){
                Log.d(TESTING,"{name: " + response_JSON.getString("notification_key"));
                return_JSON.put("result", response_JSON.getString("notification_key"));
                return return_JSON;
            }
            else {
                Log.d(TESTING, "{name: error");
                return_JSON.put("result","error");
                return return_JSON;
            }
        }
        catch (JSONException e){
            Log.d(TESTING,"JSON Exception");
        }
        catch (MalformedURLException e) {
            Log.d(TESTING,"Malformed URL");
        } catch (IOException e) {
            Log.d(TESTING,"IO exception");
            e.printStackTrace();
        }
        catch (Exception e){
            Log.d(TESTING,e.getClass().toString());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds the newly registered tokens to the respective group if the process was successful
     * @param result A JSON Object containing a JSON Array with of newly added token ids and
     *               a String containing the notification key if the process was successful,
     *               or "error" if there was an error in adding the keys to the group.
     */
    @Override
    public void onPostExecute(JSONObject result){
        try {
            if (result != null) {
                notif_key_return = result.getString("result");

                if (notif_key_return.equals("error"))  success = false;
                else success = true;

                if (success)  App_Constants.addToGroup(name, notif_key_return, result.getJSONArray("registration_ids"));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

}