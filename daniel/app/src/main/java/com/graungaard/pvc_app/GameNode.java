package com.graungaard.pvc_app;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by søren on 06-10-2014.
*/

public class GameNode {

    private boolean vistited;
    private String name;
    private LatLng location;
    private Class activity;


    public GameNode(String name, LatLng location, Class activity) {
        this.vistited = false;
        this.name = name;
        this.location = location;
        this.activity = activity;
    }

    public Class getActivity() {
        return activity;
    }

    public boolean isVistited() {
        return vistited;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setName(String name) {

        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getName() {

        return name;
    }

    public void setVistited(boolean visited) {

        this.vistited = visited;
    }

    @Override
    public String toString() {
        return "GameNode{" +
                "vistited=" + vistited +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", activity=" + activity +
                '}';
    }
}