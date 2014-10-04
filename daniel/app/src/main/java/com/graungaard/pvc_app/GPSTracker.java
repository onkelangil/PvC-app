package com.graungaard.pvc_app;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
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
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    Location location;
    double latitude;
    double longitude;

    //Minimum distancen for at ændre updates i meter. Den er sat til 10 meter.
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    //Minimum tid mellem updates i millisekunder. Den er sat til 1 min.
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;


    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }


    public Location getLocation() {
        try {

            initiateLocation();

            if (!isGPSEnabled && !isNetworkEnabled) {

                Log.d("NO GPS,NO NETWORK", "NO GPS, NO NETWORK");

            } else {

                Criteria criteria = new Criteria();

                String bestprovider = locationManager.getBestProvider(criteria, true);

                Log.w("LOCATIONDEBUGGING: " , bestprovider);


                Location location = locationManager.getLastKnownLocation(bestprovider);

                Log.w("LOCATION HEJ", location + "");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return location;
    }


    private void initiateLocation() {


        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        //Får GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Får netværks status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    //Function to get latitude

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Settings to dialog title
        alertDialog.setTitle("GPS is setting");

        //Laver beskeden til brugeren

        alertDialog.setMessage("GPS is not Enabled. Do you want to go to Settings menu?");

        //sætter Icon til dialog
        //alertDialog.setIcon(R.drawable.delete);

        //Når der bliver trykket på settings knappen

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //Når der bliver trykket på cancel
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //Vis alert boxen

        alertDialog.show();
    }

    //Denne function stopper gps'en hvis den er kaldt
    public void stopUsingGPS() {
        if (locationManager != null) {
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
