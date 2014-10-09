package com.graungaard.pvc_app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by daniel on 10/8/14.
 */
public class GameController extends IntentService {

    private ScheduledExecutorService scheduler;
    private ArrayList<GameNode> allNodes;
    private ResultReceiver mapsActivityReciever;

    private DataHolderApplication dataHolderApplication;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public GameController() {
        super("Game_Controller");
        Log.w("Game conreoller started" , "Processing nodes");

        scheduler = Executors.newSingleThreadScheduledExecutor();



        //startScheduler();



    }


    /*
        private void startScheduler() {

            scheduler.scheduleAtFixedRate
                    (new Runnable() {

                        @Override
                        public void run() {

                            checkConditions();

                        }
                    }, 10, 5, TimeUnit.SECONDS);

        }
    */

    private void checkConditions() {

        ArrayList<GameNode> nodes = ((DataHolderApplication)getApplication()).getAllNodes();
        //ArrayList<GameNode> nodes = allNodes;

        Log.d("I RRUUUUN: " , "AND I CHEEECK");
        for (GameNode node : nodes ){

            //Log.d("GameNode: ", node.toString());

            checkAndStart(node);
        }


    }

    private void checkAndStart(GameNode node) {


        LatLng nodelocation = node.getLocation();
        LatLng currentlocation = ((DataHolderApplication)getApplication()).getCurrentLocation();

        if (currentlocation == null || nodelocation == null) return;

        //TODO: Check constant
        if(((DataHolderApplication)getApplication()).compareCoordinates(currentlocation, nodelocation , new Double("0.000100"))
                && !node.isVistited() && node.isVisible()){

            Intent intent = new Intent(this, node.getActivity());

            Bundle bundle = new Bundle();
            bundle.putParcelable("START_INTENT", intent);
            bundle.putString("NODE_NAME", node.getName());

            Log.e("NODE STATUS (first): " , !node.isVistited() + "");
            node.setVistited(true);
            Log.e("NODE STATUS: " , !node.isVistited() + "");
            mapsActivityReciever.send(1,bundle);

        }



    }


    @Override
    protected void onHandleIntent(Intent intent) {

        mapsActivityReciever = intent.getParcelableExtra("mainReciever");

        checkConditions();


    }


}
