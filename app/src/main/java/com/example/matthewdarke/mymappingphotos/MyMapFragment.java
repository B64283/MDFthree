package com.example.matthewdarke.mymappingphotos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by matthewdarke on 3/24/15.
 */
public class MyMapFragment extends MapFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    Items items;
    public ArrayList<Items> mItmData;
    public static final int REQUEST_CODE = 2;
    GoogleMap mMap;
    public static final String TAG = "MapFragment.TAG";


    public static MyMapFragment newInstance() {
        MyMapFragment frag = new MyMapFragment();
        return frag;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }




//for interface communication
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }



//for any necessary setup for added info
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMap = getMap();
        mItmData = new ArrayList<>();



        if (getActivity() != null) {
            ((MainActivity)getActivity()).loadSavedData();
        }
        mMap.setInfoWindowAdapter(new MarkerAdapter());
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title(_title));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.593770, -81.303797), 16));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.595842,-81.304188)).title("Full Sail Live"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.596591,-81.301302)).title("Advising"));



    }






    @Override
    public void onInfoWindowClick(final Marker marker) {

       String markerInf = marker.getTitle();
        //String markerInfAddress.getSnippett()
        String items = MainActivity.mItmData.getmName(markerInf);
        System.out.println("clicked on this marker::" + items);
        Intent pdIntent = new Intent(getActivity(),DetailActivity.class);
           startActivityForResult(pdIntent, REQUEST_CODE);




        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.mItmData.getmName(markerInf), "mName");
        bundle.putString(MainActivity.mItmData.getmAddress(), "mQuantity");
        bundle.putString(MainActivity.mItmData.getmPicture(), "mPic");

        // requesting the start of a new Activity (DetailActivity) within The application.
       // Intent pdIntent = new Intent(view.getContext(),DetailActivity.class);

        //passing data to detail using putExtra in the intent
        pdIntent.putExtra("itemsArray", ((MainActivity)getActivity()).itemsArray);
        pdIntent.putExtras(bundle);

        //Using startActivity Android will process the intent (pdIntent), and launch the requested component from the Intent.
        startActivity(pdIntent);

                        //passing data to detail using putExtra in the intent
                        pdIntent.putExtra("itemsArray", ((MainActivity)getActivity()));
                    }






    @Override
    public void onMapClick(final LatLng location ) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Map Clicked")
                .setMessage("Add new marker here?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {



                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent addTo = new Intent(getActivity(), AddActivity.class);

                            startActivityForResult(addTo, REQUEST_CODE);
                            mMap.addMarker(new MarkerOptions().position(location).title("New Marker"));
                        }
                    })
                            .show();
                }



    @Override
    public void onMapLongClick(LatLng latLng) {



        Intent addTo = new Intent(getActivity(), AddActivity.class);
        addTo.putExtra("lattitude",latLng.latitude );
        addTo.putExtra("longitude", latLng.longitude);
        //mMap.addMarker(new MarkerOptions().position(latLng).title("New Marker"));
        startActivityForResult(addTo, REQUEST_CODE);

    }




    public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {

        TextView mText;
        TextView mSnippert;
        //ImageView mPic;

        public MarkerAdapter() {
            mText = new TextView(getActivity());
            mSnippert = new TextView(getActivity());
            //mPic = new ImageView(getActivity());
        }

        @Override
        public View getInfoContents(Marker marker) {

            ViewGroup vwG = (ViewGroup)getView();
            View v = getActivity().getLayoutInflater().inflate(R.layout.custom_info,vwG,false );
            TextView userText = (TextView)v. findViewById(R.id.textViewTitle);
            TextView snip = (TextView)v.findViewById(R.id.textView2Snippet);
            ImageView userPic = (ImageView)v.findViewById(R.id.imageViewWindow);

            userText.setText(marker.getTitle());
            snip.setText(marker.getSnippet());



            //mText.setText(marker.getTitle());

            return v;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }






    }
}
