package com.graungaard.pvc_app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class SuccessActivity extends AbstractNode {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.success, menu);
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

    public void goToMap(){
        ArrayList<GameNode> nodes = ((DataHolderApplication)getApplication()).getAllNodes();

        for (GameNode node : nodes){

            node.setVisible(false);
            if(node.getName().equals("Italienske pizzeria") || node.getName().equals("Dons Mission") || node.getName().equals("Brorens anden mission") || node.getName().equals("JustAnotherMission")){


                node.setVisible(true);

            }


        }
        finish();
    }

}
