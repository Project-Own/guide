package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Places;
import com.example.guide.R;
import com.example.guide.adapters.PlacesAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        context = this;
        activity = PlacesActivity.this;

        recyclerView = findViewById(R.id.places_recyclerView);
        placesList = new ArrayList<>();
        placesList.add(new Places("Taumadhi Square\n","nyatapolo"));
        placesList.add(new Places("Bhaktapur Durbar Square", "nirajan"));
        placesList.add(new Places("Bhaktapur Durbar Square", "pressure"));

        TextReader txt= (TextReader) new TextReader().execute(new String[]{"Taumadhi Square","taumadhi","nyatapolo"});
         txt= (TextReader) new TextReader().execute(new String[]{"Pottery Square","pottery","nyatapolo"});
         txt= (TextReader) new TextReader().execute(new String[]{"Durbar Square","durbar","nyatapolo"});

        PlacesAdapter adapter = new PlacesAdapter(placesList, recyclerView, context, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    public  class TextReader extends AsyncTask<String[],Integer,String[]> {

        @Override
        protected String[] doInBackground(String[]... strings) {
            String data = "";
            String fileName[] = strings[0];
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


            super.onPostExecute(s);

        }
    }
}
