package com.example.matthewdarke.mymappingphotos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static com.example.matthewdarke.mymappingphotos.MyMapFragment.TAG;
import static com.example.matthewdarke.mymappingphotos.MyMapFragment.newInstance;


/**
 * Created by matthewdarke on 3/24/15.
 */



public class MainActivity extends Activity implements Serializable {

    public static final String ADD_EXTRA_ADD_TOO = "com.example.matthewdarke.MymappingPhotos";
    public static final String ADD_FROM = "com.example.matthewdarke.MymappingPhotos.ADD_FROM";
    public static ArrayList<Items> itemsArray = new ArrayList<>();
    private static final long serialVersionUID = 8736847634070552888L;
    private static final int REQUEST_ENABLE_GPS = 0x02001;



    public LocationManager mManager;
    public static final int REQUEST_CODE = 2;

    //public static Items mConData;
    public static Items mItmData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager mgr = getFragmentManager();
        FragmentTransaction trans = mgr.beginTransaction();

        MyMapFragment frag = newInstance();
        trans.replace(R.id.master_container, frag, TAG);

        //behind the scenes, the fragment manager posts a new message to the main
        // thread saying that it has changes to commit. Then, the next time the main
        // thread loops around, those changes are actually committed.
        trans.commit();



        mgr = getFragmentManager();
        trans = mgr.beginTransaction();
        mManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }

    private void enableGps() {
        if(mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //seconds// meters
            //mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null) {
               loc.getLatitude();
                loc.getLongitude();
            }

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        enableGps();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //mManager.removeUpdates();
    }

    //@Override
    public void onLocationChanged(Location location) {
       // mLatitude.setText("" + location.getLatitude());
       // mLongitude.setText("" + location.getLongitude());
    }

   // @Override
    public void onProviderDisabled(String provider) {

    }

    //@Override
    public void onProviderEnabled(String provider) {

    }

   // @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        MyMapFragment mainFragment = (MyMapFragment) getFragmentManager()
                .findFragmentById(R.id.main_activity_id);
        super.onActivityResult(requestCode, resultCode, data);
        // Indicate state of results
        if (requestCode == REQUEST_CODE
                && resultCode == RESULT_OK) {


            ArrayList<Items> first =
                    (ArrayList<Items>)
                            data.getSerializableExtra("conData");

            if (itemsArray != null) {

                itemsArray.addAll(first);


                // set up mapp adapter






                saveData();
                enableGps();

            }

        }

    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Add_Contacts) {
            Intent addTo = new Intent(this, AddActivity.class);
             addTo.putExtra("addto", ADD_FROM);
            startActivityForResult(addTo, REQUEST_CODE);



        }

        return super.onOptionsItemSelected(item);
    }
    //saves data using File output stream stores to a file


    public void saveData() {


        try {
            FileOutputStream outputStream =
                    openFileOutput("matt.txt", Context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(outputStream);

            //add data items to array
            for (Items iItemsArray : itemsArray) {

                mItmData = iItemsArray;

                //write objects
                objectOutputStream.writeObject(mItmData);

                //updateWidgetView();
            }

            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadSavedData() {

        MyMapFragment mainFragment =
                (MyMapFragment)
                        getFragmentManager().
                                findFragmentById(R.id.main_activity_id);


        try {

            FileInputStream inputStream =
                    openFileInput("matt.txt");

            ObjectInputStream objectInputStream =
                    new ObjectInputStream(inputStream);



            while (inputStream.available() != 0) {

                //cast data
                mItmData = (Items)
                        objectInputStream.readObject();

                itemsArray.add(mItmData);
                //close input stream
            }

            //close input stream

            objectInputStream.close();
//add objects to array adapter




        } catch (Exception e) {

            e.printStackTrace();
        }
    }



}



