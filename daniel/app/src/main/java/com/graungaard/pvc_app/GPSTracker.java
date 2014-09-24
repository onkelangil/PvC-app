package com.graungaard.pvc_app;


import android.location.LocationListener;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
/**
 * Created by anders on 23-09-2014.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    boolean isGPSEnabled =false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    //Minimum distancen for at ændre updates i meter. Den er sat til 10 meter.
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    //Minimum tid mellem updates i millisekunder. Den er sat til 1 min.
    private static final long  MIN_TIME_BW_UPDATES = 1000*60*1;
    protected LocationManager locationManager;


    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }


    public Location getLocation(){
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //Får GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //Får netværks status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled && !isNetworkEnabled){
                //ingen netværks provider er aktiveret

            }else{
                this.canGetLocation = true;
                //Først få lokation fra Netværks provideren
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(
                            locationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager !=null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
            //hvis GPS er aktiveret få lat/long ved brug a GPS service
            if (isGPSEnabled){
                if(location == null){
                    locationManager.requestLocationUpdates(
                            locationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    //Function to get latitude

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }


    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Settings to dialog title
        alertDialog.setTitle("GPS is setting");

        //Laver beskeden til brugeren

        alertDialog.setMessage("GPS is not Enabled. Do you want to go to Settings menu?");

        //sætter Icon til dialog
        //alertDialog.setIcon(R.drawable.delete);

        //Når der bliver trykket på settings knappen

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
            });

        //Når der bliver trykket på cancel
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });

        //Vis alert boxen

        alertDialog.show();
    }

    //Denne function stopper gps'en hvis den er kaldt
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }




    @Override
        public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}
