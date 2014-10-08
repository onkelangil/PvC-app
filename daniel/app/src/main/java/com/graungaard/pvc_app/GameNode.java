package com.graungaard.pvc_app;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by søren on 06-10-2014.
*/

public class GameNode {

    private boolean vistited;
    private String name;
    private LatLng location;

    public AbstractNode getActivity() {
        return activity;
    }

    public void setActivity(AbstractNode activity) {
        this.activity = activity;
    }

    private AbstractNode activity;


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

    public void setVistited(boolean vistited) {

        this.vistited = vistited;
    }



}