package com.graungaard.pvc_app;

import android.os.Handler;
import android.os.Message;

/**
 * Created by daniel on 10/3/14.
 */
public class UserLocationHandler {
    private int sleepTime;
    private Object parent;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


        }

    };


    public UserLocationHandler() {

        this(3000, null);

    }


    public UserLocationHandler(int sleeptime, Object parent) {

        this.sleepTime = sleeptime;
        this.parent = parent;

    }

    public void start() {


        Runnable runner = new Runnable() {

            @Override
            public void run() {


                //LocationClient mLocationClient = new LocationClient(this, this, this);
                //mLocationClient.connect();




            }
        };

        Thread thread = new Thread(runner);

        thread.start();
    }





}


