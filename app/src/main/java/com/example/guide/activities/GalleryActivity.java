package com.example.guide.activities;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.guide.adapters.GalleryAdapter;
import com.example.guide.adapters.InfiniteScrollAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {


    AppCompatActivity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> placesList;
    private SpringyAdapterAnimator springyAdapterAnimator;
    private GalleryAdapter adapter;
    private GalleryTagsListInterface galleryTagsListInterface;
    private boolean isModalOpen = false;
    int x_cord, y_cord, x, y;
    private int listPosition;
    private int windowwidth;
    private int screenCenter;
    private Bitmap blurredBitmap;
    List<Integer> lstImages = new ArrayList<>();

    private void initData() {
        lstImages.add(R.drawable.fuches);
        lstImages.add(R.drawable.katamari);
        lstImages.add(R.drawable.batti);
        lstImages.add(R.drawable.bodygaurd);
        lstImages.add(R.drawable.rubi);
        lstImages.add(R.drawable.siddha);
        lstImages.add(R.drawable.bhupa);
        lstImages.add(R.drawable.rubbles);
        lstImages.add(R.drawable.nightpolo);
        lstImages.add(R.drawable.vatsala);
        lstImages.add(R.drawable.children);
        lstImages.add(R.drawable.bhailakha);
        lstImages.add(R.drawable.datattraya);
        lstImages.add(R.drawable.ihimacha);
        lstImages.add(R.drawable.panas);
        lstImages.add(R.drawable.bigbell);
        lstImages.add(R.drawable.pottery1);
        //lstImages.add(R.drawable.bhairav);
        //lstImages.add(R.drawable.siddhilaxmi);
        lstImages.add(R.drawable.potters);
        //lstImages.add(R.drawable.wakupati);
        lstImages.add(R.drawable.grain);
       // lstImages.add(R.drawable.nyatapolo12);
       // lstImages.add(R.drawable.traditionalboy);
        lstImages.add(R.drawable.traditionalmusic);
        lstImages.add(R.drawable.khing);
        lstImages.add(R.drawable.peacockw);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        context = this;
        activity = GalleryActivity.this;


        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));

        recyclerView = findViewById(R.id.places_recyclerView);

        /*********************/
        initData();

        HorizontalInfiniteCycleViewPager pager = findViewById(R.id.horizontal_cycle);
        InfiniteScrollAdapter infiAdapter = new InfiniteScrollAdapter(lstImages, getBaseContext());
        pager.setAdapter(infiAdapter);



        /*********************/




        placesList = new ArrayList<>();
//        placesList.add(new Places("Taumadhi Square\n","nyatapolo"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "nirajan"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "pressure"));


        galleryTagsListInterface = new GalleryTagsListInterface() {
            @Override
            public void onTagClicked(String tagName, String description, int position) {

                listPosition = position;
                pager.setCurrentItem(position);
            }
        };

        adapter = new GalleryAdapter(placesList, recyclerView, context, activity, galleryTagsListInterface);

        placesList.add(new Places("", "Two boys in ritual attire", "fuches"));
        placesList.add(new Places("", "Puppet of the Kumari(Living Goddess)", "katamari"));
        placesList.add(new Places("", "Aarati", "batti"));
        placesList.add(new Places("", "Bodygaurd at service of Dattatraya", "bodygaurd"));
        placesList.add(new Places("", "Girl in traditional newari dress", "rubi"));
        placesList.add(new Places("", "Siddha Pukhu", "siddha"));
        placesList.add(new Places("", "Statue of Bhupatindra Malla", "bhupa"));
        placesList.add(new Places("", "Artifacts at rubbles after 2015 earthquake", "rubbles"));
        placesList.add(new Places("", "Nyatapolo at night", "nightpolo"));
        placesList.add(new Places("", "Wood carving at Vatsala Temple", "vatsala"));
        placesList.add(new Places("", "Children in traditional newa attire", "children"));
        placesList.add(new Places("", "Chariot of Bhairavnath with temple of its own in background", "bhailakha"));
        placesList.add(new Places("", "Dattatraya captured", "datattraya"));
        placesList.add(new Places("", "Newa girl dressed up for ritual called (ihi)", "ihimacha"));
        placesList.add(new Places("", "Sukunda", "panas"));
        placesList.add(new Places("", "Big bell of Bhaktapur with statue of Bhupatindra Malla in background", "bigbell"));
        placesList.add(new Places("", "Pottery in making", "pottery1"));
        //placesList.add(new Places("", "Renovated Bhairav temple at taumadhi", "bhairav"));
       // placesList.add(new Places("", "Siddhi Laxmi temple at Durbar Square", "siddhilaxmi"));
        placesList.add(new Places("", "Potters Square at noon", "potters"));
      //  placesList.add(new Places("", "Wakupati Narayan Temple near Dattatreya", "wakupati"));
        placesList.add(new Places("", "Woman threshing grain in traditional way", "grain"));
        //placesList.add(new Places("", "The perfect shot", "nyatapolo12"));
        //placesList.add(new Places("", "Cuteness Overloaded", "traditionalboy"));
        placesList.add(new Places("", "Hymn of the Weekend", "traditionalmusic"));
        placesList.add(new Places("", "Music in the air", "khing"));
        placesList.add(new Places("", "The beauty", "peacockw"));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        //     mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);





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

    public class TextReader extends AsyncTask<String[], Integer, String[]> {

        @Override
        protected String[] doInBackground(String[]... strings) {
            String data = "";
            String[] fileName = strings[0];
            StringBuffer sBuffer = new StringBuffer();
            Log.i("Datata", fileName[1]);

            InputStream is = getApplicationContext()
                    .getResources()
                    .openRawResource(getApplicationContext().getResources().getIdentifier(fileName[1], "raw", getPackageName()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            if (is != null) {
                try {

                    while ((data = reader.readLine()) != null) {
                        sBuffer.append(data);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String[] s = new String[]{fileName[0], sBuffer.toString(), fileName[2]};
            return s;
        }

        @Override
        protected void onPostExecute(String[] s) {
            placesList.add(new Places(s[0], s[1], s[2]));
            adapter.notifyDataSetChanged();

            super.onPostExecute(s);

        }
    }


}
