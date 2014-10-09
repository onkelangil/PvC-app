package com.graungaard.pvc_app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 10/3/14.
 */
public class ServerHandler extends IntentService {

    private final String serverName;
    private ResultReceiver mainActivityReciever;
    private String action;

    public ServerHandler() {

        //Øøøøøøhhh superkald?!
        super("ServerHandler");

        this.serverName = "http://after-dark.dk/pvc";

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        // Gets data from called intent
        String dataString = workIntent.getDataString();
        mainActivityReciever = workIntent.getParcelableExtra("mainReciever");

        //Splits the given input into manageble chunks
        String[] data = dataString.split(";");

        //Writes data to log
        int i = 1;
        for (String s : data) {

            Log.d("DATABID NR" + i, s);
            i++;

        }

        action = workIntent.getAction();

        if (action == "addUser" && data.length <= 1){

            addUser(data[0]);

        } else if (action == "addLocation" && data.length == 2) {

            addLocation(data[0], data[1]);

        } else if(action == "getUsers") {

            getUsers();

        } else {

            Log.d("ServerHandler siger: " , "NO ACTION FOUND");

        }


    }

    private void addLocation(String latitude, String longitude){

        int userid = ((DataHolderApplication)getApplication()).getUserID();

        if(userid == 0){
            return;
        }

        //Makes namevaluepairs for posting to server
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("lastlocation", latitude + "@" + longitude));
        pairs.add(new BasicNameValuePair("_METHOD", "PUT"));


        Log.d("Poster lokationen til server: ", latitude + "@" + longitude);

        String res = HTTPPost(pairs, "/users/location/" + userid);

        Log.d("UserId er pt: " , userid + "");
        Log.w("ServerHandler respons: " , res);

        String result = getJSONStringField(res, "status");


        boolean booleanresult = Boolean.parseBoolean(result);

        Bundle bundle = new Bundle();
        bundle.putString("RESPONSE_TYPE", action);
        bundle.putBoolean("LOCATION_RESULT", booleanresult);

        mainActivityReciever.send(1, bundle);

    }

    private void addUser(String username){



        //Makes namevaluepairs for posting to server
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("name", username));
        pairs.add(new BasicNameValuePair("email", "user@user.dk"));
        pairs.add(new BasicNameValuePair("_METHOD", "POST"));

        String res = HTTPPost(pairs, "/users");

        //extract ID from JSON object
        String id = getJSONStringField(res, "id");

        if(id.equals("")) {
            connectFail("NO RESPONSE FROM SERVER CHECK YOUR INTERNET CONNECTION.");
            return;
        }

        Log.w("Bruger tildelt ID: " ,  id);


        Integer userid = Integer.parseInt(id);



        Bundle bundle = new Bundle();
        bundle.putString("RESPONSE_TYPE", action);
        bundle.putInt("USER_ID", userid);

        mainActivityReciever.send(1, bundle);

    }


    /**
     * Posts stuff to the server returns a string with the servers repons as a JSON object converted to a String
     * @param destination The servers adress
     * @param data The data for the server
     * @return The id of the user (if a user was added) if something went wrong an empty string  is returned
     */
    private String HTTPPost(List<NameValuePair> data, String destination) {


        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(serverName + destination);

        Log.w("SERVER: ", "POSTER " + data + " TIL " + serverName + destination);


        try {

            httppost.setEntity(new UrlEncodedFormEntity(data));


            //POSTS DATA

            HttpResponse response = httpclient.execute(httppost);

            Log.w("SERVERHANDLER: " , "DATA POSTET!!!!");

            //Get response from server and convert JSOn to string
            String stringresponse = EntityUtils.toString(response.getEntity());


            return stringresponse;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

            Log.e("ServerHandler siger: " , "Something broke");

            return "";

    }


    private String HTTPGet(String destination){


        String res = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(serverName + destination));

            Log.w("SERVER: ", "Getter"  + " FRA " + serverName + destination);

            res = EntityUtils.toString(response.getEntity());


            Log.e("RESULT ER: " , res);

        } catch (Exception e) {
            Log.e("[GET REQUEST]", "Network exception");
        }

        return res;


    }

    private void getUsers() {

        String users = HTTPGet("/users");
        //String users = testmethod();

        ArrayList<User> userlist = new ArrayList<User>();

        try {

            JSONObject reader = new JSONObject(users);
            JSONArray userarray = (JSONArray) reader.get("users");

            int len = userarray.length();
            for(int i = 0; i < len; i++) {

                JSONObject tempobject = (JSONObject) userarray.get(i);

                User user = converJSONUserToUser(tempobject);

                userlist.add(user);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            connectFail("The server did nor return the right result. Check your internet connection...");
            return;
        }

        ((DataHolderApplication)getApplication()).setAllUsers(userlist);


        Bundle bundle = new Bundle();
        bundle.putString("RESPONSE_TYPE", action);

        mainActivityReciever.send(1, bundle);

    }

    private User converJSONUserToUser(JSONObject source) throws JSONException {


        String name = source.getString("name");
        String email = source.getString("email");
        String date = source.getString("date");
        String ip = source.getString("ip");
        Integer id = Integer.parseInt(source.getString("id"));
        String lastlocation = source.getString("lastlocation");

        Log.w("LOKATIONSTRENG ER: " , lastlocation);


        LatLng latlnglocation = convertStringToLatLng(lastlocation);

        return new User(name, email, date, ip, id, latlnglocation);



    }


    private LatLng convertStringToLatLng(String source){

        String[] data = source.split("@");


       // Log.e("DATA ARRAY: " , data.toString());
       // Log.e("DATA SOURCE: " , source);

        if(data.length == 2 ){

            return new LatLng(Double.parseDouble(data[0]) , Double.parseDouble(data[1]));


        } else {

            Log.w("ServerHandler siger" , "Location strengen ser ikke ud til at være en lokation");
            return null;


        }
    }



    private String testmethod(){

        return "{\"users\": [{\"name\":\"Big King XXL\",\"email\":null,\"date\":\"2014-10-06 17:06:38\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"BigMac\",\"email\":null,\"date\":\"2014-10-06 16:54:15\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"bjr\",\"email\":null,\"date\":\"2014-10-06 12:47:15\",\"ip\":\"80.62.116.208\",\"lastlocation\":\"lastlocation\"},{\"name\":\"Burger\",\"email\":null,\"date\":\"2014-10-06 16:50:30\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"chbnj\",\"email\":null,\"date\":\"2014-10-04 16:21:12\",\"ip\":\"94.191.185.74\",\"lastlocation\":\"lastlocation\"},{\"name\":\"CheeseWobber\",\"email\":null,\"date\":\"2014-10-06 17:01:42\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"ChickenSalsa\",\"email\":null,\"date\":\"2014-10-06 17:52:14\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"ChickenSalsa\",\"email\":null,\"date\":\"2014-10-06 17:12:01\",\"ip\":\"192.38.33.1\",\"lastlocation\":\"lastlocation\"},{\"name\":\"dwd\",\"email\":\"ddd\",\"date\":\"2014-10-03 10:59:37\",\"ip\":\"192.38.33.16\",\"lastlocation\":\"lastlocation\"}]}";

    }


    /**
     * Called if the connection failes
     */
    public void connectFail(String reason) {

        Bundle bundle = new Bundle();
        bundle.putString("ERROR_REASON", reason);

        mainActivityReciever.send(0, bundle);

    }

    /**
     * Method for extracting data from stringifyed JSON objects
     * @param jsoninput The JSON input (as string)
     * @param fieldforexport The filed from the JSON object that shoudl be returned
     * @return The objecta sked as string. If something breaks a empty string is rturned
     */
    private String getJSONStringField(String jsoninput, String fieldforexport) {

        JSONObject reader = null;
        JSONObject objectforexport = null;
        String res = "";

        try {

            reader = new JSONObject(jsoninput);
            res = reader.getString(fieldforexport);
            // /objectforexport = reader.getJSONObject(fieldforexport);
            //res =  objectforexport.getString(fieldforexport);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }


}

