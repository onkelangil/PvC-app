package com.graungaard.pvc_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class SetupActivity extends Activity {

    private DataHolderApplication dataHolderApplication;
    private Handler findPartnerHandler;
    private Intent serverHandlerMainIntent;
    private ResultReceiver serverReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        dataHolderApplication = ((DataHolderApplication)getApplication());

        findPartnerHandler = new Handler();

        serverHandlerMainIntent = new Intent(this, ServerHandler.class);

        serverReciever = this.getIntent().getParcelableExtra("mainReciever");

        setupAppState();
        Log.d("ONSTART KØRER" , "YUBIIII");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Responsible for setting te app state such as fising a partner before starting the game
     */
    private void setupAppState() {

        Log.d("I RUN", "AN I LOOK FOR PARTNER");


        Toast.makeText(this, "Leder efter Partner -- Stil dig samme sted som en anden mens han/hun logger ind", Toast.LENGTH_LONG).show();

        try {

            startLookingForPartner();



        } catch (NullPointerException e) {

            Toast.makeText(this, "Henter Brugere...", Toast.LENGTH_LONG).show();


        }


        //TODO: EXIT TO MAPS
        //finish();
        //initiateGame();


    }

    /**
     * Runs findpartner every 3 seconds until it has found a partner
     */
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() throws NullPointerException {

            Log.w("I RUNNNENNNENENNENENEN" , "YES IS DUE");

            boolean haspartner = dataHolderApplication.isHasPartner();


            if (!haspartner) {
                findPartnerHandler.postDelayed(mStatusChecker, 5000);

                Log.w("Main Siger: ", "I Look for partner yes");

                findPartner();


            } else {
                String name = ((DataHolderApplication)getApplication()).getPartner().getName();
                Log.wtf("MAIN SIGER: ", "Just found my partner");
                userIsSet(name);
            }

        }
    };



    void startLookingForPartner() throws NullPointerException {
        Log.w("FEDEPIK" , "c");

        mStatusChecker.run();

    }

    void stopLookingForPartner() {
        findPartnerHandler.removeCallbacks(mStatusChecker);
    }

    private void findPartner() throws NullPointerException {

        ArrayList<User> allUsers = ((DataHolderApplication)getApplication()).getAllUsers();

        for (User user : allUsers) {

            boolean b = isUserPartner(user);

        }


    }

    private boolean isUserPartner(User user) throws NullPointerException{

        try {
            // Log.d("USER: ", user.toString());



            LatLng otherlocation = user.getLasttLocation();
            LatLng mylocation = ((DataHolderApplication)getApplication()).getCurrentLocation();

            int myid = ((DataHolderApplication)getApplication()).getUserID();


            if (otherlocation == null){

                Toast.makeText(this, "Leder efter brugere" , Toast.LENGTH_SHORT);
                return false;

            }


            //Log.w("IS; ", mylocation.toString());
            //Log.wtf("EQUAL TO; ", otherlocation.toString());

            Double distance = new Double(0.00175);


            //TODO: CHECK CONSTANT
            if(((DataHolderApplication)getApplication()).getCurrentLocation() == null){

                Log.d("SOMETHINGS NOT RIGHT" , "CURRENT");


            }


            if (dataHolderApplication.compareCoordinates(otherlocation, mylocation, distance) && myid != user.getId()) {

                ((DataHolderApplication) getApplication()).setPartner(user);

                dataHolderApplication.setHasPartner(true);

                return true;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.w("MAIN SIGER: ", "NO LOCATION POR FAVOR");
        }

        return false;

    }




    /**
     * Makes toast that tells user that a partner has been found
     * @param name name of the partner
     */
    private void userIsSet(String name) {
        Toast.makeText(this, "Du er på hold med " + name + " og det er ret awesome", Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<GameNode> nodes = ((DataHolderApplication)getApplication()).getAllNodes();

        for (GameNode node : nodes){

            if(node.getName().equals("Start")){
                node.setVisible(true);
            }
        }

        finish();
    }



}
