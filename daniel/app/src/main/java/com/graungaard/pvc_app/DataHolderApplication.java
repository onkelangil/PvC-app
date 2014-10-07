package com.graungaard.pvc_app;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by daniel on 10/6/14.
 */
public class DataHolderApplication extends Application {
    private int userID;
    private LatLng currentLocation;
    private ArrayList<User> allUsers;


    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }


    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    protected void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getUserID() {
        return userID;
    }

    protected  void setUserID(int userID) {
        this.userID = userID;
    }






}
