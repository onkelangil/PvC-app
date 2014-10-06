package com.graungaard.pvc_app;

import android.app.Application;

/**
 * Created by daniel on 10/6/14.
 */
public class DataHolderApplication extends Application {
    private int userID;

    public int getUserID() {
        return userID;
    }

    protected  void setUserID(int userID) {
        this.userID = userID;
    }




}
