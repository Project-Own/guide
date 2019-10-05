package com.example.guide.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
GoogleMap.OnPolylineClickListener,GoogleMap.OnPolygonClickListener{

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
        Polyline polyline=googleMap.addPolyline(new PolylineOptions().clickable(true).add(
                new LatLng(27.673661, 85.438327),
                new LatLng(27.668192, 85.427392),
                new LatLng(27.676642, 85.429743),
                new LatLng(27.674812, 85.428022),
                new LatLng(27.671388, 85.419236)


        ));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.668311, 85.431090), 4));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
//        mMap.setMinZoomPreference(10.0f); // Set a preference for minimum zoom (Zoom out).
//        mMap.setMaxZoomPreference(14.0f); // Set a preference for maximum zoom (Zoom In).

        // Add a marker in Sydney and move the camera
//        LatLng origin = new LatLng(27.678889, 85.411077);
//        CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(origin);
//        mMap.moveCamera(panToOrigin);

//        LatLngBounds ADELAIDE=new LatLngBounds(new LatLng(27.672154,85.423061),new LatLng(27.673424,85.438341));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f),null);
//
//
//
//        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);




    }


    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
