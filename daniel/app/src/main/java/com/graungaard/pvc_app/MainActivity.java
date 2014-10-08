package com.graungaard.pvc_app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Button btnShowLocation;
    public static DataHolderApplication dataHolder = new DataHolderApplication();
    private Intent serverHandlerMainIntent;
    private ServerReciever serverReciever = new ServerReciever();

    private Handler activityHandler = new Handler();

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    public void startGame(View view) {

        updateUserArrayList();

        startSetupActivity();


        // SensorManager sensormanager = ((SensorManager)getSystemService(SENSOR_SERVICE));

        // ToolHandler toolHandler= new ToolHandler(sensormanager)


    }

    private void startSetupActivity() {

        startMap();
    }


    /**
     * Responsible for starting the game
     * called when user has partner and is ready
     */
    private void initiateGame() {

        Intent intent = new Intent(this, StartNodeActivity.class);
        startActivity(intent);

    }


    private void updateUserArrayList() {

        serverHandlerMainIntent.setAction("getUsers");

        String dataForServerHandler = "Hej Hr serverHandler jeg er en ligegyldig streng..., Helt lieggyldig";
        serverHandlerMainIntent.setData(Uri.parse(dataForServerHandler));

        //Send reciever to ServerHandler
        serverHandlerMainIntent.putExtra("mainReciever", serverReciever);

        this.startService(serverHandlerMainIntent);

    }



    public void startMap() {

        Intent intent = new Intent(this, MapsActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);


        Intent serverHandlerLocationIntent = new Intent(this, ServerHandler.class);
        serverHandlerLocationIntent.putExtra("mainReciever", new ServerReciever());


        intent.putExtra("SERVERHANDLER_INTENT", serverHandlerLocationIntent);

        addUserToServer(username);

        startActivity(intent);


    }

    /**
     * Adds user to server
     *
     * @param username
     */
    private void addUserToServer(String username) {

        //Constructs URI with data for server
        String dataForServerHandler = username;


        serverHandlerMainIntent.setAction("addUser");
        //Parse data to intent
        serverHandlerMainIntent.setData(Uri.parse(dataForServerHandler));
        //Send reciever to ServerHandler
        serverHandlerMainIntent.putExtra("mainReciever", serverReciever);

        this.startService(serverHandlerMainIntent);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);


        serverHandlerMainIntent = new Intent(this, ServerHandler.class);

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



    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }


        /*
    * Called by Location Services when the request to connect the
    * client finishes successfully. At this point, you can
    * request the current location or start periodic updates
    */


    }

    /**
     * Resultreciever with reference to activities handler.
     * Makes it possible for serverhandler to parse data back
     */

    class ServerReciever extends ResultReceiver {

        public ServerReciever() {
            super(activityHandler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            //Check which kind of data was send to dertermine what should be done
            try {
                if (resultCode == 0) {

                    String reason = resultData.getString("ERROR_REASON");

                    Log.e("ERROR :::: ", "Something went wrong in the ServerHandler: " + reason);


                    return;
                }

                if (resultData.getString("RESPONSE_TYPE").equals("addUser")) {
                    Log.d("Main Activity siger:  ", "HEEEEYYY ET ID!!!!");

                    int userid = resultData.getInt("USER_ID");

                    ((DataHolderApplication) getApplication()).setUserID(userid);


                } else if (resultData.getString("RESPONSE_TYPE").equals("addLocation")) {

                    boolean result = resultData.getBoolean("LOCATION_RESULT");

                    Log.w("Main Activity siger: ", "Status på LocationUpdate er: " + result);

                } else if (resultData.getString("RESPONSE_TYPE").equals("getUsers")) {

                    ArrayList<User> users = ((DataHolderApplication) getApplication()).getAllUsers();

                    for (User u : users) {
                        Log.w("User: ", u.toString());

                    }

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


    }


}
