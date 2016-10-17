package viraj.com.bse;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

/**
 * This class acts as a custom data type which helps store a notification group on the server.
 * This includes the groups name, notification key and the registered tokens.
 */
public class notificationGroup {
    String name;
    String key;
    JSONArray members;
    static ArrayList<String> allNames = new ArrayList<String>();

    /**
     * Constructor takes in information about the group to store a new group
     * @param name Name of the group
     * @param key Notification key of the group as received by the server
     * @param tokens A JSONArray of all the tokens already registered with the group
     */
    public notificationGroup(String name, String key, JSONArray tokens){
        members = tokens;
        this.name = name;
        this.key = key;
        allNames.add(name);
    }

    /**
     *
     * @return The name of the group
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     *
     * @return The notification key of the group
     */
    public String getKey(){
        return key;
    }

    /**
     *
     * @return All the tokens registered with the group in a JSON Array
     */
    public JSONArray getTokens(){
        return members;
    }

    /**
     * Static class which searches all groups to check whether a group name already exists.
     * @param name The group name
     * @return True if group name already exists, else returns False.
     */
    public static boolean hasName(String name){
        return allNames.contains(name);
    }

    /**
     * Adds tokens to the list of registered tokens
     * @param tokens Tokens to be added to the list of registered tokens.
     */
    public void add_tokens(JSONArray tokens){
        for (int i = 0; i < tokens.length();i++)
            try {
                members.put(tokens.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
