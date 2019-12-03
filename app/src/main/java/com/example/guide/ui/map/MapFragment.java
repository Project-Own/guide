package com.example.guide.ui.map;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.CustomRenderer;
import com.example.guide.Model.Geofence.MyLatLng;
import com.example.guide.Model.MarkerItem;
import com.example.guide.R;
import com.example.guide.activities.MainActivity;
import com.example.guide.databinding.MapFragmentBinding;
import com.example.guide.interfaces.IOnLoadLocationListener;
import com.example.guide.interfaces.TagsListInterface;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapFragment extends Fragment implements OnMapReadyCallback, GeoQueryEventListener, IOnLoadLocationListener {

    private MapViewModel mViewModel;

    private GoogleMap mMap;

    /****************************************************************************************************/
    Marker nearbyMarker;
    private Location lastLocation;
    private IOnLoadLocationListener listener;
    private DatabaseReference myCity;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    TagsListInterface tagsListInterface;
    ClusterManager<MarkerItem> mClusterManager;

    private PolygonOptions polygonOptions;

    private LatLngBounds BHAKTAPUR = new LatLngBounds(
            new LatLng(27.651823, 85.424451), new LatLng(27.697431, 85.421389));


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
    CustomRenderer customRenderer;
    int i = 0;
    private float GEOFENCE_RADIUS = 0.02f; // 0.5 = 500m
    private List<LatLng> heritageArea;
    private MapFragmentBinding binding;
    private Spinner convertPlacesSpinner;
    private double latitude, longitude;

    public static Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
        return resultBitmap;
    }

    List<Marker> bathroomMarker = new ArrayList<>();
     List<Marker> counterMarker = new ArrayList<>();

     Toolbar toolbar;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.map_fragment, container, false));
        View view = binding.getRoot();
        convertPlacesSpinner = view.findViewById(R.id.spinnerNearby);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.






                        buildLocationRequest();
                        buildLocationCallback();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

                        //Called after heritageArea loaded fromm database
                    /*    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
*/
                        //    addGeoPointToFirebaseDatabase();
                        initArea();
                        settingGeoFire();


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(), "You must enable permissions", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);



            mClusterManager = new ClusterManager<MarkerItem>(getContext(), mMap);


            mViewModel.loadSpinnerData().observe(this, new Observer<String[]>() {
                @Override
                public void onChanged(String[] strings) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, strings);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    convertPlacesSpinner.setAdapter(arrayAdapter);
                }
            });

            convertPlacesSpinner.setVisibility(View.VISIBLE);

            mViewModel.loadResult().observe(this, new Observer<List<MarkerOptions>>() {
                @Override
                public void onChanged(List<MarkerOptions> markerOptionsList) {

                    if (mMap != null) {


                        CustomRenderer clusterRenderer = new CustomRenderer(getContext(), mMap, mClusterManager);

                        // Point the map's listeners at the listeners implemented by the cluster
                        // manager.
                        mMap.setOnCameraIdleListener(mClusterManager);
                        mMap.setOnMarkerClickListener(mClusterManager);
                        mClusterManager.setRenderer(clusterRenderer);

                        mClusterManager.clearItems();
                        mClusterManager.getClusterMarkerCollection().clear();
                        mClusterManager.cluster();
                        // Initialize the manager with the context and the map.
                        // (Activity extends context, so we can pass 'this' in the constructor.)


                        if (markerOptionsList == null) {

                            return;
                        }
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.671635, 85.429339), 15));

// Create a cluster item for the marker and set the title and snippet using the constructor.
                        for (MarkerOptions markerOptions : markerOptionsList) {
                            MarkerItem infoWindowItem = new MarkerItem(markerOptions);
                            mClusterManager.addItem(infoWindowItem);
                            mClusterManager.cluster();
                        }

                    }

                }

            });





    }

    private void addUserMarker() {
        geoFire.setLocation("You", new GeoLocation(lastLocation.getLatitude(),
                lastLocation.getLongitude()), (key, error) -> {

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

    private void initArea() {

        // Referencing firebase database
        myCity = FirebaseDatabase.getInstance()
                .getReference("HeritageArea")
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
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*heritageArea = new ArrayList<>();
        heritageArea.add(new LatLng(37.422,-122.044));
        heritageArea.add(new LatLng(37.422,-122.144));
        heritageArea.add(new LatLng(37.422,-122.244));

        //Comment After execution
        FirebaseDatabase.getInstance()
                .getReference("heritageArea")
                .child("MyCity")
                .setValue(heritageArea)
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

    @Override
    public void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        sendNotification("Bhaktapur Guide", String.format("%s entered the heritage area", key));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mClusterManager = new ClusterManager<>(getContext(), mMap);
        customRenderer = new CustomRenderer(getContext(), mMap, mClusterManager);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);


        int fillColorArgb = getResources().getColor(R.color.fillColorRgb);

        int strokeColorArgb = Color.HSVToColor(20, new float[]{0, 0, 0});

        polygonOptions = new PolygonOptions()
                .addAll(
                        Arrays.asList(
                                new LatLng(26.591212, 86.47988), // Top rightmost
                                new LatLng(26.591212, 84.247988), // Top rightmost
                                new LatLng(28.791212, 84.247988), // Top rightmost
                                new LatLng(28.791212, 86.47988)// Top rightmost

                        )
                )
                .addHole(
                        Arrays.asList(
                                new LatLng(27.691212, 85.447988), // Top rightmost

                                new LatLng(27.688212, 85.437818), // Left side tail of top rightmost

                                new LatLng(27.679657, 85.435108), // Tail Left Slope

                                new LatLng(27.678641, 85.425264), // Top Midway slight bend

                                new LatLng(27.677258, 85.416974),

                                new LatLng(27.679508, 85.412062),

                                new LatLng(27.677460, 85.406996), // Leftmost after bump

                                new LatLng(27.6769508, 85.403164), //  Leftmost bump

                                new LatLng(27.673928, 85.400017), // Leftmost Peak

                                new LatLng(27.671298, 85.408142), // Below Sallaghari


                                new LatLng(27.666076, 85.417879), // Ghalel tol

                                new LatLng(27.665289, 85.424768), // Below Gapali

                                new LatLng(27.665927, 85.438154), // Left Side of V

                                new LatLng(27.663825, 85.442135), //Rightmost Bottom

                                new LatLng(27.675604, 85.448746), //RightMost Peak

                                new LatLng(27.691212, 85.447988) // Top rightmost
                        )
                )
                .fillColor(fillColorArgb)
                .strokeColor(strokeColorArgb)
                .clickable(true)
                .strokeJointType(JointType.ROUND) //Bevel,round,default
                .strokeWidth(POLYGON_STROKE_WIDTH_PX)
                 // dot-(Dot,Gap){....} dash-(Dash,Gap){---} mixed-(dot,gap,dash,gap)

        ;

        mMutalbePolygon = mMap.addPolygon(polygonOptions);


/*
        GroundOverlayOptions bhaktapurMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.bhaktapur_map))
                .transparency(0.5f)
                .position(new LatLng(27.671635, 85.429339), 8600f, 6500f);
        mMap.addGroundOverlay(bhaktapurMap);
*/
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(27.671635, 85.429339))      // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.


        if(getArguments() != null) {
            String redirect = getArguments().getString("redirect");

            if (redirect != null) {
                switch (redirect) {
                    case "counter":
                        mViewModel.loadResultList(getArguments().getInt("counter"));
                        break;
                    case "marker":
                        latitude = getArguments().getDouble("latitude");
                        longitude = getArguments().getDouble("longitude");
                        String title = getArguments().getString("title");
                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude))
                                .title(title)
                                .snippet(getArguments().getString("description"));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

                        break;
                    default:

                }
            }
        }else{
            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(27.671635, 85.429339))      // Sets the center of the map to Mountain View
                    .zoom(13)                   // Sets the zoom
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }




        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        // Add Circle for dangerous area
        addCircleArea();


    }

    @Override
    public void onKeyExited(String key) {
        //   sendNotification("Bhaktapur Guide", String.format("%s leaved the heritage area", key));

    }

    private void sendNotification(String title, String content) {
        Toast.makeText(getContext(), "" + content, Toast.LENGTH_SHORT).show();
        String NOTIFICAION_CHANNEL_ID = "edit_multiple_location";
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        notificationIntent.putExtra("NotificationMessage", "I am from Notification");
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);

        //Config
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICAION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Channel Description");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFICAION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.logo_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                .setContentIntent(resultIntent)
        ;
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(), notification);
    }


    /* NearbySearchData*/

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void addGeoPointToFirebaseDatabase() {
        heritageArea = new ArrayList<>();
//        Dattatry:27.672275, 85.429280
//        Bhaktapur durbarSquare square:27.672378, 85.428124
//        55 Window Palace:27.672393, 85.428578
//
//        Golden gate:27.672476, 85.428464
//        Bhairav nath:27.671359, 85.429517
//
//        Pashupati nath:27.671834, 85.428458
//        Kedar nath:27.672037, 85.427865
//        navadurga:27.675410, 85.435734
//        wakupati narayan:27.673746, 85.437095
//        pottery square:27.670156, 85.427781
        heritageArea.add(new LatLng(27.673494, 85.435307)); //Dattatray
        heritageArea.add(new LatLng(27.672075, 85.428101)); //Bhaktapur Durbar
        heritageArea.add(new LatLng(27.672157, 85.428591)); // Window Palace
        heritageArea.add(new LatLng(27.672155, 85.428421)); // Golden Gate
        heritageArea.add(new LatLng(27.671103, 85.429502)); // Bhairav nath
        heritageArea.add(new LatLng(27.671802, 85.428457)); // PashupatiNath
        heritageArea.add(new LatLng(27.672037, 85.427865)); // KedarNath
        heritageArea.add(new LatLng(27.675112, 85.435722)); // NavaDurga
        heritageArea.add(new LatLng(27.673452, 85.437138)); // Wakupati Narayan
        heritageArea.add(new LatLng(27.669888, 85.427779)); // Pottery Square
        heritageArea.add(new LatLng(27.673791, 85.403437)); // Pottery Square
        heritageArea.add(new LatLng(27.671363, 85.429330)); // Nyatapolo
        heritageArea.add(new LatLng(27.678230, 85.439310)); // my home

        //Comment After execution
        FirebaseDatabase.getInstance()
                .getReference("HeritageArea")
                .child("MyCity")
                .setValue(heritageArea)
                .addOnCompleteListener(task -> Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        // sendNotification("Bhaktapur Guide", String.format("%s move within the heritage area", key));
    }

    @Override
    public void onLoadLocationFailed(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }
    //private String KEYWORD = "cruise";

    @Override
    public void onLoadLocationSucess(List<MyLatLng> latLngs) {
        heritageArea = new ArrayList<>();
        for (MyLatLng myLatLng : latLngs) {
            LatLng convert = new LatLng(myLatLng.getLatitude(), myLatLng.getLongitude());
            heritageArea.add(convert);
        }
        //Afer dangerous area loaded
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
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
    public void onDestroy() {
        toolbar.setVisibility(View.VISIBLE);
        super.onDestroy();
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
        for (LatLng latlng : heritageArea) {
//            mMap.addCircle(new CircleOptions().center(latlng)
//                    .radius(GEOFENCE_RADIUS * 1000) // GEOFENCE_RADIUS : 0.5f * 1000 = 500m
//                    .strokeColor(Color.BLUE)
//                    .fillColor(0x220000ff)
//                    .strokeWidth(5.0f)
//            );

            // Create GeoQuery when user is in dangerous location
            geoQuery = geoFire.queryAtLocation(new GeoLocation(latlng.latitude, latlng.longitude), GEOFENCE_RADIUS); //500m
            geoQuery.addGeoQueryEventListener(this);
        }
    }






}
