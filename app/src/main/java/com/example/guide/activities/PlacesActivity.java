package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Places;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.PlacesAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> placesList;
    private SpringyAdapterAnimator springyAdapterAnimator;
    private PlacesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = PlacesActivity.this;


        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));

        recyclerView = findViewById(R.id.places_recyclerView);
        placesList = new ArrayList<>();
//        placesList.add(new Places("Taumadhi Square\n","nyatapolo"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "nirajan"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "pressure"));

        adapter = new PlacesAdapter(placesList, recyclerView, context, activity);

        TextReader txt= (TextReader) new TextReader().execute(new String[]{"Taumadhi Square","taumadhi","nyatapolo"});
         txt= (TextReader) new TextReader().execute(new String[]{"Pottery Square","pottery","potterysquare"});
         txt= (TextReader) new TextReader().execute(new String[]{"Durbar Square","durbar","durbarsquare"});
        txt= (TextReader) new TextReader().execute(new String[]{"Datattreya Square","datattreya","datattreya"});
        txt= (TextReader) new TextReader().execute(new String[]{"Ta: Pukhu","tapukhu","tapukhu"});
        txt= (TextReader) new TextReader().execute(new String[]{"Biska Jatra","biska","bisket"});
        txt= (TextReader) new TextReader().execute(new String[]{"Saparu","saparu","saparu"});
        txt= (TextReader) new TextReader().execute(new String[]{"Holi","fagu","holi"});




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);

    }

    public  class TextReader extends AsyncTask<String[],Integer,String[]> {

        @Override
        protected String[] doInBackground(String[]... strings) {
            String data = "";
            String[] fileName = strings[0];
            StringBuffer sBuffer =  new StringBuffer();
            Log.i("Datata",fileName[1]);

            InputStream is = getApplicationContext()
                    .getResources()
                    .openRawResource(getApplicationContext().getResources().getIdentifier(fileName[1],"raw",getPackageName()));

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

            String[] s= new String[]{fileName[0],sBuffer.toString(),fileName[2]};
            return s;
        }

        @Override
        protected void onPostExecute(String[] s) {
            placesList.add(new Places(s[0],s[1],s[2]));
            adapter.notifyDataSetChanged();

            super.onPostExecute(s);

        }
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
