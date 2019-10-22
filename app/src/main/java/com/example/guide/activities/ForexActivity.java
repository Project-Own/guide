package com.example.guide.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.guide.Modal.Forex.ForexData;
import com.example.guide.Modal.Forex.Rates;
import com.example.guide.R;
import com.example.guide.adapters.ForexAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class ForexActivity extends AppCompatActivity {
    String supportedPairs = "AUDUSD,EURGBP,EURUSD,GBPUSD,NZDUSD,USDAED,USDAFN,USDALL,USDAMD,USDANG,USDAOA,USDARS,USDATS,USDAUD,USDAWG,USDAZM,USDAZN,USDBAM,USDBBD,USDBDT,USDBEF,USDBGN,USDBHD,USDBIF,USDBMD,USDBND,USDBOB,USDBRL,USDBSD,USDBTN,USDBWP,USDBYN,USDBYR,USDBZD,USDCAD,USDCDF,USDCHF,USDCLP,USDCNH,USDCNY,USDCOP,USDCRC,USDCUC,USDCUP,USDCVE,USDCYP,USDCZK,USDDEM,USDDJF,USDDKK,USDDOP,USDDZD,USDEEK,USDEGP,USDERN,USDESP,USDETB,USDEUR,USDFIM,USDFJD,USDFKP,USDFRF,USDGBP,USDGEL,USDGGP,USDGHC,USDGHS,USDGIP,USDGMD,USDGNF,USDGRD,USDGTQ,USDGYD,USDHKD,USDHNL,USDHRK,USDHTG,USDHUF,USDIDR,USDIEP,USDILS,USDIMP,USDINR,USDIQD,USDIRR,USDISK,USDITL,USDJEP,USDJMD,USDJOD,USDJPY,USDKES,USDKGS,USDKHR,USDKMF,USDKPW,USDKRW,USDKWD,USDKYD,USDKZT,USDLAK,USDLBP,USDLKR,USDLRD,USDLSL,USDLTL,USDLUF,USDLVL,USDLYD,USDMAD,USDMDL,USDMGA,USDMGF,USDMKD,USDMMK,USDMNT,USDMOP,USDMRO,USDMRU,USDMTL,USDMUR,USDMVR,USDMWK,USDMXN,USDMYR,USDMZM,USDMZN,USDNAD,USDNGN,USDNIO,USDNLG,USDNOK,USDNPR,USDNZD,USDOMR,USDPAB,USDPEN,USDPGK,USDPHP,USDPKR,USDPLN,USDPTE,USDPYG,USDQAR,USDROL,USDRON,USDRSD,USDRUB,USDRWF,USDSAR,USDSBD,USDSCR,USDSDD,USDSDG,USDSEK,USDSGD,USDSHP,USDSIT,USDSKK,USDSLL,USDSOS,USDSPL,USDSRD,USDSRG,USDSTD,USDSTN,USDSVC,USDSYP,USDSZL,USDTHB,USDTJS,USDTMM,USDTMT,USDTND,USDTOP,USDTRL,USDTRY,USDTTD,USDTVD,USDTWD,USDTZS,USDUAH,USDUGX,USDUSD,USDUYU,USDUZS,USDVAL,USDVEB,USDVEF,USDVES,USDVND,USDVUV,USDWST,USDXAF,USDXAG,USDXAU,USDXBT,USDXCD,USDXDR,USDXOF,USDXPD,USDXPF,USDXPT,USDYER,USDZAR,USDZMK,USDZMW,USDZWD ";

    String forexUrl = "https://www.freeforexapi.com/api/live?pairs=" + supportedPairs;


    String currencyUrl = "https://free.currconv.com/api/v7/currencies?apiKey=" + "de548f61b8d4321b60ed";

    String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=Bhaktapur,np&APPID=" + "e98fff7661b64a5e994e6394560e74e9";

    String countryUrl = "https://free.currconv.com/api/v7/countries?apiKey=" + "de548f61b8d4321b60ed";

    String currencyConversionUserChoice = "USD_NPR";
    String currencyConversionUrl = "https://free.currconv.com/api/v7/convert?apiKey=de548f61b8d4321b60ed&q=" + currencyConversionUserChoice;


    private String searchApiKey = "AIzaSyDktfztr-u1kXk81mLP1_ZZkVzAMJLyizE";

    private String LOCATION = "-33.8670522,151.1957362";
    private String RADIUS = "1500";
    private String TYPE = "restaurant";
    private String KEYWORD = "cruise";

    private String searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LOCATION + "&radius=" + RADIUS + "&type=" + TYPE + "&keyword=" + KEYWORD + "&key=" + searchApiKey;

    private Context context = this;
    private RecyclerView recyclerView;
    private FrameLayout forexFrameLayout;
    private ProgressBar forexProgressBar;
    private ForexAdapter forexAdapter;
    private List<Object> forexList;
    private List<String> pairList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex);

        forexFrameLayout = findViewById(R.id.forexFrameLayout);
        recyclerView = findViewById(R.id.forexRecyclerView);
        forexProgressBar = findViewById(R.id.forexProgressBar);
        forexProgressBar.setVisibility(View.VISIBLE);


        getForexData();

    }

    /************** FOREX Data Function************/

    public void getForexData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, forexUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                forexAdapter = new ForexAdapter(pairList, forexList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(forexAdapter);

                forexProgressBar.setVisibility(View.INVISIBLE);

                Log.i("ForexApiDataCall", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                TextView textView = new TextView(context);
                textView.setText("Oops!! Please retry");

                textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                forexFrameLayout.addView(textView);

                forexProgressBar.setVisibility(View.INVISIBLE);

                Log.e("ForexApiDataCall", error.toString());
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

                    /********************************/
                    // parseNetworkResponse works on background thread and doesnot slow down UI Thread so parsing of data is done here for performance
                    // Here JsonString contains Json data
                    ForexData forexData = new Gson().fromJson(jsonString, ForexData.class);

                    forexList = new ArrayList<Object>();
                    pairList = new ArrayList<String>();

                    Rates rates;

                    java.lang.reflect.Method ratesMethod = null;
                    try {
                        ratesMethod = ForexData.class.getMethod("getRates");

                        java.lang.reflect.Method execRatesMethod = forexData.getClass().getMethod(ratesMethod.getName());
                        rates = (Rates) execRatesMethod.invoke(forexData);

                        java.lang.reflect.Method[] methods = Rates.class.getMethods();
                        Object obj;
                        for (java.lang.reflect.Method method : methods) {
                            String name = method.getName();
                            if (name.startsWith("get") && !name.endsWith("Class")) {
                                java.lang.reflect.Method execMethod = rates.getClass().getMethod(name);
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


                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    /********************************/

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


    /************** FOREX Data Function************/


}

