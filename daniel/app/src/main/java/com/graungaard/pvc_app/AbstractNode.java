package com.graungaard.pvc_app;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by søren on 08-10-2014.
 */
public class AbstractNode extends Activity {

    private ToolHandler toolHandler;
    private String weapon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SensorManager sensormanager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        toolHandler = new ToolHandler(sensormanager);
    }

    public void setWeapon (String Weapon){
        toolHandler.setWeapon(Weapon);
        Log.e("Lortet virker," + Weapon);
    }


    public int getToolHandler() {
        return toolHandler.getProgress();
    }


}
