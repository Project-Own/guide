package com.example.guide.ui.map;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.Model.NearbySearch.NearbySearchData;
import com.example.guide.Model.NearbySearch.Result;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private Bitmap icon;

    public MapViewModel(@NonNull Application application) {
        super(application);
        userLocation = "27.655032,85.094533";

    }

    private String searchUrl;
    private String TYPE;
    private String RADIUS = "500";
    private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    private String userLocation;
    private MutableLiveData<List<MarkerOptions>> resultList;
    private MutableLiveData<String[]> arrayAdapterString;
    private boolean isAtm = true;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    private String iconName = "cafe-15";



    public MutableLiveData<List<MarkerOptions>> loadResult(){
        if(resultList == null){
            resultList = new MutableLiveData<>();
            loadResultList();
        }
        return resultList;
    }

    private void loadResultList() {
        new TextReader().execute(0);
    }
    public void loadResultList(int selection) {
        new TextReader().execute(selection);
    }



    public MutableLiveData<String[]> loadSpinnerData(){
        if(arrayAdapterString == null){
            arrayAdapterString = new MutableLiveData<String[]>();
            loadSpinner();
        }
        return arrayAdapterString;
    }

    private void loadSpinner() {
        String[] list = {"None","ATM", "Bank", "Cafe", "Hindu Temple",
                "Hospital", "Lodging", "Museum", "Pharmacy",
                "Police Station","Restaurant", "Travel Agency", "Ticket Counter", "Toilet"
        };
        arrayAdapterString.setValue(list);

    }

    public void spinnerTypeSelected(AdapterView<?> parent, View view, int position, long id) {
        new TextReader().execute(position);

    }




    public  class TextReader extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... integers) {
            String data = "";
            switch (integers[0]) {
                case 0:
                    TYPE = "none";
                    return TYPE;
                case 1:
                    TYPE = "atm";
                    break;
                case 2:
                    TYPE = "bank";
                    break;
                case 3:
                    TYPE = "cafe";
                    break;
                case 4:
                    TYPE = "hindu_temple";
                    break;
                case 5:
                    TYPE = "hospital";
                    break;
                case 6:
                    TYPE = "lodging";
                    break;
                case 7:
                    TYPE = "museum";
                    break;
                case 8:
                    TYPE = "pharmacy";
                    break;
                case 9:
                    TYPE = "police";
                    break;
                case 10:
                    TYPE = "restaurant";
                    break;
                case 11:
                    TYPE = "travel_agency";
                    break;

                case 12:
                    TYPE = "ticket_counter";
                    return TYPE;
                case 13:
                    TYPE = "toilet";
                    return TYPE;

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
           switch (s) {
               case "none":
                   resultList.setValue(null);
                   break;
               case "toilet":
                   placeBathroomMarker();
                   break;
               case "ticket_counter":
                   placeCounterMarker();
                   break;
                   default:
               NearbySearchData nearbySearchData = new Gson().fromJson(s, NearbySearchData.class);
               List<Result> results = nearbySearchData.getResults();
               List<MarkerOptions> markerOptionsList = new ArrayList<>();

                       Glide.with(getApplication()).asBitmap().load(getApplication().getResources()
                               .getIdentifier(TYPE, "drawable", getApplication().getPackageName()))
                               .fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
                           @Override
                           public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                               if (resource != null) {
                                   //  Bitmap circularBitmap = getRoundedCornerBitmap(bitmap, 150);
                                   //   BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(changeBitmapColor(resource,i));
                                   icon = resource;
                                   for (Result result : results) {

                                       double lat = result.getGeometry().getLocation().getLat();
                                       double lng = result.getGeometry().getLocation().getLng();



                                       MarkerOptions markerOptions = new MarkerOptions()
                                               .position(new LatLng(lat, lng))
                                               .title(result.getName())
                                               .snippet("Vicinity :" + result.getVicinity());

                                       markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));

                                       markerOptionsList.add(markerOptions);
                                   }
                                   resultList.setValue(markerOptionsList);

                               }
                           }

                           @Override
                           public void onLoadCleared(@Nullable Drawable placeholder) {

                           }

                       });

           }
            super.onPostExecute(s);

        }
    }


    private void placeBathroomMarker() {

        List<MarkerOptions> toiletList = new ArrayList<>();



        Glide.with(getApplication()).asBitmap().load(getApplication().getResources()
                .getIdentifier(TYPE, "drawable", getApplication().getPackageName()))
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (resource != null) {
                    //  Bitmap circularBitmap = getRoundedCornerBitmap(bitmap, 150);
                    //   BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(changeBitmapColor(resource,i));
                    icon = resource;

                    toiletList.add(new MarkerOptions()
                            .position(new LatLng(27.673527, 85.438918))
                            .title("Chyamashing Public Toilet") .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Chyamasing"));
                    toiletList.add(new MarkerOptions()
                            .position(new LatLng(27.673812, 85.435212))
                            .title("Dattatry Public Toilet") .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Dattatray"));
                    toiletList.add(new MarkerOptions()
                            .position(new LatLng(27.672618, 85.428886))
                            .title("Durbar Square Public Toilet") .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Durbar Square"));
                    toiletList.add(new MarkerOptions()
                            .position(new LatLng(27.668656, 85.427561))
                            .title("Pottery Public Toilet") .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Pottery Square"));
                    resultList.setValue(toiletList);

                }


            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

        });

    }

    public void placeCounterMarker(){
        List<MarkerOptions> counterList = new ArrayList<>();

        Glide.with(getApplication()).asBitmap().load(getApplication().getResources()
                .getIdentifier(TYPE, "drawable", getApplication().getPackageName()))
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (resource != null) {
                    //  Bitmap circularBitmap = getRoundedCornerBitmap(bitmap, 150);
                    //   BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(changeBitmapColor(resource,i));
                    icon = resource;

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.667747, 85.427190))
                            .title("Tourist Ticket Counter")
                            .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Pottery Square"))

                    ;

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.667822, 85.424338))
                            .title("Tourist Ticket Counter")
                            .icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Near Barahi Temple"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.671592,85.423147))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Siddha Pukhu"));


                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.672382,85.427373))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Durbar Square"));


                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.674380,85.427993))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Tourist Bus Park"));


                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.675298,85.430040))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Byasi"));


                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.675185,85.431792))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Thalachhen Tole"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.676077,85.431792))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Mahalaxmi"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.676542,85.435062))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Mahalaxmi ko Mathi"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.675890,85.436637))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Kwathandau"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.673438,85.438294))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Chyamhasingh"));

                    counterList.add(new MarkerOptions()
                            .position(new LatLng(27.668201,85.431662))
                            .title("Tourist Ticket Counter").icon(BitmapDescriptorFactory.fromBitmap(icon))
                            .snippet("Vicinity :" + "Bhelukhel"));

                    resultList.setValue(counterList);


                }


            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

        });




    }
}
