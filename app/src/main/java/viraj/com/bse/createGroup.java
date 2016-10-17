package viraj.com.bse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 *This class is a Async Task and enables us to create new notification groups with the Google
 * server. It is very useful as instead of constantly passing thousands of tokens with each and
 * every HTTP Post request to send a mass push notification, the server can just send the group
 * notification key (which is provided by the Google server when the group is created) in the to
 * field of the payload and the notification is automatically sent to all the devices registered
 * in the group.
 */
public class createGroup  extends AsyncTask<String[],Void,JSONObject>{
    JSONObject mainJson = new JSONObject();
    String response;
    String TESTING = App_Constants.TESTING;
    String notif_key_return;
    boolean success;
    String name;
    JSONObject send_info = new JSONObject();
    ProgressDialog progressDialog;
    Context context;
    public boolean groupCreated;



    /**
     * Contains the algorithm to be executed asynchronously
     * @param params It is an array of String Array.
     *               The first item in the array is (note) an array containing a single element
     *               which is the name of the new group. The second element is the array of
     *               registration_ids (tokens) to be added to this new group.
     *
     * @return Notification key, if the group was successfully mane, or the error message received
     *         by the server if the group was not created. This is passed on the the onPostExecute
     *         method.
     */
    @Override
    public  JSONObject doInBackground(String[]... params){
        JSONObject return_JSON = new JSONObject();
        Log.d(TESTING,"On create group called");
        try {
            name =  params[0][0];
            //Creates the payload JSON which will be sent to create the group
            mainJson.put("operation", "create");
            mainJson.put("notification_key_name", name);
            mainJson.put("registration_ids",new JSONArray(params[1]));
            mainJson.put("project_id",App_Constants.PROJECT_ID);

            return_JSON.put("registration_ids",new JSONArray(params[1]));

            //Initiates the connection with the Google server
            URL url  = new URL("https://android.googleapis.com/gcm/notification");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(TESTING,"Connectino initiated");
            //Sets the headers of the HTTP Post
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Authorization",App_Constants.API_KEY);
            conn.setRequestProperty("project_id",App_Constants.PROJECT_ID);


            //Send the payload to the Google server
            conn.getOutputStream().write(mainJson.toString().getBytes("UTF-8"));
            InputStream is;

            // Extract the input stream if the group creation was successful, else extract the error
            // message
            if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST){
                is = conn.getErrorStream();
            }
            else {
                 is = conn.getInputStream();
            }
            response = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
            is.close();
            Log.d(TESTING,"response: "+ response);

            //convert the response into a JSON object
            JSONObject response_JSON = new JSONObject(response);

            // Return the response key if available, otherwise return the error code
            if (response_JSON.has("notification_key")){
                Log.d(TESTING,"{name: " + response_JSON.getString("notification_key"));
                return_JSON.put("result",response_JSON.getString("notification_key"));
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
     * This method stores the group information in the App_Constants as an established group if the
     * group was created successfully.
     * @param result it is a JSONObject containing 2 fields. It stores a String result, and a
     *               JSONArray of all the tokens registered to this new group. If the group is
     *               created successfully, result field will contain the group's notification_key,
     *               else it will contain "error".
     */
    @Override
    public void onPostExecute(JSONObject result){
        try {
            if (result != null) {
                notif_key_return = result.getString("result");

                if (notif_key_return.equals("error")) {
                    success = false;
                } else {
                    success = true;
                }
                Log.d(TESTING,"success = " + success);

                if (success) {
                    // Store the group information
                    App_Constants.new_group(name, notif_key_return, result.getJSONArray("regitration_ids"));
                    groupCreated=true;
                    Toast.makeText(context, "Group created successfully", Toast.LENGTH_SHORT).show();
                }
            }
            else Log.d(TESTING, "Post exec failed");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
