package com.example.guide.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
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
import com.example.guide.Modal.Geofence.MyLatLng;
import com.example.guide.Modal.NearbySearch.NearbySearchData;
import com.example.guide.Modal.NearbySearch.Result;
import com.example.guide.R;
import com.example.guide.interfaces.IOnLoadLocationListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener, IOnLoadLocationListener {

    private GoogleMap mMap;

    /****************************************************************************************************/
    Marker nearbyMarker;
    private Location lastLocation;
    private IOnLoadLocationListener listener;
    private DatabaseReference myCity;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    private float GEOFENCE_RADIUS = 0.5f; // 0.5 = 500mf
    private List<LatLng> dangerousArea;

    private PolygonOptions polygonOptions;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    //For HIGHLIGHING MAP
    private Polygon mMutalbePolygon;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private LocationRequest locationRequest;
    private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";
    private String LOCATION = "27.671635,85.429339"; //Nyatapol Center
    private String RADIUS = "1000";
    private String TYPE = "restaurant";
    private String searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LOCATION + "&radius=" + RADIUS + "&type=" + TYPE + /*"&keyword=" + KEYWORD +*/ "&key=" + searchApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Dexter is a library for checking necessary permissions

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

                        buildLocationRequest();
                        buildLocationCallback();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

                        //Called after dangerousarea loaded fromm database
                    /*    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
*/
                        initArea();
                        settingGeoFire();


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(), "You must enable permissions", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
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

        googleMap.moveCamera(newLatLngZoom(new LatLng(27.668311, 85.431090), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15),null);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);


        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        // Add Circle for dangerous area
        addCircleArea();

        getNearbySearchData();
    }

    private void initArea() {

        // Referencing firebase database
        myCity = FirebaseDatabase.getInstance()
                .getReference("DangerousArea")
                .child("MyCity");
        // Sync with firebase database when online
        myCity.keepSynced(true);

        listener = this;


//                myCity.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        List<MyLatLng> latLnglist = new ArrayList<>();
//                        for(DataSnapshot locationSnapShot : dataSnapshot.getChildren()){
//                            MyLatLng latLng = locationSnapShot.getValue(MyLatLng.class);
//                            latLnglist.add(latLng);
//                        }
//                        listener.onLoadLocationSucess(latLnglist);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        listener.onLoadLocationFailed(databaseError.getMessage());
//                    }
//                });


        //load from Firebase
        myCity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Update
                List<MyLatLng> latLnglist = new ArrayList<>();
                for (DataSnapshot locationSnapShot : dataSnapshot.getChildren()) {
                    MyLatLng latLng = locationSnapShot.getValue(MyLatLng.class);
                    latLnglist.add(latLng);
                }
                listener.onLoadLocationSucess(latLnglist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*dangerousArea = new ArrayList<>();
        dangerousArea.add(new LatLng(37.422,-122.044));
        dangerousArea.add(new LatLng(37.422,-122.144));
        dangerousArea.add(new LatLng(37.422,-122.244));

        //Comment After execution
        FirebaseDatabase.getInstance()
                .getReference("DangerousArea")
                .child("MyCity")
                .setValue(dangerousArea)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MapsActivity.this, "Updated", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

*/
    }

    private void addGeoPointToFirebaseDatabase() {
        dangerousArea = new ArrayList<>();
        dangerousArea.add(new LatLng(37.422, -122.044));
        dangerousArea.add(new LatLng(37.422, -122.144));
        dangerousArea.add(new LatLng(37.422, -122.244));

        //Comment After execution
        FirebaseDatabase.getInstance()
                .getReference("DangerousArea")
                .child("MyCity")
                .setValue(dangerousArea)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addUserMarker() {
        geoFire.setLocation("You", new GeoLocation(lastLocation.getLatitude(),
                lastLocation.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (currentUser != null) {
                    currentUser.remove();
                }
                currentUser = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lastLocation.getLatitude(),
                                lastLocation.getLongitude()))
                        .title("You")
                );
                //Animate Camera
                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngZoom(currentUser.getPosition(), 12.0f));


            }
        });
    }

    private void settingGeoFire() {

        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if (mMap != null) {

                    lastLocation = locationResult.getLastLocation();

                    addUserMarker();


                }
            }
        };

    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(50000)
                .setFastestInterval(3000)
                .setSmallestDisplacement(10f)
        ;

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

    public void addCircleArea() {
        if (geoQuery != null) {
            geoQuery.removeGeoQueryEventListener(this);
            geoQuery.removeAllListeners();

        }
        // Add Circle for dangerous area
        for (LatLng latlng : dangerousArea) {
            mMap.addCircle(new CircleOptions().center(latlng)
                    .radius(GEOFENCE_RADIUS * 1000) // GEOFENCE_RADIUS : 0.5f * 1000 = 500m
                    .strokeColor(Color.BLUE)
                    .fillColor(0x220000ff)
                    .strokeWidth(5.0f)
            );

            // Create GeoQuery when user is in dangerous location
            geoQuery = geoFire.queryAtLocation(new GeoLocation(latlng.latitude, latlng.longitude), GEOFENCE_RADIUS); //500m
            geoQuery.addGeoQueryEventListener(this);
        }
    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        sendNotification("EDMTDev", String.format("%s entered the dangerous area", key));
    }

    @Override
    public void onKeyExited(String key) {
        sendNotification("EDMTDev", String.format("%s leaved the dangerous area", key));

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("EDMTDev", String.format("%s move within the dangerous area", key));
    }

    private void sendNotification(String title, String content) {
        Toast.makeText(this, "" + content, Toast.LENGTH_SHORT).show();
        String NOTIFICAION_CHANNEL_ID = "edit_multiple_location";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //Config
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICAION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Channel Description");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICAION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(), notification);
    }


    /* NearbySearchData*/

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadLocationSucess(List<MyLatLng> latLngs) {
        dangerousArea = new ArrayList<>();
        for (MyLatLng myLatLng : latLngs) {
            LatLng convert = new LatLng(myLatLng.getLatitude(), myLatLng.getLongitude());
            dangerousArea.add(convert);
        }
        //Afer dangerous area loaded
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildLocationCallback();
        //clear map and add again
        if (mMap != null) {
            mMap.clear();
            // Relaod map
            addUserMarker();
            //Add Circle of dangerous area
            addCircleArea();
        }
    }

    @Override
    public void onLoadLocationFailed(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
    //private String KEYWORD = "cruise";

    public void getNearbySearchData() {
        searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LOCATION + "&radius=" + RADIUS + "&type=" + TYPE + /*"&keyword=" + KEYWORD +*/ "&key=" + searchApiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                /***************************************/
                NearbySearchData nearbySearchData = new Gson().fromJson(response.toString(), NearbySearchData.class);

                List<Result> results = nearbySearchData.getResults();

                String message = "Total list: " + results.size() + "\n\n";
                for (Result result : results) {
                    URL url = null;
                    try {
                        url = new URL(result.getIcon());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    nearbyMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(result.getGeometry().getLocation().getLat(),
                                    result.getGeometry().getLocation().getLng()))
                            .title(result.getName())
                            .snippet("Vicinity :" + result.getVicinity())
                    );
                }
                /***************************************/


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


/****************************************************************************************************/
}
