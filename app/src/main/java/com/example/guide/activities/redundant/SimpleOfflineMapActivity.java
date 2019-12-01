package com.example.guide.activities.redundant;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guide.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
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
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
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
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
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
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.expressions.Expression.concat;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.downcase;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.number;
import static com.mapbox.mapboxsdk.style.expressions.Expression.pi;
import static com.mapbox.mapboxsdk.style.expressions.Expression.product;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.expressions.Expression.step;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.string;
import static com.mapbox.mapboxsdk.style.expressions.Expression.upcase;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.Property.TEXT_ANCHOR_TOP;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

// classes needed to add a marker
// classes to calculate a route

/**
 * /**
 * Download and view an offline map using the Mapbox Android SDK.
 */
public class SimpleOfflineMapActivity extends AppCompatActivity implements MapboxMap.OnMapClickListener,
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
    private ValueAnimator parkColorAnimator;
    private ValueAnimator hotelColorAnimator;
    private ValueAnimator attractionsColorAnimator;
    private Layer waterLayer;
    private LocationEngine locationEngine;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
                        Mapbox.getInstance(getBaseContext(), getString(R.string.access_token));

// This contains the MapView in XML and needs to be called after the access token is configured.
                        setContentView(R.layout.activity_simple_offline);

                        linearLayout = findViewById(R.id.loadView);

                        mapView = findViewById(R.id.mapView);
                        mapView.onCreate(savedInstanceState);
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull final MapboxMap map) {


                                map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                                    @Override
                                    public void onStyleLoaded(@NonNull final Style style) {


                                        new LoadDataTask(SimpleOfflineMapActivity.this).execute();
                                        mapboxMap = map;
//Enable locattion Plugin
                                        enableLocationComponent(style);

                                        addDestinationIconSymbolLayer(style);

                                        mapboxMap.addOnMapClickListener(SimpleOfflineMapActivity.this);
                                        button = findViewById(R.id.startButton);
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                boolean simulateRoute = true;
                                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                                        .directionsRoute(currentRoute)
                                                        .shouldSimulateRoute(simulateRoute)
                                                        .build();
// Call this method with Context from within an Activity
                                                NavigationLauncher.startNavigation(SimpleOfflineMapActivity.this, options);
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
                                        offlineManager = OfflineManager.getInstance(SimpleOfflineMapActivity.this);

// Create a bounding box for the offline region
                                        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                                                .include(new LatLng(27.726761, 85.459005)) // Northeast
                                                .include(new LatLng(27.628252, 85.459936)) // Southwest
                                                .include(new LatLng(27.645307, 85.270841))
                                                .include(new LatLng(27.732261, 85.278338))
                                                .build();


// Define the offline region
                                        final OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                                                style.getUri(),
                                                latLngBounds,
                                                10,
                                                20,
                                                SimpleOfflineMapActivity.this.getResources().getDisplayMetrics().density);

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
                                                            progressBar = findViewById(R.id.progress_bar);
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
                                                                        endProgress(getString(R.string.simple_offline_end_progress_success));
                                                                        // Create new camera position
                                                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                                                .target(definition.getBounds().getCenter())
                                                                                .zoom(definition.getMinZoom())
                                                                                .build();

                                                                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000);


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

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

// Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
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
            permissionsManager.requestLocationPermissions(this);
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

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
        Toast.makeText(this, R.string.user_location_permission_explanation,
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
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (offlineManager != null) {
            offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
                @Override
                public void onList(OfflineRegion[] offlineRegions) {
                    if (offlineRegions.length > 0) {
// delete the last item in the offlineRegions list which will be yosemite offline map
                        offlineRegions[(offlineRegions.length - 1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
                            @Override
                            public void onDelete() {
                                Toast.makeText(
                                        SimpleOfflineMapActivity.this,
                                        getString(R.string.basic_offline_deleted_toast),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("On delete error: %s", error);
                            }
                        });
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("onListError: %s", error);
                }
            });
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
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

    private void endProgress(final String message) {
// Don't notify more than once
        if (isEndNotified) {
            return;
        }


        linearLayout.setVisibility(View.GONE);
// Stop and hide the progress bar
        isEndNotified = true;
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

// Show a toast
        Toast.makeText(SimpleOfflineMapActivity.this, message, Toast.LENGTH_LONG).show();
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

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapbox_blue);
        return true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
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

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<SimpleOfflineMapActivity> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(SimpleOfflineMapActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            SimpleOfflineMapActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

// Create a Toast which displays the new location's coordinates
                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
                        String.valueOf(result.getLastLocation().getLatitude()),
                        String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_SHORT).show();

// Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
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
            SimpleOfflineMapActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class LoadDataTask extends AsyncTask<Void, Void, FeatureCollection> {

        private WeakReference<SimpleOfflineMapActivity> activity;

        LoadDataTask(SimpleOfflineMapActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected FeatureCollection doInBackground(Void... params) {

            try {
                // read local geojson from raw folder
                String tinyCountriesJson = activity.get().readRawResource( R.raw.cluster);

                return FeatureCollection.fromJson(tinyCountriesJson);

            } catch (IOException exception) {
                Timber.e(exception);
            }

            return null;
        }

        @Override
        protected void onPostExecute(FeatureCollection featureCollection) {
            super.onPostExecute(featureCollection);
            SimpleOfflineMapActivity activity = this.activity.get();
            if (featureCollection == null || activity == null) {
                return;
            }

            activity.onDataLoaded(featureCollection);
        }
    }

    public String readRawResource(@RawRes int rawResource) throws IOException {
        String json = "";

        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream is = getResources().openRawResource(rawResource)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int numRead;
            while ((numRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, numRead);
            }
        }
        json = writer.toString();

        return json;
    }

    private static final String SOURCE_ID = "com.mapbox.mapboxsdk.style.layers.symbol.source.id";
    private static final String LAYER_ID = "com.mapbox.mapboxsdk.style.layers.symbol.layer.id";
    private static final String FEATURE_ID = "brk_name";
    private static final String FEATURE_RANK = "scalerank";
    private static final String FEATURE_NAME = "name_sort";
    private static final String FEATURE_TYPE = "type";
    private static final String FEATURE_REGION = "continent";

    public void onDataLoaded(@NonNull FeatureCollection featureCollection) {
        if (mapView.isDestroyed()) {
            return;
        }

        // create expressions
        Expression iconImageExpression = string(get(literal(FEATURE_ID)));
        Expression iconSizeExpression = division(number(get(literal(FEATURE_RANK))), literal(2.0f));
        Expression textSizeExpression = product(get(literal(FEATURE_RANK)), pi());
        Expression textFieldExpression = concat(upcase(literal("a ")), upcase(string(get(literal(FEATURE_TYPE)))),
                downcase(literal(" IN ")), string(get(literal(FEATURE_REGION)))
        );
        Expression textColorExpression = match(get(literal(FEATURE_RANK)),
                literal(1), rgba(255, 0, 0, 1.0f),
                literal(2), rgba(0, 0, 255.0f, 1.0f),
                rgba(0.0f, 255.0f, 0.0f, 1.0f)
        );

        rgba(
                division(literal(255), get(FEATURE_RANK)),
                literal(0.0f),
                literal(0.0f),
                literal(1.0f)
        );

        // create symbol layer
        SymbolLayer symbolLayer = new SymbolLayer(LAYER_ID, SOURCE_ID)
                .withProperties(
                        // icon configuration
                        iconImage(iconImageExpression),
                        iconAllowOverlap(false),
                        iconSize(iconSizeExpression),
                        iconAnchor(ICON_ANCHOR_BOTTOM),
                        iconOffset(step(zoom(), literal(new float[] {0f, 0f}),
                                stop(1, new Float[] {0f, 0f}),
                                stop(10, new Float[] {0f, -35f})
                        )),

                        // text field configuration
                        textField(textFieldExpression),
                        textSize(textSizeExpression),
                        textAnchor(TEXT_ANCHOR_TOP),
                        textColor(textColorExpression)
                );

        // add a geojson source to the map
        Source source = new GeoJsonSource(SOURCE_ID, featureCollection);
        mapboxMap.getStyle().addSource(source);

        // add symbol layer
        mapboxMap.getStyle().addLayer(symbolLayer);

        // get expressions
        Expression iconImageExpressionResult = symbolLayer.getIconImage().getExpression();
        Expression iconSizeExpressionResult = symbolLayer.getIconSize().getExpression();
        Expression textSizeExpressionResult = symbolLayer.getTextSize().getExpression();
        Expression textFieldExpressionResult = symbolLayer.getTextField().getExpression();
        Expression textColorExpressionResult = symbolLayer.getTextColor().getExpression();

        // log expressions
        Timber.e(iconImageExpressionResult.toString());
        Timber.e(iconSizeExpressionResult.toString());
        Timber.e(textSizeExpressionResult.toString());
        Timber.e(textFieldExpressionResult.toString());
        Timber.e(textColorExpressionResult.toString());

        // reset expressions
        symbolLayer.setProperties(
                iconImage(iconImageExpressionResult),
                iconSize(iconSizeExpressionResult),
                textSize(textSizeExpressionResult),
                textField(textFieldExpressionResult),
                textColor(textColorExpressionResult)
        );

        new GenerateSymbolTask(mapboxMap, getBaseContext()).execute(featureCollection);
    }

    private static class GenerateSymbolTask extends AsyncTask<FeatureCollection, Void, HashMap<String, Bitmap>> {

        private MapboxMap mapboxMap;
        private WeakReference<Context> context;

        GenerateSymbolTask(MapboxMap mapboxMap, Context context) {
            this.mapboxMap = mapboxMap;
            this.context = new WeakReference<>(context);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected HashMap<String, Bitmap> doInBackground(FeatureCollection... params) {
            HashMap<String, Bitmap> imagesMap = new HashMap<>();
            Context context = this.context.get();
            List<Feature> features = params[0].features();
            if (context != null && features != null) {
                for (Feature feature : features) {
                    String countryName = feature.getStringProperty(FEATURE_ID);
                    TextView textView = new TextView(context);
                    textView.setBackgroundColor(context.getResources().getColor(R.color.mapbox_navigation_route_layer_blue));
                    textView.setPadding(10, 5, 10, 5);
                    textView.setTextColor(Color.WHITE);
                    textView.setText(countryName);
                    imagesMap.put(countryName, SimpleOfflineMapActivity.SymbolGenerator.generate(textView));
                }
            }
            return imagesMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, Bitmap> bitmapHashMap) {
            super.onPostExecute(bitmapHashMap);
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    style.addImagesAsync(bitmapHashMap);
                }
            });
        }
    }


    /**
     * Utility class to generate Bitmaps for Symbol.
     * <p>
     * Bitmaps can be added to the map with
     * </p>
     */
    private static class SymbolGenerator {

        /**
         * Generate a Bitmap from an Android SDK View.
         *
         * @param view the View to be drawn to a Bitmap
         * @return the generated bitmap
         */
        public static Bitmap generate(@NonNull View view) {
            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(measureSpec, measureSpec);

            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            view.layout(0, 0, measuredWidth, measuredHeight);
            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
    }

}