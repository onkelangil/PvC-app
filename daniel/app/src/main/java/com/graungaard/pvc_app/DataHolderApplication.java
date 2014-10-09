package com.graungaard.pvc_app;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by daniel on 10/6/14.
 */
public class DataHolderApplication extends Application {
    private int userID;
    private LatLng currentLocation;
    private ArrayList<User> allUsers;
    private User partner;
    private boolean hasPartner = false;
    private ArrayList<GameNode> allNodes;


    public DataHolderApplication() {

        this.allUsers = new ArrayList<User>();
        this.allNodes = createNodes();

    }

    public ArrayList<GameNode> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(ArrayList<GameNode> allNodes) {
        this.allNodes = allNodes;
    }


    public boolean isHasPartner() {
        return hasPartner;
    }

    public void setHasPartner(boolean hasPartner) {
        this.hasPartner = hasPartner;
    }

    public User getPartner() {
        return partner;
    }

    public void setPartner(User partner) {
        this.partner = partner;
    }

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

    protected void setUserID(int userID) {
        this.userID = userID;
    }


    private ArrayList<GameNode> createNodes() {

        Log.e("I GENERATE NOTES", "WUHU OR SOMETHING");
        //Note to self: If this was a commercial app I would properbly store these in some xml somewhere..
        Double lat1 = new Double("00.111");
        Double lon1 = new Double("00.354");

        Double lat2 = new Double("56.354");
        Double lon2 = new Double("09.111");


        GameNode node1 = new GameNode("Mission 1", new LatLng(lat1, lon1), GpsActivity.class);
        GameNode node2 = new GameNode("Choice", new LatLng(lat2, lon2), PoliceActivity.class);

        allNodes = new ArrayList<GameNode>();

        allNodes.add(node1);
        allNodes.add(node2);

        return allNodes;
    }


    /**
     * Takes two latlon objects and a distance then in checks if the objects are within distance of each other.
     */
    public Boolean compareCoordinates(LatLng firstcoordinate, LatLng secondcoodinate, Double distance) throws NullPointerException {

        Double longf = 00.000300;
        Double langf = 00.000300;

        if(firstcoordinate == null || secondcoodinate == null){

            return false;
        }

        if (firstcoordinate.latitude <= secondcoodinate.latitude) {
            longf = secondcoodinate.latitude - firstcoordinate.latitude;
        } else if (firstcoordinate.latitude >= secondcoodinate.latitude) {
            longf = firstcoordinate.latitude - secondcoodinate.latitude;
        }

        if (firstcoordinate.longitude <= secondcoodinate.longitude) {
            langf = secondcoodinate.latitude - firstcoordinate.latitude;
        } else if (firstcoordinate.longitude >= secondcoodinate.longitude) {
            langf = firstcoordinate.latitude - secondcoodinate.latitude;
        }

        if (longf < distance && langf < distance) {
            return true;
        } else {
            return false;
        }

    }

}
