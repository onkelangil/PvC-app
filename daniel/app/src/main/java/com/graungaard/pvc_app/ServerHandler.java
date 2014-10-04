package com.graungaard.pvc_app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 10/3/14.
 */
public class ServerHandler extends IntentService {

    private final String serverName;


    public ServerHandler() {

        //Øøøøøøhhh superkald?!
        super("");

        this.serverName = "http://after-dark.dk/pvc";

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        // Gets data from called intent
        String dataString = workIntent.getDataString();


        //Splits the given input into manageble chunks
        String[] data = dataString.split(",");

        //Writes data to log
        int i = 1;
        for (String s : data) {

            Log.d("DATABID NR" + i, s);
            i++;

        }


        if (data.length >= 3) {

            HTTPPost(data[0], data[1], data[2]);

        }

    }

    private void HTTPPost(String method, String destination, String username) {


        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(serverName + destination);

        Log.w("SERVER: ", "POSTER " + username + " TIL " + serverName + destination);


        try {

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("name", username));
            pairs.add(new BasicNameValuePair("email", "ØØØØHHH EMAIL?!"));
            pairs.add(new BasicNameValuePair("_METHOD", method));

            httppost.setEntity(new UrlEncodedFormEntity(pairs));


            //POSTS DATA
            HttpResponse response = httpclient.execute(httppost);
            Log.w("SERVERHANDLER: " , "DATA POSTET!!!!");


            //TODO Hent BRUGER ID fra server: hint hint: Async Task


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

