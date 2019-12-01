package com.example.guide.ui.offlineMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.MapsButton;
import com.example.guide.Model.NearbySearch.Result;
import com.example.guide.R;
import com.example.guide.databinding.OfflineMapFragmentBinding;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Looper.getMainLooper;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

public class OfflineMapFragment extends Fragment implements MapboxMap.OnMapClickListener,
        PermissionsListener {

    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private static final String TAG = "DirectionsActivity";
    private boolean isEndNotified;
    private ProgressBar progressBar;
    private OfflineManager offlineManager;
    private BuildingPlugin buildingPlugin;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;

    private LocationEngine locationEngine;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;

    private OfflineMapViewModel mViewModel;
    private ArrayList<LatLng> heritageArea;


    private RecyclerView recyclerView;
    private List<MapsButton> mapsButtonList;

    private MarkerView markerView;
    private MarkerViewManager markerViewManager;



    private SymbolManager symbolManager;
    private Symbol symbol;
    private Spinner convertPlacesSpinner;
    private OfflineMapFragmentBinding binding;
    private String MAKI_ICON_MARKER = "marker-15";
    private List<Symbol> symbols;


    public static OfflineMapFragment newInstance() {
        return new OfflineMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(getContext(), getContext().getString(R.string.access_token));

        binding = DataBindingUtil.bind(inflater.inflate(R.layout.offline_map_fragment, container, false));

// This contains the MapView in XML and needs to be called after the access token is configured.
        View view = binding.getRoot();

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-57.225365, -33.213144)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-54.14164, -33.981818)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-56.990533, -30.583266)));

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        convertPlacesSpinner = view.findViewById(R.id.spinnerNearby);

        mapView.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(@NonNull final MapboxMap map) {




                map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull final Style style) {


                        mapboxMap = map;


                        // Set up a SymbolManager instance
                        symbolManager = new SymbolManager(mapView, mapboxMap, style);


                      //  markerViewManager = new MarkerViewManager(mapView, mapboxMap);

                        //Enable locattion Plugin
                        enableLocationComponent(style);

                        addDestinationIconSymbolLayer(style);


                        mapboxMap.addOnMapClickListener(OfflineMapFragment.this::onMapClick);
                        button = view.findViewById(R.id.startButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkAvailable()) {
                                    boolean simulateRoute = true;
                                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                            .directionsRoute(currentRoute)
                                            .shouldSimulateRoute(simulateRoute)
                                            .build();
// Call this method with Context from within an Activity
                                    NavigationLauncher.startNavigation(getActivity(), options);

                                } else {
                                    buildAlertMessageNoInternetConnection();
                                }
                            }
                        });

//Set up traffic congestion
                        TrafficPlugin trafficPlugin = new TrafficPlugin(mapView, map, style);
                        trafficPlugin.setVisibility(true); // Enable the traffic view

                        //Set up Building Plugin
                        buildingPlugin = new BuildingPlugin(mapView, map, style);
                        buildingPlugin.setMinZoomLevel(15f);
                        buildingPlugin.setVisibility(true);
// Set up the OfflineManager
                        offlineManager = OfflineManager.getInstance(getActivity());

// Create a bounding box for the offline region
                        /* KAthmandu Valley bound
                                .include(new LatLng(27.726761, 85.459005)) // Northeast
                                .include(new LatLng(27.628252, 85.459936)) // Southwest
                                .include(new LatLng(27.645307, 85.270841))
                                .include(new LatLng(27.732261, 85.278338))*/
                        LatLngBounds latLngBounds = new LatLngBounds.Builder()


                                .include(new LatLng(27.691212, 85.447988)) // Top rightmost

                                .include(new LatLng(27.688212, 85.437818)) // Left side tail of top rightmost

                                .include(new LatLng(27.679657, 85.435108)) // Tail Left Slope

                                .include(new LatLng(27.678641, 85.425264)) // Top Midway slight bend

                                .include(new LatLng(27.677258, 85.416974))

                                .include(new LatLng(27.679508, 85.412062))

                                .include(new LatLng(27.677460, 85.406996)) // Leftmost after bump

                                .include(new LatLng(27.6769508, 85.403164)) //  Leftmost bump

                                .include(new LatLng(27.673928, 85.400017)) // Leftmost Peak

                                .include(new LatLng(27.671298, 85.408142)) // Below Sallaghari


                                .include(new LatLng(27.666076, 85.417879)) // Ghalel tol

                                .include(new LatLng(27.665289, 85.424768)) // Below Gapali

                                .include(new LatLng(27.665927, 85.438154)) // Left Side of V

                                .include(new LatLng(27.663825, 85.442135)) //Rightmost Bottom

                                .include(new LatLng(27.675604, 85.448746)) //RightMost Peak

                                .include(new LatLng(27.691212, 85.447988)) // Top rightmost
                                .build();


// Define the offline region
                        final OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                                style.getUri(),
                                latLngBounds,
                                10,
                                20,
                                getActivity().getResources().getDisplayMetrics().density);

// Set the metadata
                        byte[] metadata;
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(JSON_FIELD_REGION_NAME, "Bhaktapur");
                            String json = jsonObject.toString();
                            metadata = json.getBytes(JSON_CHARSET);
                        } catch (Exception exception) {
                            Log.e("", String.format("Failed to encode metadata: %s", exception.getMessage()));
                            metadata = null;
                        }

// Create the region asynchronously
                        if (metadata != null) {
                            offlineManager.createOfflineRegion(
                                    definition,
                                    metadata,
                                    new OfflineManager.CreateOfflineRegionCallback() {
                                        @Override
                                        public void onCreate(OfflineRegion offlineRegion) {
                                            offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);

// Display the download progress bar
                                            progressBar = view.findViewById(R.id.progress_bar);
                                            startProgress();

// Monitor the download progress using setObserver
                                            offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                                                @Override
                                                public void onStatusChanged(OfflineRegionStatus status) {

// Calculate the download percentage and update the progress bar
                                                    double percentage = status.getRequiredResourceCount() >= 0
                                                            ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                                                            0.0;

                                                    if (status.isComplete()) {
// Download complete
                                                      //  loadMarker();
                                                        endProgress("offline end progress success");
                                                        // Create new camera position


//// Move camera to new position
                                                        map.setMinZoomPreference(2);
//                                                        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                                    } else if (status.isRequiredResourceCountPrecise()) {
// Switch to determinate state
                                                        setPercentage((int) Math.round(percentage));
                                                    }
                                                }

                                                @Override
                                                public void onError(OfflineRegionError error) {
// If an error occurs, print to logcat
                                                    Log.e("onError reason: %s", error.getReason());
                                                    Log.e("onError message: %s", error.getMessage());
                                                }

                                                @Override
                                                public void mapboxTileCountLimitExceeded(long limit) {
// Notify if offline region exceeds maximum tile count
                                                    Log.e("", String.format("Mapbox tile count limit exceeded: %s", limit));
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Log.e("Error: %s", error);
                                        }
                                    });
                        }
                    }
                });
            }
        });
        return view;

    }

//    private void loadSymbol() {
//
//        symbolManager.setIconAllowOverlap(true);
//        symbolManager.setTextAllowOverlap(true);
//
//
//        List<Result> results = new ArrayList<>();
//        try {
//            NearbySearchData nearbySearchData = new Gson().fromJson(readRawResource(getContext(), R.raw.bank), NearbySearchData.class);
//            results = nearbySearchData.getResults();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(Result result : results) {
//
//// Add symbol at specified lat/lon
//            symbol = symbolManager.create(new SymbolOptions()
//                    .withLatLng(new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng()))
//                    .withIconImage(MAKI_ICON_HARBOR)
//                    .withIconSize(2.0f)
//                    .withDraggable(true));
//
//        }
//// Add click listener and change the symbol to a cafe icon on click
//        symbolManager.addClickListener(new OnSymbolClickListener() {
//            @Override
//            public void onAnnotationClick(Symbol symbol) {
//                Toast.makeText(getActivity(),
//                        "Symbol Clicked", Toast.LENGTH_SHORT).show();
//                symbol.setIconImage(MAKI_ICON_CAFE);
//                symbolManager.update(symbol);
//            }
//        });
//
//// Add long click listener and change the symbol to an airport icon on long click
//        symbolManager.addLongClickListener((new OnSymbolLongClickListener() {
//            @Override
//            public void onAnnotationLongClick(Symbol symbol) {
//                Toast.makeText(getActivity(),
//                        "Symbol LongClick message", Toast.LENGTH_SHORT).show();
//                symbol.setIconImage(MAKI_ICON_AIRPORT);
//                symbolManager.update(symbol);
//            }
//        }));
//
//        symbolManager.addDragListener(new OnSymbolDragListener() {
//            @Override
//// Left empty on purpose
//            public void onAnnotationDragStarted(Symbol annotation) {
//            }
//
//            @Override
//// Left empty on purpose
//            public void onAnnotationDrag(Symbol symbol) {
//            }
//
//            @Override
//// Left empty on purpose
//            public void onAnnotationDragFinished(Symbol annotation) {
//            }
//        });
//
//
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OfflineMapViewModel.class);
        symbols = new ArrayList<>();

        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.loadResult().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                if (symbolManager != null) {


                    if(symbols != null){
                        symbolManager.delete(symbols);
                    }

                    symbolManager.setIconAllowOverlap(true);
                    symbolManager.setTextAllowOverlap(false);

                    List<SymbolOptions> options = new ArrayList<>();

                    for (Result result : results) {

// Add symbol at specified lat/lon
                        options.add(new SymbolOptions()
                                .withLatLng(new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng()))
                                .withIconImage(mViewModel.getIconName())
                                .withIconSize(2.0f)
                                .withTextField(result.getName())
                                .withIconOffset(new Float[] {0f,-1.5f})
                                .withTextField(result.getName())
                                .withTextHaloColor("rgba(255, 255, 255, 100)")
                                .withTextHaloWidth(5.0f)
                                .withTextAnchor("top")
                                .withTextOffset(new Float[] {0f, 1.5f})
                        );

                    }

                    symbols = symbolManager.create(options);

// Add click listener and change the symbol to a cafe icon on click
                    symbolManager.addClickListener(new OnSymbolClickListener() {
                        @Override
                        public void onAnnotationClick(Symbol symbol) {
                            Toast.makeText(getActivity(),
                                    "Symbol Clicked", Toast.LENGTH_SHORT).show();
                            symbolManager.update(symbol);
                        }
                    });

// Add long click listener and change the symbol to an airport icon on long click
                    symbolManager.addLongClickListener((new OnSymbolLongClickListener() {
                        @Override
                        public void onAnnotationLongClick(Symbol symbol) {
                            Toast.makeText(getActivity(),
                                    "Calculating navigation route", Toast.LENGTH_SHORT).show();

                        }
                    }));

                    symbolManager.addDragListener(new OnSymbolDragListener() {
                        @Override
// Left empty on purpose
                        public void onAnnotationDragStarted(Symbol annotation) {
                        }

                        @Override
// Left empty on purpose
                        public void onAnnotationDrag(Symbol symbol) {
                        }

                        @Override
// Left empty on purpose
                        public void onAnnotationDragFinished(Symbol annotation) {
                        }
                    });
                }
            }

            });


        mViewModel.loadSpinnerData().observe(getActivity(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] s) {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                convertPlacesSpinner.setAdapter(arrayAdapter);

            }
        });
    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

// Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

// Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle)
                            .useDefaultLocationEngine(true)
                            .build();

// Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(getActivity());

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), R.string.user_location_permission_explanation,
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (markerViewManager != null) {
            markerViewManager.onDestroy();
        }
        if(symbolManager != null){
            symbolManager.onDestroy();
        }
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    // Progress bar methods
    private void startProgress() {

// Start and show the progress bar
        isEndNotified = false;
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setPercentage(final int percentage) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(percentage);
    }

    private void endProgress(String message) {
// Don't notify more than once
        if (isEndNotified) {
            return;
        }

        if (message == null) {
            message = new String("");

        }


// Stop and hide the progress bar
        isEndNotified = true;
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

// Show a toast
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();


        } else {
            if (isNetworkAvailable()) {


                if (mapboxMap != null) {
                    Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
                    assert locationComponent.getLastKnownLocation() != null;
                    Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                            locationComponent.getLastKnownLocation().getLatitude());

                    GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
                    if (source != null) {
                        source.setGeoJson(Feature.fromGeometry(destinationPoint));
                    }

                    getRoute(originPoint, destinationPoint);
                    button.setVisibility(View.VISIBLE);
                    button.setBackgroundResource(R.color.mapbox_blue);
                }
            } else {
                buildAlertMessageNoInternetConnection();
            }
        }

        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void buildAlertMessageNoInternetConnection() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your Intenet seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

// Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }


    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<OfflineMapFragment> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(OfflineMapFragment fragment) {
            this.activityWeakReference = new WeakReference<>(fragment);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            OfflineMapFragment fragment = activityWeakReference.get();

            if (fragment != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

// Pass the new location to the Maps SDK's LocationComponent
                if (fragment.mapboxMap != null && result.getLastLocation() != null) {
                    fragment.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            OfflineMapFragment activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity.getContext(), exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void addClusteredGeoJsonSource(@NonNull Style loadedMapStyle) {

        // Add a new source from the GeoJSON data and set the 'cluster' option to true.
        try {
            String tinyCountriesJson = null;
            FeatureCollection featureCollection;
                tinyCountriesJson = readRawResource( getContext(),R.raw.cluster);
                featureCollection = FeatureCollection.fromJson(tinyCountriesJson);


            loadedMapStyle.addSource(


                    // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes from
                    // 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
                    new GeoJsonSource("earthquakes",
                            new URI("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"),
                            new GeoJsonOptions()
                                    .withCluster(true)
                                    .withClusterMaxZoom(14)
                                    .withClusterRadius(50)
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Creating a marker layer for single data points
        SymbolLayer unclustered = new SymbolLayer("unclustered-points", "earthquakes");

        unclustered.setProperties(
                iconImage("cross-icon-id"),
                iconSize(
                        division(
                                get("mag"), literal(4.0f)
                        )
                ),
                iconColor(
                        interpolate(exponential(1), get("mag"),
                                stop(2.0, rgb(0, 255, 0)),
                                stop(4.5, rgb(0, 0, 255)),
                                stop(7.0, rgb(255, 0, 0))
                        )
                )
        );
        unclustered.setFilter(has("mag"));
        loadedMapStyle.addLayer(unclustered);

        // Use the earthquakes GeoJSON source to create three layers: One layer for each cluster category.
        // Each point range gets a different fill color.
        int[][] layers = new int[][]{
                new int[]{150, ContextCompat.getColor(getContext(), R.color.mapbox_navigation_route_layer_congestion_red)},
                new int[]{20, ContextCompat.getColor(getContext(), R.color.mapbox_plugins_green)},
                new int[]{0, ContextCompat.getColor(getContext(), R.color.mapbox_blue)}
        };

        for (int i = 0; i < layers.length; i++) {
            //Add clusters' circles
            CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(18f)
            );

            Expression pointCount = toNumber(get("point_count"));

            // Add a filter to the cluster layer that hides the circles based on "point_count"
            circles.setFilter(
                    i == 0
                            ? all(has("point_count"),
                            gte(pointCount, literal(layers[i][0]))
                    ) : all(has("point_count"),
                            gte(pointCount, literal(layers[i][0])),
                            lt(pointCount, literal(layers[i - 1][0]))
                    )
            );
            loadedMapStyle.addLayer(circles);
        }

        //Add the count labels
        SymbolLayer count = new SymbolLayer("count", "earthquakes");
        count.setProperties(
                textField(Expression.toString(get("point_count"))),
                textSize(12f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                textAllowOverlap(true)
        );
        loadedMapStyle.addLayer(count);
    }


    public static String readRawResource(Context context,@RawRes int rawResource) throws IOException {
            String json = "";

                Writer writer = new StringWriter();
                char[] buffer = new char[1024];
                try (InputStream is = context.getResources().openRawResource(rawResource)) {
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    int numRead;
                    while ((numRead = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, numRead);
                    }
                }
                json = writer.toString();

            return json;
    }

    public void loadMarker(Result result){

            if(markerView != null){
                markerViewManager.removeMarker(markerView);
            }
            // Use an XML layout to create a View object
            View customView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.marker_view_bubble, null);
            customView.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

// Set the View's TextViews with content
            TextView titleTextView = customView.findViewById(R.id.marker_window_title);
            titleTextView.setText(result.getName());

            TextView snippetTextView = customView.findViewById(R.id.marker_window_snippet);
            snippetTextView.setText(result.getVicinity());

// Use the View to create a MarkerView which will eventually be given to
// the plugin's MarkerViewManager class
            markerView = new MarkerView(new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng()), customView);
            markerViewManager.addMarker(markerView);


    }

}
