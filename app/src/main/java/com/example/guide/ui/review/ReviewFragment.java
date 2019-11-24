package com.example.guide.ui.review;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.R;
import com.firebase.client.Firebase;

public class ReviewFragment extends Fragment {
    private Firebase firebase;

    private ReviewViewModel mViewModel;
    RatingBar ratingBar;
    Button button;
    EditText name;
    EditText email;
    EditText review;
    EditText getReview;




    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_layout, container, false);

        name=view.findViewById(R.id.editText);
        email=view.findViewById(R.id.editText3);
        review=view.findViewById(R.id.editText4);
        getReview=view.findViewById(R.id.editText5);
        button= view.findViewById(R.id.button);
        ratingBar=view.findViewById(R.id.rating1);
        Firebase.setAndroidContext(getContext());
        String uniqueID= Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        firebase=new Firebase("https://review-ff940.firebaseio.com/Users"+ uniqueID);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(getContext(),"Stars: ", Toast.LENGTH_SHORT).show();
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitfeedback();
            }
        });

        mViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        // TODO: Use the ViewModel
    }

    public void submitfeedback(){
        String rating=Integer.toString((int) ratingBar.getRating());
        String username=name.getText().toString();
        String useremail=email.getText().toString();
        String userreview=review.getText().toString();
        String  userreview1=getReview.getText().toString();
        Firebase reusername=firebase.child("name");

        reusername.setValue(username);
        if(username.isEmpty()){
            name.setError("this field is required");
            button.setEnabled(false);
        }
        else{
            name.setError(null);
            button.setEnabled(true);
        }
        Firebase reuseremail=firebase.child("email");
        reuseremail.setValue(useremail);
        if(useremail.isEmpty()){
            email.setError("this field is required");
            button.setEnabled(false);
        }
        else{
            email.setError(null);
            button.setEnabled(true);
        }
        Firebase reuserreview=firebase.child("review on bhaktapur");
        reuserreview.setValue(userreview);
        if(userreview.isEmpty()){
            review.setError("this field is required");
            button.setEnabled(false);
        }
        else{
            review.setError(null);
            button.setEnabled(true);
        }
        Firebase reuserreview1=firebase.child("review on app");
        reuserreview1.setValue(userreview1);
        if(userreview1.isEmpty()){
            getReview.setError("this field is required");
            button.setEnabled(false);
        }
        else{
            getReview.setError(null);
            button.setEnabled(true);
        }


        Firebase rerating=firebase.child("rating");
        rerating.setValue(rating);
        if(userreview1.isEmpty()){
            getReview.setError("Rating is required");
            button.setEnabled(false);
        }
        else{
            getReview.setError(null);
            button.setEnabled(true);
        }




    }


}
