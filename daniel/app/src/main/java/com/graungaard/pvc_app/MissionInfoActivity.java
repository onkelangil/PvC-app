package com.graungaard.pvc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MissionInfoActivity extends AbstractNode {

    private String callerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callerID = getIntent().getStringExtra("CALLER_STRING");
        setContentView(R.layout.activity_mission_info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mission_info, menu);
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

    public void buttonOnClick(View v) {
        Button button = (Button) v;


            ArrayList<GameNode> nodes = ((DataHolderApplication)getApplication()).getAllNodes();

            for (GameNode node : nodes){

                if(node.getName().equals("Mafiosoens Bror"));

                node.setVisible(true);

                startActivity(new Intent(this,MapsActivity.class));
            }


    }
}
