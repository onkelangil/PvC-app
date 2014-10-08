package com.graungaard.pvc_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class SawActivity extends AbstractNode {

    ProgressBar pb;
    ToolHandler th;
    int startprogress = 0;
    int progressSaw = th.getProgressSaw();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saw);

        //TODO: INSERT SESSION MANAGER
        //th = new ToolHandler();
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        new Thread(new Runnable(){
            @Override
            public void run() {


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saw, menu);
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


}
