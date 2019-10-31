package com.example.guide.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.BlurBuilder;
import com.example.guide.Modal.Places;
import com.example.guide.R;
import com.example.guide.adapters.GalleryAdapter;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        context = this;
        activity = GalleryActivity.this;

        recyclerView = findViewById(R.id.places_recyclerView);
        frameLayout = findViewById(R.id.modalContainer);
        imageView = findViewById(R.id.modalImage);
        button = findViewById(R.id.modalButton);
        textView = findViewById(R.id.modalText);

        placesList = new ArrayList<>();
//        placesList.add(new Places("Taumadhi Square\n","nyatapolo"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "nirajan"));
//        placesList.add(new Places("Bhaktapur Durbar Square", "pressure"));


        galleryTagsListInterface = new GalleryTagsListInterface() {
            @Override
            public void onTagClicked(String tagName, String description, int position) {

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
                                Bitmap blurredBitmap = BlurBuilder.blur(context, resource);
                                frameLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));

                                imageView.setImageBitmap(resource);

                                textView.setText(description);
                                frameLayout.setVisibility(View.VISIBLE);

                                frameLayout.animate()
                                        .alpha(1f)
                                        .setDuration(500)
                                ;

                                frameLayout.setClickable(true);
                                recyclerView.setLayoutFrozen(true);

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

            }
        };

        adapter = new GalleryAdapter(placesList, recyclerView, context, activity, galleryTagsListInterface);

        placesList.add(new Places("", "Two boys in ritual attire", "fuches"));
        placesList.add(new Places("", "Puppet of the Kumari(Living Goddess)", "katamari"));
        placesList.add(new Places("", "", "batti"));
        placesList.add(new Places("", "Bodygaurd at service of Dattatraya", "bodygaurd"));
        placesList.add(new Places("", "newa girl in traditional dress", "rubi"));
        placesList.add(new Places("", "Siddha Pukhu", "siddha"));
        placesList.add(new Places("", "Statue of Bhupatindra Malla", "bhupa"));
        placesList.add(new Places("", "Artifacts at rubbles after 2015 earthquake", "rubbles"));
        placesList.add(new Places("", "Nyatapolo at night", "nightpolo"));
        placesList.add(new Places("", "Wood carving at Vatsala Temple", "vatsala"));
        placesList.add(new Places("", "Newa children in traditional attire", "children"));
        placesList.add(new Places("", "Chariot of Bhairavnath with temple of its own in background", "bhailakha"));
        placesList.add(new Places("", "Dattatraya captured", "datattraya"));
        placesList.add(new Places("", "Newa girl dressed up for ritual called (ihi)", "ihimacha"));
        placesList.add(new Places("", "Sukunda", "panas"));
        placesList.add(new Places("", "Big bell of Bhaktapur with statue of Bhupatindra Malla in background", "bigbell"));
        placesList.add(new Places("", "Pottery in making", "pottery1"));
       // placesList.add(new Places("", "", "weather"));
       // placesList.add(new Places("", "", "usd"));
       // placesList.add(new Places("", "", "npr"));
       // placesList.add(new Places("", "", "weather"));

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeModal();
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
        isModalOpen = false;
    }


    @Override
    public void onBackPressed() {
        if (isModalOpen) {
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
