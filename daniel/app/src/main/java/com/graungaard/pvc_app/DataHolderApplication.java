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
        Double lat1 = new Double("56.172107");
        Double lon1 = new Double("10.187414");

        //Double lat1 = new Double("56.1719212");
        //Double lon1 = new Double("10.1884509");

        Double lat2 = new Double("56.171236");
        Double lon2 = new Double("10.189841");

        Double lat3 = new Double("56.170567");
        Double lon3 = new Double("10.189905");

        Double lat4 = new Double("56.171274");
        Double lon4 = new Double("10.191246");

        Double lat5 = new Double("56.170809");
        Double lon5 = new Double("10.197072");

        Double lat6 = new Double("56.167030");
        Double lon6 = new Double("10.204656");

        Double lat7 = new Double("56.167030");
        Double lon7 = new Double("10.204656");


        GameNode node1 = new GameNode("Start", new LatLng(lat1, lon1), StartNodeActivity.class);
        GameNode node2 = new GameNode("Mafiosoens Bror", new LatLng(lat2, lon2), GpsActivity.class);
        GameNode node3 = new GameNode("Italienske pizzeria", new LatLng(lat3, lon3), GpsActivity.class);
        GameNode node4 = new GameNode("Dons Mission", new LatLng(lat4, lon4), GpsActivity.class);
        GameNode node5 = new GameNode("Brorens anden mission", new LatLng(lat5, lon5), GpsActivity.class);
        GameNode node6 = new GameNode("JustAnotherMission", new LatLng(lat6, lon6), PoliceActivity.class);
        GameNode node7 = new GameNode("PoliceMission", new LatLng(lat7, lon7), WeaponActivity.class);
        GameNode node8 = new GameNode("MafiaMission", new LatLng(lat7, lon7), CameraActivity.class);

        allNodes = new ArrayList<GameNode>();

        allNodes.add(node1);
        allNodes.add(node2);
        allNodes.add(node3);
        allNodes.add(node4);
        allNodes.add(node5);
        allNodes.add(node6);

        return allNodes;
    }


    /**
     * Takes two latlon objects and a distance then in checks if the objects are within distance of each other.
     */
    public Boolean compareCoordinates(LatLng firstcoordinate, LatLng secondcoodinate, Double distance) throws NullPointerException {

        Double longf = 00.030000;
        Double langf = 00.030000;

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
