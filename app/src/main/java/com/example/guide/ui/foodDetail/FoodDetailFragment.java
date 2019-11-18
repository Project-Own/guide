package com.example.guide.ui.foodDetail;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.guide.R;
import com.github.florent37.shapeofview.shapes.ArcView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FoodDetailFragment extends Fragment implements TextToSpeech.OnInitListener {

    private FoodDetailViewModel mViewModel;
    TextToSpeech t1;
    MotionLayout motionLayout;
    View view;

    TextView textView;
    TextView titleText;
    Boolean contentTextFullscreen = false;
    Button b1;
    ImageView imageView;
    View divider;
    CardView cardView;
    ArcView arc;


    int shortAnimationDuration;

    public static FoodDetailFragment newInstance() {
        return new FoodDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_detail_fragment, container, false);

        motionLayout = v.findViewById(R.id.motionLayout);

        imageView = v.findViewById(R.id.coverImage);

        divider = v.findViewById(R.id.divider);
        view = v.findViewById(R.id.bgView);
        textView = v.findViewById(R.id.contentText);
        titleText = v.findViewById(R.id.titleTextView);
        arc = v.findViewById(R.id.myShape);
        cardView = v.findViewById(R.id.tq);

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
                        textView.setMovementMethod(null);
                        textView.setVerticalScrollBarEnabled(false);
                        //set the animation
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
                textView.scrollTo(0, 0);

                //   Log.i("Scroll animation", name);
                //   Log.i("Scroll animation", name);
                view.setBackground(getResources().getDrawable(getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
            }
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) { }
            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {          }
        });
        String description = getArguments().getString("description");
        String imageName = getArguments().getString("image");
        String name = getArguments().getString("name");


        textView.setText(description);
        titleText.setText(name);


        if (!imageName.equals("")) {
            Glide.with(this)
                    .load(getResources()
                            .getIdentifier(imageName, "drawable", getActivity().getPackageName()))
                    .fitCenter()
                    .into(imageView);

        }
        return v;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mViewModel = ViewModelProviders.of(this).get(FoodDetailViewModel.class);
        // TODO: Use the ViewModel
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



}
