package com.example.guide.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.guide.Modal.Currency.CurrencyData;
import com.example.guide.Modal.Forex.ForexData;
import com.example.guide.Modal.Forex.Rates;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.ForexAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ForexActivity extends AppCompatActivity {
    String supportedPairs = "AUDUSD,EURGBP,EURUSD,GBPUSD,NZDUSD,USDAED,USDAFN,USDALL,USDAMD,USDANG,USDAOA,USDARS,USDATS,USDAUD,USDAWG,USDAZM,USDAZN,USDBAM,USDBBD,USDBDT,USDBEF,USDBGN,USDBHD,USDBIF,USDBMD,USDBND,USDBOB,USDBRL,USDBSD,USDBTN,USDBWP,USDBYN,USDBYR,USDBZD,USDCAD,USDCDF,USDCHF,USDCLP,USDCNH,USDCNY,USDCOP,USDCRC,USDCUC,USDCUP,USDCVE,USDCYP,USDCZK,USDDEM,USDDJF,USDDKK,USDDOP,USDDZD,USDEEK,USDEGP,USDERN,USDESP,USDETB,USDEUR,USDFIM,USDFJD,USDFKP,USDFRF,USDGBP,USDGEL,USDGGP,USDGHC,USDGHS,USDGIP,USDGMD,USDGNF,USDGRD,USDGTQ,USDGYD,USDHKD,USDHNL,USDHRK,USDHTG,USDHUF,USDIDR,USDIEP,USDILS,USDIMP,USDINR,USDIQD,USDIRR,USDISK,USDITL,USDJEP,USDJMD,USDJOD,USDJPY,USDKES,USDKGS,USDKHR,USDKMF,USDKPW,USDKRW,USDKWD,USDKYD,USDKZT,USDLAK,USDLBP,USDLKR,USDLRD,USDLSL,USDLTL,USDLUF,USDLVL,USDLYD,USDMAD,USDMDL,USDMGA,USDMGF,USDMKD,USDMMK,USDMNT,USDMOP,USDMRO,USDMRU,USDMTL,USDMUR,USDMVR,USDMWK,USDMXN,USDMYR,USDMZM,USDMZN,USDNAD,USDNGN,USDNIO,USDNLG,USDNOK,USDNPR,USDNZD,USDOMR,USDPAB,USDPEN,USDPGK,USDPHP,USDPKR,USDPLN,USDPTE,USDPYG,USDQAR,USDROL,USDRON,USDRSD,USDRUB,USDRWF,USDSAR,USDSBD,USDSCR,USDSDD,USDSDG,USDSEK,USDSGD,USDSHP,USDSIT,USDSKK,USDSLL,USDSOS,USDSPL,USDSRD,USDSRG,USDSTD,USDSTN,USDSVC,USDSYP,USDSZL,USDTHB,USDTJS,USDTMM,USDTMT,USDTND,USDTOP,USDTRL,USDTRY,USDTTD,USDTVD,USDTWD,USDTZS,USDUAH,USDUGX,USDUSD,USDUYU,USDUZS,USDVAL,USDVEB,USDVEF,USDVES,USDVND,USDVUV,USDWST,USDXAF,USDXAG,USDXAU,USDXBT,USDXCD,USDXDR,USDXOF,USDXPD,USDXPF,USDXPT,USDYER,USDZAR,USDZMK,USDZMW,USDZWD ";

    String forexUrl = "https://www.freeforexapi.com/api/live?pairs=" + supportedPairs;


    String currencyUrl = "https://free.currconv.com/api/v7/currencies?apiKey=" + "de548f61b8d4321b60ed";

    String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=Bhaktapur,np&APPID=" + "e98fff7661b64a5e994e6394560e74e9";

    String countryUrl = "https://free.currconv.com/api/v7/countries?apiKey=" + "de548f61b8d4321b60ed";

    String currencyConversionUserChoice = "USD_NPR";
    String currencyConversionUrl = "https://free.currconv.com/api/v7/convert?apiKey=de548f61b8d4321b60ed&q=" + currencyConversionUserChoice;

    private Context context = this;
    private RecyclerView recyclerView;
    private FrameLayout forexFrameLayout;
    private ProgressBar forexProgressBar;
    private ForexAdapter forexAdapter;
    private List<Object> forexList;
    private List<String> pairList;
    private EditText editText;

    private Map<String, String> countryNameMap;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex);

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(this, drawer, this.getClass().getSimpleName()));

        forexFrameLayout = findViewById(R.id.forexFrameLayout);
        recyclerView = findViewById(R.id.forexRecyclerView);
        //   forexProgressBar = findViewById(R.id.forexProgressBar);
        //   forexProgressBar.setVisibility(View.VISIBLE);
        editText=findViewById(R.id.Edit);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(Color.CYAN, Color.RED, Color.BLUE, Color.BLACK);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCurrencyData();
            swipeRefreshLayout.setRefreshing(false);
        });


        countryNameMap = new HashMap<>();


        getCurrencyData();


    }

    private List<String> filteredListPair = new ArrayList<>();
    private List<Object> filteredListForex = new ArrayList<>();

    private void filter(String text){
        filteredListPair.clear();
        filteredListForex.clear();

        for (Map.Entry<String, String> stringStringEntry : countryNameMap.entrySet()) {

            if (((Map.Entry) stringStringEntry).getValue().toString().toLowerCase().contains(text.toLowerCase())) {

                for (String forexPair : pairList) {
                    if (forexPair.contains(((Map.Entry) stringStringEntry).getKey().toString())) {
                        int index = pairList.indexOf(forexPair);
                        filteredListForex.add(forexList.get(index));
                        filteredListPair.add(pairList.get(index));
                    }
                }
            }

        }

        Log.i("Errororor",filteredListForex.toString());
        Log.i("ErrorororPair",filteredListPair.toString());
        Log.i("ErrorororPair",countryNameMap.toString());


        forexAdapter.filterList(filteredListForex,filteredListPair);

    }


    /************** FOREX Data Function************/

    public void getForexData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, forexUrl, null, response -> {


//                Log.i("Forex Data Function", countryNameMap.toString());
            forexAdapter = new ForexAdapter(forexList, pairList, countryNameMap, recyclerView, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(forexAdapter);


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());


                }
            });
            swipeRefreshLayout.setRefreshing(false);
            //forexProgressBar.setVisibility(View.INVISIBLE);

            //   Log.i("ForexApiDataCall", response.toString());

        }, error -> {

            TextView textView = new TextView(context);
            textView.setText(R.string.errorMessage);

            textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            forexFrameLayout.addView(textView);

            swipeRefreshLayout.setRefreshing(false);
            // forexProgressBar.setVisibility(View.INVISIBLE);

            Log.e("ForexApiDataCall", error.toString());
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

                    // parseNetworkResponse works on background thread and doesnot slow down UI Thread so parsing of data is done here for performance
                    // Here JsonString contains Json data
                    ForexData forexData = new Gson().fromJson(jsonString, ForexData.class);

                    forexList = new ArrayList<>();
                    pairList = new ArrayList<>();

                    Rates rates;

                    java.lang.reflect.Method ratesMethod;
                    try {
                        ratesMethod = ForexData.class.getMethod("getRates");

                        java.lang.reflect.Method execRatesMethod = forexData.getClass().getMethod(ratesMethod.getName());
                        rates = (Rates) execRatesMethod.invoke(forexData);

                        java.lang.reflect.Method[] methods = Rates.class.getMethods();
                        Object obj;
                        for (java.lang.reflect.Method method : methods) {
                            String name = method.getName();
                            if (name.startsWith("get") && !name.endsWith("Class")) {
                                assert rates != null;
                                java.lang.reflect.Method execMethod = rates.getClass().getMethod(name);
                                //  String className = "" + execMethod.getClass().getSimpleName();
                                String className = "com.example.guide.Modal.Forex.";
                                String classNameAdd = name.substring(3);
                                className += classNameAdd;

                                Class<?> c = Class.forName(className);

                                obj = c.cast(execMethod.invoke(rates));

                                if (obj != null) {
                                    //   message += obj.toString() + "\n\n";

//                                message += name +" " +name.substring(3)+" "+ obj.toString() +" "+execMethod.getReturnType()+ "\n\n";
                                    java.lang.reflect.Method[] objMethods = obj.getClass().getMethods();
                                    for (java.lang.reflect.Method objMethod : objMethods) {
                                        String objMethodName = objMethod.getName();
                                        if (objMethodName.equals("getRate")) {
                                            pairList.add(classNameAdd);
                                            forexList.add(objMethod.invoke(obj));

                                        }
                                    }
                                }
                            }
                        }


                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }


    /******************************Currency Function*******************************/

    /* Currency Data function*/
    public void getCurrencyData() {

        swipeRefreshLayout.setRefreshing(true);
        //forexProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currencyUrl, null, response -> {
            getForexData();
            //    Log.i("ForexActivity.class",response.toString());
        }, error -> Log.e("ForexActivity", error.toString())) {
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

                    CurrencyData currencyData = new Gson().fromJson(jsonString, CurrencyData.class);

                    com.example.guide.Modal.Currency.Results results;

                    ArrayList<Object> forexList = new ArrayList<>();


                    java.lang.reflect.Method resultsMethod;
                    try {
                        resultsMethod = CurrencyData.class.getMethod("getResults");

                        java.lang.reflect.Method execRatesMethod = currencyData.getClass().getMethod(resultsMethod.getName());
                        results = (com.example.guide.Modal.Currency.Results) execRatesMethod.invoke(currencyData);

                        //  message += results.toString() + " ";
                        java.lang.reflect.Method[] methods = com.example.guide.Modal.Currency.Results.class.getMethods();
                        Object obj;
                        for (java.lang.reflect.Method method : methods) {
                            String name = method.getName();
                            if (name.startsWith("get") && !name.endsWith("Class")) {
                                assert results != null;
                                java.lang.reflect.Method execMethod = results.getClass().getMethod(name);
                                //String className = "" + execMethod.getClass().getSimpleName();
                                String className = "com.example.guide.Modal.Currency.";
                                className += name.substring(3);
//                                Log.i("TorpeCurrency",className);
                                Class<?> c = Class.forName(className);

                                obj = c.cast(execMethod.invoke(results));

                                if (obj != null) {
                                    //    message += obj.toString() + "\n\n";

                                    //   message += name +" " +name.substring(3)+" "+ obj.toString() +" "+execMethod.getReturnType()+ "\n\n";
                                    String currencyName = "";
                                    String currencyId = "";
                                    java.lang.reflect.Method[] objMethods = obj.getClass().getMethods();
                                    for (java.lang.reflect.Method objMethod : objMethods) {
                                        String objMethodName = objMethod.getName();
                                        if (objMethodName.startsWith("get") && !objMethodName.endsWith("Class")) {
                                            if (objMethodName.equals("getCurrencyName")) {
                                                currencyName = (String) objMethod.invoke(obj);
                                            } else if (objMethodName.equals("getId")) {
                                                currencyId = (String) objMethod.invoke(obj);
                                            }
                                            //  message += objMethodName + ": "+ objMethod.invoke(obj).toString() + " " ;
                                        }
                                    }
                                    countryNameMap.put(currencyId, currencyName);
//                                   message += currencyId + " " + countryNameMap.get(currencyId) + "\n\n";
//                                   Log.i("ForexActivity",message);
//                                    message = "";
                                }
                            }
                        }


                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //   Log.i("Torpe","" + currencyData);


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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}

