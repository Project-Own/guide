package com.example.guide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FoodDetail extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech t1;
    MotionLayout motionLayout;
    View view;

    TextView textView;
    Boolean contentTextFullscreen = false;
    Button b1;
    ImageView imageView;
    TextView titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_recycler_list_detail);

        motionLayout = findViewById(R.id.motionLayout);

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(this, drawer, this.getClass().getSimpleName()));

        view = findViewById(R.id.bgView);
        textView = findViewById(R.id.contentText);
        imageView = findViewById(R.id.coverImage);
        titleText = findViewById(R.id.titleTextView);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        assert b != null;
        String description = b.getString("description", "");
        String imageName = b.getString("image", "");
        String name = b.getString("name", "");
        textView.setText("sfdsfsdfdfsdf");
        titleText.setText(name);


        if (!imageName.equals("")) {
            Glide.with(this)
                    .load(getResources()
                            .getIdentifier(imageName, "drawable", getPackageName()))
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        }


        b1 = findViewById(R.id.botton);
        t1 = new TextToSpeech(getApplicationContext(), this, "com.google.android.tts");
        //        Set<String> a=new HashSet<>();
        //        a.add("female");//here you can give male if you want to select male voice.
        //        Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","UK"),400,200,true,a);
        //        t1.setVoice(v);
        //        t1.setSpeechRate(0.8f);
        onInit(0);
        b1.setOnClickListener(v -> {

            //Toast.makeText(getApplicationContext(), description,Toast.LENGTH_SHORT).show();
            t1.speak(description, TextToSpeech.QUEUE_FLUSH, null);
        });


        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                contentTextFullscreen = v == 1;
                String name = "rectangle";
                int trans = (int) (v * 10);
                switch (trans) {
                    case 0:
                        name += trans;
                        textView.setVerticalScrollBarEnabled(false);
                        textView.scrollTo(0, 0);
                        break;
                    case 1:
                        name += trans;
                        break;
                    case 2:
                        name += trans;
                        break;
                    case 3:
                        name += trans;
                        break;
                    case 4:
                        name += trans;
                        break;
                    case 5:
                        name += trans;
                        break;
                    case 6:
                        name += trans;
                        break;
                    case 7:
                        name += trans;
                        break;
                    case 8:
                        name += trans;
                        break;
                    case 9:
                        name += trans;
                        break;
                    case 10:
                        name += trans;

                        textView.setMovementMethod(new ScrollingMovementMethod());
                        break;
                    default:
                        name += trans;
                        break;

                }

                //   Log.i("Scroll animation", name);
                //   Log.i("Scroll animation", name);
                view.setBackground(getResources().getDrawable(getResources().getIdentifier(name, "drawable", getPackageName())));


            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);

        if (t1 != null) {
            t1.stop();
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (contentTextFullscreen) {
            motionLayout.transitionToStart();
        } else {
            assert t1 != null;
            t1.shutdown();

            super.onBackPressed();

        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Set<String> a = new HashSet<>();
            a.add("male");//here you can give male if you want to select male voice.
//            Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
            Voice v = new Voice("en-us-x-sfg#male_2-local", new Locale("en", "US"), 400, 200, true, a);
            t1.setVoice(v);
            t1.setSpeechRate(0.8f);

            // int result = t1.setLanguage(Locale.US);
            int result = t1.setVoice(v);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // btnSpeak.setEnabled(true);

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String message) {

        t1.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onPause() {

        super.onPause();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
