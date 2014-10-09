package com.graungaard.pvc_app;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by anders on 06-10-2014.
 */


public class ToolHandler implements SensorEventListener {

    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private float deltaX;
    private float deltaY;
    private float deltaZ;
    private int progress =0;
    private String weapon = "";


    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }


    public ToolHandler(SensorManager sensormanager){

        mInitialized = false;

       this.mSensorManager = sensormanager;

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        Log.w("Toolhandler is running","Toolhandler is running");

    }

    public void pause(){
        mSensorManager.unregisterListener(this);
    }

    public void stop(){

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void changeX(SensorEvent event) {

        long curTimeX = System.currentTimeMillis();

        float x = event.values[0];

        float mLastX = 0;


        long lastUpdateX = 0;

        //Her checker den x og updatere
        if ((curTimeX - lastUpdateX) > 300) {
            lastUpdateX = curTimeX;
            float movementX = Math.abs(mLastX - x);
            //MovementX kan godt bliver sat op. 1.0 er ikke vildt meget
            if (movementX > 3.0) {
                progress++;
            }
            if (progress == 1000) {

                Log.i("sawSucces", "Succesfull");
            }
            mLastX = x;

            if (!mInitialized) {

                mLastX = x;

                mInitialized = true;


            } else {


                this.deltaX = Math.abs(mLastX - x);


                if (deltaX < NOISE) deltaX = (float) 0.0;


            }


        }

    }

    private void changeY(SensorEvent event) {

        long curTimeY = System.currentTimeMillis();

        float y = event.values[1];

        float mLastY = 0;

        long lastUpdateY = 0;

        //Her checker den y og updatere
        if ((curTimeY - lastUpdateY) > 300) {
            lastUpdateY = curTimeY;
            float movementY = Math.abs(mLastY - y);
            //MovementX kan godt bliver sat op. 1.0 er ikke vildt meget
            if (movementY > 3.0) {
                progress++;
            }
            if (progress == 1000) {

                Log.i("AxesSucces", "Succesfull");
            }
            mLastY = y;

            if (!mInitialized) {

                mLastY = y;

                mInitialized = true;


            } else {


                this.deltaY = Math.abs(mLastY - y);


                if (deltaY < NOISE) deltaY = (float) 0.0;


            }


        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

            if(weapon.equals("ax")){

                changeY(event);

            } else if (weapon.equals("saw")) {

                changeX(event);

            }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public  int getProgress(){
        Log.e("HELIJAL", "Progress" + progress);
        return progress;}
}
