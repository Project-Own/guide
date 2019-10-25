package com.example.guide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.bumptech.glide.Glide;
import com.example.guide.R;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PlacesDetail extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech t1;
    MotionLayout motionLayout;
    View view;

    TextView textView;
    Boolean contentTextFullscreen = false;
    Button b1;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_recycler_list_detail);

        motionLayout = findViewById(R.id.motionLayout);

        view = findViewById(R.id.bgView);
        textView = findViewById(R.id.contentText);
        imageView = findViewById(R.id.coverImage);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String description = b.getString("description", "");
        String name = b.getString("image","");
        textView.setText(description);


        if(!name.equals("")){
            Glide.with(this)
                    .load(getResources()
                            .getIdentifier(name, "drawable", getPackageName()))
                            .fitCenter()

                    .into(imageView);

        }


        b1=(Button)findViewById(R.id.botton);
            t1= new TextToSpeech(getApplicationContext(), (TextToSpeech.OnInitListener) this, "com.google.android.tts");
        Set<String> a=new HashSet<>();
        a.add("female");//here you can give male if you want to select male voice.
        Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","UK"),400,200,true,a);
        t1.setVoice(v);
        t1.setSpeechRate(0.8f);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = textView.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                if (v >= 0.95) {
                    view.setBackgroundResource(R.color.colorAccent);
                    contentTextFullscreen = true;
                } else {
                    view.setBackgroundResource(R.drawable.rectangle);
                    contentTextFullscreen = false;
                }

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
        if (contentTextFullscreen) {
            motionLayout.transitionToStart();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Set<String> a=new HashSet<>();
            a.add("male");//here you can give male if you want to select male voice.
//            Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
            Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
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

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
