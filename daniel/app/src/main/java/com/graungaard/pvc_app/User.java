package com.graungaard.pvc_app;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by daniel on 10/6/14.
 */
public class User {

    private String name;
    private String email;
    private String date;
    private String ip;
    private Integer id;
    private LatLng lasttLocation;


    public User(String name, String email, String date, String ip, Integer id, LatLng lasttLocation) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.ip = ip;
        this.id = id;
        this.lasttLocation = lasttLocation;
    }

    @Override
    public String toString() {
        return "User:" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", ip='" + ip + '\'' +
                ", id=" + id +
                ", lasttLocation=" + lasttLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LatLng getLasttLocation() {
        return lasttLocation;
    }

    public void setLasttLocation(LatLng lasttLocation) {
        this.lasttLocation = lasttLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
