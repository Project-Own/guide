package com.example.guide.ui.places;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Places;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlacesViewModel extends AndroidViewModel {
    public PlacesViewModel(@NonNull Application application) {
        super(application);


    }

    private MutableLiveData<List<Places>> placesList;
    private List<Places> places;

    public MutableLiveData<List<Places>> loadPlaces(){
        if(placesList == null){
            placesList = new MutableLiveData<>();
            loadPlacesList();
        }
        return placesList;
    }

    private void loadPlacesList() {
        places = new ArrayList<>();
        TextReader txt= (TextReader) new TextReader().execute(new String[]{"Taumadhi Square","taumadhi","nyatapolo","27.671231", "85.429259"});
        txt= (TextReader) new TextReader().execute(new String[]{"Pottery Square","pottery","potterysquare","27.669873", "85.427808"});
        txt= (TextReader) new TextReader().execute(new String[]{"Durbar Square","durbar","durbarsquare","27.672073", "85.428095"});
        txt= (TextReader) new TextReader().execute(new String[]{"Datattreya Square","datattreya","datattreya","27.673421", "85.435387"});
        txt= (TextReader) new TextReader().execute(new String[]{"Ta: Pukhu","tapukhu","tapukhu","27.671980", "85.420511"});
        txt= (TextReader) new TextReader().execute(new String[]{"Biska Jatra","biska","bisket","27.672073", "85.428095"});
        txt= (TextReader) new TextReader().execute(new String[]{"Saparu","saparu","saparu","27.672073", "85.428095"});
        txt= (TextReader) new TextReader().execute(new String[]{"Holi","fagu","holi","27.672073", "85.428095"});

    }

    public  class TextReader extends AsyncTask<String[],Integer,String[]> {

        @Override
        protected String[] doInBackground(String[]... strings) {
            String data = "";
            String[] fileName = strings[0];
            StringBuffer sBuffer =  new StringBuffer();
            Log.i("Datata",fileName[1]);

            InputStream is = (getApplication())
                    .getResources()
                    .openRawResource((getApplication()).getResources().getIdentifier(fileName[1],"raw",getApplication().getPackageName()));

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

            String[] s= new String[]{fileName[0],sBuffer.toString(),fileName[2],fileName[3],fileName[4]};
            return s;
        }

        @Override
        protected void onPostExecute(String[] s) {

            places.add(new Places(s[0],s[1],s[2],Double.valueOf(s[3]),Double.valueOf(s[4])));
            placesList.setValue(places);
            super.onPostExecute(s);

        }
    }
}
