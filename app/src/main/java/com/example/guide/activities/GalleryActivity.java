package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.BlurBuilder;
import com.example.guide.Modal.Places;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.example.guide.adapters.GalleryAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {


    Activity activity;
    Context context;
    private RecyclerView recyclerView;
    private List<Places> placesList;
    private SpringyAdapterAnimator springyAdapterAnimator;
    private GalleryAdapter adapter;
    private GalleryTagsListInterface galleryTagsListInterface;
    private FrameLayout frameLayout;
    private ImageView imageView;
    private Button button;
    private TextView textView;
    private boolean isModalOpen = false;
    int x_cord, y_cord, x, y;
    private int listPosition;
    private int windowwidth;
    private int screenCenter;
    private Bitmap blurredBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        context = this;
        activity = GalleryActivity.this;

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(context, drawer, this.getClass().getSimpleName()));


        recyclerView = findViewById(R.id.places_recyclerView);
        frameLayout = findViewById(R.id.modalContainer);
        imageView = findViewById(R.id.modalImage);
        button = findViewById(R.id.modalButton);
        textView = findViewById(R.id.modalText);


        windowwidth = getWindowManager().getDefaultDisplay().getWidth();

        screenCenter = windowwidth / 2;


        placesList = new ArrayList<>();
//        placesList.add(new Places("Taumadhi Square\n","nyatapolo"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "nirajan"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "pressure"));


        galleryTagsListInterface = new GalleryTagsListInterface() {
            @Override
            public void onTagClicked(String tagName, String description, int position) {

                listPosition = position;
                loadModalPhoto(description, position);
            }
        };

        adapter = new GalleryAdapter(placesList, recyclerView, context, activity, galleryTagsListInterface);

        placesList.add(new Places("", "Two boys in ritual attire", "fuches"));
        placesList.add(new Places("", "Puppet of the Kumari(Living Goddess)", "katamari"));
        placesList.add(new Places("", "", "batti"));
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
        placesList.add(new Places("", "Renovated Bhairav temple at taumadhi", "bhairav"));
        placesList.add(new Places("", "Siddhi Laxmi temple at Durbar Square", "siddhilaxmi"));
        placesList.add(new Places("", "Potters Square at noon", "potters"));
        placesList.add(new Places("", "Wakupati Narayan Temple near Dattatreya", "wakupati"));
        placesList.add(new Places("", "Woman threshing grain in traditional way", "grain"));
        placesList.add(new Places("", "The perfect shot", "nyatapolo12"));
        placesList.add(new Places("", "Cuteness Overloaded", "traditionalboy"));
        placesList.add(new Places("", "Hymn of the Weekend", "traditionalmusic"));
        placesList.add(new Places("", "Music in the air", "khing"));


        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.siddha);
        blurredBitmap = BlurBuilder.blur(context, bitmap);
        frameLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));

        imageView.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x_cord = (int) event.getRawX();
                y_cord = (int) event.getRawY();

                imageView.setX(0);
                imageView.setY(0);
                imageView.setRotation((float) 0);


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = (int) event.getX();
                        y = (int) event.getY();


                        Log.v("On touch", x + " " + y);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        x_cord = (int) event.getRawX();
                        // smoother animation.
                        y_cord = (int) event.getRawY();

                        imageView.setX(x_cord - x);
                        imageView.setY(y_cord - y);


                        if (x_cord >= screenCenter) {
                            imageView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 64)));
                            if (x_cord > (screenCenter + (screenCenter / 2))) {
//                                if(!isLoading) {
//                                    isLoading = true;
//                                    swipeRight();
//                                }
                            }


                        } else {
                            // rotate image while moving
                            imageView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 64)));
                            if (x_cord < (screenCenter / 2)) {
//                                if(!isLoading){
//                                    isLoading = true;
//                                    swipeLeft();
//                                }
                            }


                        }

                        break;
                }


                return super.onTouch(v, event);

            }

            @Override
            public void onSwipeRight() {
                if (listPosition - 1 >= 0) {
                    listPosition--;
                    loadModalPhoto(placesList.get(listPosition).getDescription(), listPosition);
                } else {
                    Toast.makeText(activity, "Start of Gallery Reached", Toast.LENGTH_SHORT).show();
                }
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                if (listPosition < placesList.size() - 1) {
                    listPosition++;
                    loadModalPhoto(placesList.get(listPosition).getDescription(), listPosition);
                } else {
                    Toast.makeText(activity, "End of Gallery Reached", Toast.LENGTH_SHORT).show();
                }
                super.onSwipeLeft();
            }


        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeModal();
            }
        });


    }

    private void loadModalPhoto(String description, int position) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(context.getResources()
                        .getIdentifier(placesList.get(position).getImage(), "drawable", context.getPackageName()))
                .error(R.drawable.nirajan)
                .override(1000, 2000)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        isModalOpen = true;
                        frameLayout.setAlpha(0f);
                        //frameLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
                        imageView.setImageBitmap(resource);


                        textView.setText(description);
                        frameLayout.setVisibility(View.VISIBLE);

                        frameLayout.animate()
                                .alpha(1f)
                                .setDuration(500)
                        ;

                        frameLayout.setClickable(true);


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }


    public void closeModal() {

        frameLayout.animate()
                .alpha(0f)
                .setDuration(500)
        ;
        recyclerView.setLayoutFrozen(false);
        frameLayout.setClickable(false);
        frameLayout.setVisibility(View.GONE);
        isModalOpen = false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isModalOpen) {
            closeModal();

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
