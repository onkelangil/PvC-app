package com.graungaard.pvc_app;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daniel on 10/6/14.
 */
public class ToolHandler implements SensorEventListener {


    public ToolHandler(SensorManager sensormanager){

        Sensor accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if(event.values[0]!=0){
//            try {
//               Thread.sleep(16);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ArrayList<Float> xAxixCounter = new ArrayList<Float>();
            xAxixCounter.add(event.values[0]);
            Log.w("ACCELOUTPUT: " , xAxixCounter + "");

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
