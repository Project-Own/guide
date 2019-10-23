package com.example.guide.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.guide.Modal.NearbySearch.NearbySearchData;
import com.example.guide.Modal.NearbySearch.Result;
import com.example.guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        {

    private GoogleMap mMap;

    private Polygon mMutalbePolygon;

    private PolygonOptions polygonOptions;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


            private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";


            /* NearbySearchData*/
            // View2
            private String LOCATION = "27.671298,85.408142";
            private String RADIUS = "1500";
            private String TYPE = "restaurant";
            private String searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LOCATION + "&radius=" + RADIUS + "&type=" + TYPE + /*"&keyword=" + KEYWORD +*/ "&key=" + searchApiKey;
            //private String KEYWORD = "cruise";
            private Marker nearbyMarker;

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
        mMap.setMyLocationEnabled(true);


        int fillColorArgb = Color.HSVToColor(100,new float[]{360,1,1});

        int strokeColorArgb = Color.HSVToColor(200,new float[]{0,1,1});

        polygonOptions = new PolygonOptions()

                .addAll(
                        Arrays.asList(
                                new LatLng(27.691212,85.447988), // Top rightmost

                                new LatLng(27.688212,85.437818), // Left side tail of top rightmost

                                new LatLng(27.679657,85.435108), // Tail Left Slope

                                new LatLng(27.678641,85.425264), // Top Midway slight bend

                                new LatLng(27.677258,85.416974),

                                new LatLng(27.679508,85.412062),

                                new LatLng(27.677460,85.406996), // Leftmost after bump

                                new LatLng(27.6769508,85.403164), //  Leftmost bump

                                new LatLng(27.673928,85.400017), // Leftmost Peak

                                new LatLng(27.671298,85.408142), // Below Sallaghari


                                new LatLng(27.666076,85.417879), // Ghalel tol

                                new LatLng(27.665289,85.424768), // Below Gapali

                                new LatLng(27.665927,85.438154), // Left Side of V

                                new LatLng(27.663825,85.442135), //Rightmost Bottom

                                new LatLng(27.675604,85.448746), //RightMost Peak

                                new LatLng(27.691212,85.447988) // Top rightmost
                        )
                )
                .fillColor(fillColorArgb)
                .strokeColor(strokeColorArgb)
                .clickable(true)
                .strokeJointType(JointType.ROUND) //Bevel,round,default
                .strokePattern(Arrays.asList(DASH,GAP)) // dot-(Dot,Gap){....} dash-(Dash,Gap){---} mixed-(dot,gap,dash,gap)
                .strokeWidth(POLYGON_STROKE_WIDTH_PX)
        ;

        mMutalbePolygon = mMap.addPolygon(polygonOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.668311, 85.431090), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15),null);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        /***********************/
        getNearbySearchData();
        /***********************/


        mMap.setMinZoomPreference(0.0f); // Set a preference for minimum zoom (Zoom out).
        mMap.setMaxZoomPreference(20.0f); // Set a preference for maximum zoom (Zoom In).

    }

            public void getNearbySearchData() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Log.i("MainActivity", "" + response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }) {
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        try {
                            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                            if (cacheEntry == null) {
                                cacheEntry = new Cache.Entry();
                            }
                            final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                            final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                            long now = System.currentTimeMillis();
                            final long softExpire = now + cacheHitButRefreshed;
                            final long ttl = now + cacheExpired;
                            cacheEntry.data = response.data;
                            cacheEntry.softTtl = softExpire;
                            cacheEntry.ttl = ttl;
                            String headerValue;
                            headerValue = response.headers.get("Date");
                            if (headerValue != null) {
                                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                            }
                            headerValue = response.headers.get("Last-Modified");
                            if (headerValue != null) {
                                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                            }
                            cacheEntry.responseHeaders = response.headers;
                            final String jsonString = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers));


                            /***************************************/
                            NearbySearchData nearbySearchData = new Gson().fromJson(jsonString, NearbySearchData.class);

                            List<Result> result = nearbySearchData.getResults();

                            String message = "Total list: " + result.size() + "\n\n";
                            for (Result results : result) {

                                nearbyMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(results.getGeometry().getLocation().getLat(),
                                                results.getGeometry().getLocation().getLng()))
                                        .title("You")
                                );
//                                message += "Name: "+ results.getName()
//                                        +"Longitude: " + results.getGeometry().getLocation().getLng()
//                                        +"Latitude: " + results.getGeometry().getLocation().getLat()
//                                        +"\n\n"
//                                ;
                            }
                            /***************************************/


                            return Response.success(new JSONObject(jsonString), cacheEntry);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            return Response.error(new ParseError(e));
                        }
                    }

                    @Override
                    protected void deliverResponse(JSONObject response) {
                        super.deliverResponse(response);
                    }

                    @Override
                    protected VolleyError parseNetworkError(VolleyError volleyError) {
                        return super.parseNetworkError(volleyError);
                    }

                    @Override
                    public void deliverError(VolleyError error) {
                        super.deliverError(error);
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonObjectRequest);
            }


        }