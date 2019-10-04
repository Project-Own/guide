package com.example.guide.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMinZoomPreference(10.0f); // Set a preference for minimum zoom (Zoom out).
//        mMap.setMaxZoomPreference(14.0f); // Set a preference for maximum zoom (Zoom In).

        // Add a marker in Sydney and move the camera
//        LatLng origin = new LatLng(27.678889, 85.411077);
//        CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(origin);
//        mMap.moveCamera(panToOrigin);

        LatLngBounds ADELAIDE=new LatLngBounds(new LatLng(27.672154,85.423061),new LatLng(27.673424,85.438341));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f),null);



        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);




    }




}
