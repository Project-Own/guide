package com.example.guide.ui.nearbyPlaces;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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
import com.example.guide.Model.NearbySearch.NearbySearchData;
import com.example.guide.Model.NearbySearch.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NearbyPlacesViewModel extends AndroidViewModel {

    private String searchUrl;
    private String TYPE;
    private String RADIUS = "500";
    private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";
    private String userLocation;
    private MutableLiveData<List<Result>> resultList;
    private MutableLiveData<String[]> arrayAdapterString;

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public MutableLiveData<List<Result>> loadResult(){
        if(resultList == null){
            resultList = new MutableLiveData<>();
            loadResultList();
        }
        return resultList;
    }

    private void loadResultList() {
        getNearbySearchData(0);
    }


    public MutableLiveData<String[]> loadSpinnerData(){
        if(arrayAdapterString == null){
            arrayAdapterString = new MutableLiveData<String[]>();
            loadSpinner();
        }
        return arrayAdapterString;
    }

    private void loadSpinner() {
        String[] list = {"ATM", "Restaurant", "Police Station", "Cafe",
                "Lodging", "Museum", "Pharmacy",
                "Hospital","Hindu Temple", "Bank", "Travel Agency"
        };
        arrayAdapterString.setValue(list);

    }

    public void spinnerTypeSelected(AdapterView<?> parent, View view, int position, long id) {
        getNearbySearchData(position);
    }

    public NearbyPlacesViewModel(@NonNull Application application) {
        super(application);
        userLocation = "27.655032,85.094533";
    }
    // TODO: Implement the ViewModel
    public void getNearbySearchData(int position) {


        switch (position) {
            case 0:
                TYPE = "atm";
                break;
            case 1:
                TYPE = "restaurant";
                break;
            case 2:
                TYPE = "police";
                break;
            case 3:
                TYPE = "cafe";
                break;
            case 4:
                TYPE = "lodging";
                break;
            case 5:
                TYPE = "museum";
                break;
            case 6:
                TYPE = "pharmacy";
                break;
            case 7:
                TYPE = "hospital";
                break;
            case 8:
                TYPE = "hindu_temple";
                break;
            case 9:
                TYPE = "bank";
                break;
            case 10:
                TYPE = "travel_agency";
                break;
            default:
                TYPE = "hindu_temple";

        }

        searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + userLocation + "&radius=" + RADIUS + "&type=" + TYPE + /*"&keyword=" + KEYWORD +*/ "&key=" + searchApiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchUrl, null, response -> {

            /***************************************/
            NearbySearchData nearbySearchData = new Gson().fromJson(response.toString(), NearbySearchData.class);

            List<Result> results = nearbySearchData.getResults();


            resultList.setValue(results);

            /***************************************/


            Log.i("MainActivity", "" + response);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(jsonObjectRequest);
    }


}
