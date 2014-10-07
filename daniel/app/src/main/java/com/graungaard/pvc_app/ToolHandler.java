
package com.graungaard.pvc_app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by anders on 06-10-2014.
 */


public class ToolHandler implements SensorEventListener {

    Context AppContext;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;

    public ToolHandler(Context context){

        AppContext = context;

        SensorManager  mSensorManager =  (SensorManager) AppContext.getSystemService(Context.SENSOR_SERVICE);

        Log.w("CONSTRUCTOR", "Is running");

    }
    /**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        mInitialized = false;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    protected void onPause() {

        super.onPause();

        mSensorManager.unregisterListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
     **/

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];

        float y = event.values[1];

        float z = event.values[2];


        float mLastX=0;
        float mLastY=0;
        float mLastZ =0;
        if (!mInitialized) {


            mLastX =x;

            mLastY = y;

            mLastZ = z;
            mInitialized = true;

        }else {

            float deltaX = Math.abs(mLastX - x);

            float deltaY = Math.abs(mLastY - y);

            float deltaZ = Math.abs(mLastZ - z);

            if (deltaX < NOISE) deltaX = (float) 0.0;

            if (deltaY < NOISE) deltaY = (float) 0.0;

            if (deltaZ < NOISE) deltaZ = (float) 0.0;

            Log.w("deltax", deltaX + "");
            Log.w("deltay", deltaY + "");
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
