package com.example.guide.ui.weather;

import android.app.Application;
import android.util.Log;

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
import com.example.guide.Model.Weather.Weather;
import com.example.guide.Model.Weather.WeatherData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private String iconName = "10d";
    private String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=Bhaktapur,np&units=metric&APPID=" + "e98fff7661b64a5e994e6394560e74e9";
    private WeatherData weatherData;


    private MutableLiveData<WeatherData> mWeatherData;
    public MutableLiveData<Boolean> isLoading;
    public void onRefresh() {
        isLoading.setValue(true);
        getWeatherData();
    }
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        this.updatedAtText = new MutableLiveData<String>();
        this.temp = new MutableLiveData<String>();
        this.tempMin = new MutableLiveData<String>();
        this.tempMax = new MutableLiveData<String>();
        this.pressure = new MutableLiveData<String>();
        this.humidity = new MutableLiveData<String>();
        this.address = new MutableLiveData<String>();
        this.windSpeed = new MutableLiveData<String>();
        this.sunrise = new MutableLiveData<String>();
        this.sunset = new MutableLiveData<String>();
        this.status = new MutableLiveData<String>();
        this.isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<WeatherData> loadData(){
        isLoading.setValue(true);
        if(mWeatherData == null){
            mWeatherData = new MutableLiveData<WeatherData>();
            getWeatherData();
        }
        return mWeatherData;
    }

    public MutableLiveData<String> updatedAtText;
    public MutableLiveData<String> temp;
    public MutableLiveData<String> tempMin;
    public MutableLiveData<String> tempMax;
    public MutableLiveData<String> pressure;
    public MutableLiveData<String> humidity;
    public MutableLiveData<String> address;
    public MutableLiveData<String> windSpeed;
    public MutableLiveData<String> sunrise;
    public MutableLiveData<String> sunset;
    public MutableLiveData<String> status;

    public void getWeatherData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                mWeatherData.setValue(weatherData);
                updatedAtText.setValue("Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Integer.valueOf( weatherData.getDt().toString().toUpperCase()) * 1000L)));
                temp.setValue(weatherData.getMain().getTemp() + "°C");
                tempMin.setValue("Min Temp: " + weatherData.getMain().getTempMin() + "°C");
                tempMax.setValue("Max Temp: " + weatherData.getMain().getTempMax() + "°C");
                pressure.setValue( String.valueOf(weatherData.getMain().getPressure()));
                humidity.setValue(String.valueOf(weatherData.getMain().getHumidity()));
                address.setValue(weatherData.getName() + ", " + weatherData.getSys().getCountry());

                sunrise.setValue(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weatherData.getSys().getSunrise() * 1000L)));
                sunset.setValue(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weatherData.getSys().getSunset() * 1000L)));
                windSpeed.setValue(String.valueOf(weatherData.getWind().getSpeed()));
                //          crossfadeOnSuccess();

                String weatherDescription = "";
                for (Weather weather : weatherData.getWeather()) {
                    weatherDescription = weather.getDescription() + "\n";
                    iconName = weather.getIcon();
                }
                status.setValue(weatherDescription);

                isLoading.setValue(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
//                crossfadeOnFail();
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
                    weatherData = new Gson().fromJson(jsonString, WeatherData.class);

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
