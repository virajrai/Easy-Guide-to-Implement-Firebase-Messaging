package viraj.com.bse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
/**
 * This class contains all the variables required for sending HTTP Posts
 */

public class App_Constants {
    static String PROJECT_ID = "Your project ID";
    static String API_KEY = "key=Enter API KEY";
    static ArrayList<String> all_tokens = new ArrayList<String>();
    static ArrayList<notificationGroup> groups = new ArrayList<>();
    static String TESTING = "FirebaseMessagingTesting";


    /**
     * Adds the token to the tokens ArrayList if the token is not already present
     * @param token the token to be added to the databse
     */

    public static void add_token(String token){
        if (!all_tokens.contains(token)) all_tokens.add(token);
    }

    /**
     * Adds a new group to the JSON along with its notification_key
     * @param name Name of the group
     * @param notification_key The notification key of the respective group as received from the
     *                         Google server
     */
    public  static void new_group(String name, String notification_key, JSONArray registration_ids){

        if (!notificationGroup.hasName(name)){
            notificationGroup temp_group = new notificationGroup(name,notification_key,registration_ids);
            groups.add(temp_group);
        }
    }

    /**
     * This group adds tokens tokens to an already existing group
     * @param name Name of the group
     * @param notification_key Notification key of the group (used for validation)
     * @param registration_ids JSON Array of registration ids to be added to the group
     */
    public static void addToGroup(String name, String notification_key, JSONArray registration_ids){
        //Check if the group exists
        if (notificationGroup.hasName(name)){

            //Iterate through all groups and check if the group exists and the notification key is
            //valid
            for (notificationGroup i : groups){
                if (i.toString().equals(name) && i.getKey().equals(notification_key)){
                    //add tokens to the particular group
                    i.add_tokens(registration_ids);
                    addToAll(registration_ids);
                }
            }
        }
    }

    /**
     * Add tokens to the Master ArrayList containing all tokens if they are already not there
     * @param tokens A JSONArray of tokens
     */

    public static void addToAll(JSONArray tokens){
        try {
            for (int i = 0; i < tokens.length(); i++) {
                if (!all_tokens.contains(tokens.getString(i))) {
                    all_tokens.add(tokens.getString(i));
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the notification key of a group
     * @param name Name of the group whose notification key is required
     * @return Notification key as a string if a valid group name was passed, else returns "error"
     */
    public static String getNotificationKey(String name){
        if (notificationGroup.hasName(name)){
            for(notificationGroup i : groups){
                if (i.toString().equals(name)){
                    return i.getKey();
                }
            }
        }
        return "error";
    }

}
