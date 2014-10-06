package com.graungaard.pvc_app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

    public ServerHandler() {

        //Øøøøøøhhh superkald?!
        super("");

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

        String action = workIntent.getAction();

        if (action == "addUser" && data.length <= 1){

            addUser(data[0]);

        } if(action == "addLocation" && data.length == 2) {


            Log.w("LocationHANDLER SIGER: " , "ADD LOCATION BLIVER KØRT");


            try {
                //AD LOCATION METHOD SHOUDL BE CALLED HERE
                Log.w("RESULT AF USERIDKALD: " , workIntent.getStringExtra("USER_ID"));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ECEPTION AF TYPEN " , e.getClass() + "");
            }

        } else {

            Log.d("ServerHandler siger: " , "NO ACTION FOUND");

        }


    }

    private void addLocation(String latitude, String longitude, String userid){




        //Makes namevaluepairs for posting to server
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("lastlocation", latitude + ";" + longitude));
        pairs.add(new BasicNameValuePair("_METHOD", "PUT"));


        String res = HTTPPost(pairs, "/users/location/" + userid);

        Log.e("ServerHandler respons: " , res);

    }

    private void addUser(String username){



        //Makes namevaluepairs for posting to server
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("name", username));
        pairs.add(new BasicNameValuePair("email", "ØØØØHHH EMAIL?!"));
        pairs.add(new BasicNameValuePair("_METHOD", "POST"));

        String res = HTTPPost(pairs, "/users");

        //extract ID from JSON object
        String id = convertJSONToString(res, "id");

        Log.w("Bruger tildelt ID: " ,  id);


        int userid = Integer.parseInt(id);

        Bundle bundle = new Bundle();
        bundle.putString("RESPONSE_TYPE", "USER_ID");
        mainActivityReciever.send(userid, bundle);

    }

    /**
     * Posts stuff to the server returns a string with the servers repons as a JSON object converted to a String
     * @param destination The servers adress
     * @param data The username of the user
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

    /**
     * Method for extracting data from stringifyed JSON objects
     * @param jsoninput The JSON input (as string)
     * @param fieldforexport The filed from the JSON object that shoudl be returned
     * @return The objecta sked as string. If something breaks a empty string is rturned
     */
    private String convertJSONToString(String jsoninput, String fieldforexport) {

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

