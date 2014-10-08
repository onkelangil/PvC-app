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
    private int progressSaw =0;
    private int progressØske=0;



    public ToolHandler(SensorManager sensormanager){
        //svarer til oncreate

        mInitialized = false;

       this.mSensorManager = sensormanager;

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        Log.w("Toolhandler is running","Toolhandler is running");

    }

    public void pause(){
        //Svare til pause
        mSensorManager.unregisterListener(this);
    }

    public void stop(){
        //SVarer til overrided Stopmetoden

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long curTimeX = System.currentTimeMillis();
        long curTimeY = System.currentTimeMillis();

        float x = event.values[0];

        float y = event.values[1];

        float z = event.values[2];

        float mLastX=0;
        float mLastY=0;
        float mLastZ =0;

        long lastUpdateX=0;
        long lastUpdateY=0;


        //Her checker den x og updatere
        if((curTimeX - lastUpdateX)>300){
            lastUpdateX = curTimeX;
            float movementX = Math.abs(mLastX - x);
            //MovementX kan godt bliver sat op. 1.0 er ikke vildt meget
            if(movementX > 3.0){
                progressSaw++;
            }
            if (progressSaw ==100){

                Log.i("sawSucces","Succesfull");
            }
            mLastX =x;
        }


        //Her checker den y og updatere
        if((curTimeY - lastUpdateY)>300){
            lastUpdateY = curTimeY;
            float movementY = Math.abs(mLastY - y);
            //MovementX kan godt bliver sat op. 1.0 er ikke vildt meget
            if(movementY > 3.0){
                progressØske++;
            }
            if (progressØske ==100){

                Log.i("AxesSucces","Succesfull");
            }
            mLastY =y;
        }



        if (!mInitialized) {


            mLastX =x;

            mLastY = y;

            mLastZ = z;
            mInitialized = true;

        }else {


            this.deltaX = Math.abs(mLastX - x);

            this.deltaY = Math.abs(mLastY - y);

            this.deltaZ = Math.abs(mLastZ - z);

            if (deltaX < NOISE) deltaX = (float) 0.0;

            if (deltaY < NOISE) deltaY = (float) 0.0;

            if (deltaZ < NOISE) deltaZ = (float) 0.0;

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public int getProgressSaw() {
        return progressSaw;
    }
    public  int getProgressØske(){  return progressØske;}
}
