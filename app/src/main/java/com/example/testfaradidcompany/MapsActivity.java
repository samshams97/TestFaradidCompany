package com.example.testfaradidcompany;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> listPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listPoints = new ArrayList<>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // reset market whent already 2
                if (listPoints.size() == 2){
                    listPoints.clear();
                    mMap.clear();
                }
                // save first point selected
                listPoints.add(latLng);
                // create market 1 and marker 2
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                if (listPoints.size() == 1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(markerOptions);

                if (listPoints.size() == 2){
                    //create url to get request from markerr1 to marker2
                    String url = getRequestUrl(listPoints.get(0),listPoints.get(1));
                }

            }

        });
    }

    private String getRequestUrl(LatLng origin, LatLng destination) {
        // value of origin
        String org = "origin : "+origin.latitude+","+ origin.longitude;
        // value of destination
        String des = "destination : " + destination.latitude + "," + destination.longitude;
        // set value enable the sensor
        String sen = "sensor = false";
        //mode for find direction
        String mode = "mode = driving";
        // build the full param
        String param = "param : " +org+ "& " +des+ "&" +sen+ "&" +mode;
        //output format
        String output = "json";
        //request
        String url = "https://maps.googleapis.com/maps/directions/" +output+ "?" +param;
        return url;
    }
}
