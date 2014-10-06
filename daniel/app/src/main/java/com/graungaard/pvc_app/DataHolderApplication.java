package com.graungaard.pvc_app;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by daniel on 10/6/14.
 */
public class DataHolderApplication extends Application {
    private int userID;

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    protected void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    private LatLng currentLocation;


    public int getUserID() {
        return userID;
    }

    protected  void setUserID(int userID) {
        this.userID = userID;
    }






}
