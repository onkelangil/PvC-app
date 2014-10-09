package com.graungaard.pvc_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SawActivity extends AbstractNode {
    int jumpTime = 0;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saw);
        progress = new ProgressDialog(this);
        //TODO: INSERT SESSION MANAGER
    }


    public void open(View view){
        progress.setMessage("SAV NU DET LÅRT!!! ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        final int totalProgressTime = 1000;

        super.setWeapon("saw");



        final Thread t = new Thread(){

            @Override
            public void run(){


                while(jumpTime < totalProgressTime){
                    try {
                        sleep(200);
                        jumpTime ++;
                        //updateProgress();
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();

    }

    private void updateProgress(){
    jumpTime = super.getToolHandlerProgress();
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
