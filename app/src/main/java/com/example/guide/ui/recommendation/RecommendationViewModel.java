package com.example.guide.ui.recommendation;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.guide.Model.NearbySearch.NearbySearchData;
import com.example.guide.Model.NearbySearch.Result;
import com.example.guide.Model.Recommendation;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationViewModel extends AndroidViewModel {

    private String TYPE;
    private String searchUrl;

    private String RADIUS = "500";
    private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";
    private String userLocation;
    private MutableLiveData<List<Result>> resultList;
    private MutableLiveData<String[]> arrayAdapterString;


    private MutableLiveData<List<Recommendation>> recommendationList;
    private MutableLiveData<List<Recommendation>> hotelList;

    public RecommendationViewModel(@NonNull Application application) {
        super(application);
        userLocation = "27.655032,85.094533";

    }

    public MutableLiveData<List<Recommendation>> loadHotel(){
        if(hotelList == null){
            hotelList = new MutableLiveData<List<Recommendation>>();
            loadHotelList();
        }
        return hotelList;
    }


    public MutableLiveData<List<Recommendation>> loadRecommendedPlaces(){
        if(recommendationList == null){
            recommendationList = new MutableLiveData<List<Recommendation>>();
            loadRecommendedPlacesList();
        }
        return recommendationList;
    }

    private void loadRecommendedPlacesList() {
        List<Recommendation> recomendationsPlaces = new ArrayList<>();
        recomendationsPlaces.add(new Recommendation("Bhaktapur Durbar Square", "", "", "", "", "one of the largest ancient squares in Nepal filled to the " +
                "\n brim with temples, cultural carvings and buildings ", "durbarsquare"));
        recomendationsPlaces.add(new Recommendation("Basantapur Chowk", "", "", "", "", "two famous sculptures that cost a man his hands \n" +
                "\n", "basantapur"));
        recomendationsPlaces.add(new Recommendation("The Palace of Fifty-five Windows", "", "", "", "", "a masterpiece of wood carving The Golden Gate: one of " +
                "\n the most intricate and well decorated gates in the world", "durbarsquare"));
        recomendationsPlaces.add(new Recommendation("Pashupatinath Temple", "", "", "", "", "impressive wooden temple", "pasupati"));
        recomendationsPlaces.add(new Recommendation("Vatsala Durga Temple & Taleju Bell", "", "", "", "", "Stone temple with bell built by King Jagat Prakash Malla " +
                "\n (currently - 2017/18- under reconstruction however you can give donations directly to its rebuilding) ", "vatsala"));
        recomendationsPlaces.add(new Recommendation("Siddhi Laxmi Temple", "", "", "", "", "A stone temple built in the 17th-century (currently - 2017/18- under reconstuction)\n" +
                "\n", "siddhilaxmi"));
        recomendationsPlaces.add(new Recommendation("Pottery Square", "", "", "", "", "Watch Bhaktapur's famous pottery as it's made in this idyllic traditional square filled with potters and their wares. ", "potters"));
        recomendationsPlaces.add(new Recommendation("Royal Curd", "", "", "", "", "Take a time out and eat some of Bhaktapur's famous curd", "kingcurd"));
        recomendationsPlaces.add(new Recommendation("Side streets of Bhaktapur", "", "", "", "", "Spend hours wandering off down the side " +
                "\n streets of the city enjoying local handicraft stores, " +
                "\n artisans, local cafes and countless ancient buildings ", "alley"));
        recomendationsPlaces.add(new Recommendation("Taumadhi Square", "", "", "", "", "a huge square with the tallest temple in Nepal", "nightpolo"));

        recomendationsPlaces.add(new Recommendation("Dattatreya Square", "", "", "", "", "the oldest royal square in Nepal filled with things to see and do.", "datattreya"));

        recomendationsPlaces.add(new Recommendation("The Peacock Window", "", "", "", "", "One of Nepal's most precious woodcarvings and national art treasures - there are several craft stores along the same street worth visiting", "peacockw"));

        recommendationList.setValue(recomendationsPlaces);
    }

    private void loadHotelList() {

        List<Recommendation> recomendations = new ArrayList<>();
        recomendations.add(new Recommendation("Peacock Guest House", "4.5/5", "01-6611829", "Datattreya Square", "NPR 4031 per night", "On the main square stands this well run guesthouse with clean well decorated rooms in Newari design.", "peacock"));
        recomendations.add(new Recommendation("Milla Guest House", "5/5", " 9851027012/9851024137", "Datattreya Square", "NPR 7932 per night", "Highly rated hotel and have bery good reputation locally", "milla"));
        recomendations.add(new Recommendation("Thagu Chenn", "5/5", "01-6612043/9851152541", "Bhaktapur Durbar Square", "NPR 6643 per night", "Located just 50m away from UNESCO enlisted Bhaktapur Durbar Square, THAGU CHHEN, is a heritage boutique Bed and Breakfast " +
                "initiated as a step towards preserving culture with a sustainable and green approach", "thagu"));
        recomendations.add(new Recommendation("Shiva Guest House 1 & 2", "4.5/5", "01-6613912/01-6610740", "Bhaktapur Durbar Square", "NPR 1158 per  night", "Eco-Friendly Heritage Guest House. SHIVA GUEST HOUSE-pleasantly located in the heart of the Durbar Square is your address in Bhaktapur. Standing next to the Yaksheshwor" +
                " Temple the city's oldest edifice dating back to 1472 AD, " +
                "the guest house is where you can start your exploration trip from.", "shiva"));


        hotelList.setValue(recomendations);
    }




    public MutableLiveData<List<Result>> loadResult(){
        if(resultList == null){
            resultList = new MutableLiveData<>();
            loadResultList();
        }
        return resultList;
    }

    private void loadResultList() {
        new TextReader().execute(4);
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




    public void convertToGeoJson(){
        List<Result> results= resultList.getValue();
        JSONObject featureCollection = new JSONObject();
        int i = 0;
        try {
            featureCollection.put("type", "FeatureCollection");
            JSONObject properties = new JSONObject();
            properties.put("name", "ESPG:4326");
            JSONObject crs = new JSONObject();

            crs.put("type", "name");
            crs.put("properties", properties);
            featureCollection.put("crs", crs);


            JSONArray features = new JSONArray();

            for(Result result : results) {
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geometry = new JSONObject();

                JSONArray JSONArrayCoord = new JSONArray();

                JSONArrayCoord.put(0, result.getGeometry().getLocation().getLat());
                JSONArrayCoord.put(1, result.getGeometry().getLocation().getLng());
                geometry.put("type", "Point");
                geometry.put("coordinates", JSONArrayCoord);
                feature.put("geometry", geometry);

                JSONObject property = new JSONObject();
                property.put("name",result.getName());
                property.put("vicinity",result.getVicinity());

                feature.put("properties", property);
                features.put(i,feature);
                i++;

            }
            featureCollection.put("features", features);
        }catch (Exception e){
            Log.i("GEoJSON Nearby plaves", e.getLocalizedMessage());
        }

        Log.i("Nearby MOdel", featureCollection.toString());


    }
    public  class TextReader extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... integers) {
            String data = "";
            switch (integers[0]) {
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


            StringBuffer sBuffer =  new StringBuffer();
            Log.i("Datata",TYPE);

            InputStream is = (getApplication())
                    .getResources()
                    .openRawResource((getApplication()).getResources().getIdentifier(TYPE,"raw",getApplication().getPackageName()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            if(is != null){
                try{

                    while((data=reader.readLine()) != null){
                        sBuffer.append(data);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            NearbySearchData nearbySearchData = new Gson().fromJson(s, NearbySearchData.class);
            List<Result> results = nearbySearchData.getResults();
            List<Recommendation> recomendations = new ArrayList<>();

            for(Result result : results) {
                recomendations.add(new Recommendation(result.getName(), "4.5/5", "01-6611829", "Datattreya Square", "NPR 4031 per night", "On the main square stands this well run guesthouse with clean well decorated rooms in Newari design.", "peacock"));
            }

                hotelList.setValue(recomendations);


            resultList.setValue(results);

            super.onPostExecute(s);

        }
    }
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

        String video = "android.resource://"+getApplication().getPackageName()+"/raw/"+TYPE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( "android.resource://"+getApplication().getPackageName()+"/raw/"+TYPE, null, response -> {

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

