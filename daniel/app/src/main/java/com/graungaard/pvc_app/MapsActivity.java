package com.graungaard.pvc_app;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {


    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 42;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private UserLocationHandler userLocationHandler;
    private LocationClient locationClient;
    private Location currentLocation;

    private Timer userLocationUpdateTimer;

    final Handler mapHandler = new Handler();

    final Runnable runUpdateLocation = new Runnable() {
        @Override
        public void run() {
            updateLocation();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        //Turns on location
        mMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(this, this, this);
        locationClient.connect();


    }

    @Override
    protected void onStart() {
        super.onStart();


        if (currentLocation != null) {

            setLocation();

        } else {

            Toast toast = Toast.makeText(getApplicationContext(), "Venter på placering...", Toast.LENGTH_SHORT);
            toast.show();

        }



        }

        @Override
        protected void onStop () {
            super.onStop();
            locationClient.disconnect();

        }


        @Override
        protected void onResume () {
            super.onResume();
            setUpMapIfNeeded();
        }

        /**
         * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
         * installed) and the map has not already been instantiated.. This will ensure that we only ever
         * call {@link #setUpMap()} once when {@link #mMap} is not null.
         * <p/>
         * If it isn't installed {@link SupportMapFragment} (and
         * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
         * install/update the Google Play services APK on their device.
         * <p/>
         * A user can return to this FragmentActivity after following the prompt and correctly
         * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
         * have been completely destroyed during this process (it is likely that it would only be
         * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
         * method in {@link #onResume()} to guarantee that it will be called.
         */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    /**
     * Called from timer
     * Asks the locationclient top update its location
     */
    public void updateLocation() {

        if (locationClient != null) {


            currentLocation = locationClient.getLastLocation();
            Log.w("CURRENT LOCATION IS: ", "" + currentLocation);
            Log.w("LOCATION OBJEKTET ER EN ", "" + currentLocation.getClass());

        } else {

            Log.w("LOCATION: ", "LOCATIONCLIENT ER NULL");

        }

        setLocation();

    }


    private LatLng convertLocation(Location location) {

        double lat = location.getLatitude();
        double lon = location.getLongitude();

        return new LatLng(lat, lon);

    }


    private void setLocation() {

        //Retrieve username and location and add fancy bubbles
        String username = getUsername();
        LatLng location = convertLocation(currentLocation);
        createBubble(username, location);

    }


    private String getUsername() {
        Intent parentIntent = getIntent();
        String username = parentIntent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        return username;
    }

    private void createBubble(String iconCaption, LatLng location) {
        //Make new factory to genetate bubbles
        IconGenerator factory = new IconGenerator(this);
        Bitmap icon = factory.makeIcon(iconCaption);
        mMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromBitmap(icon)));
    }

    @Override
    public void onConnected(Bundle dataBundle) {

        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

        Log.w("Onconnected siger", "I RAN");
        //STarts timer
        userLocationUpdateTimer = new Timer();
        userLocationUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                mapHandler.post(runUpdateLocation);

            }
        }, 0, 1000);


    }

    /*
         * Called by Location Services if the connection to the
         * location client drops because of an error.
         */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode) {

        Log.w("DER SKET EN FEJL", "!!!!!!!");

        Log.w("A TRO DET VA A FAJL NR:  ", "" + errorCode);

    }



}

/**
 * Class for running update in new sthread
 */
