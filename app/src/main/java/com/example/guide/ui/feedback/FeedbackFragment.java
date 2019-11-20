package com.example.guide.ui.feedback;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.R;
import com.firebase.client.Firebase;

public class FeedbackFragment extends Fragment {
    private Firebase firebase;
    private EditText email;
    private EditText suggestion;
    private FeedbackViewModel mViewModel;
   private Button button;
    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        email = view.findViewById(R.id.email);
        suggestion = view.findViewById(R.id.suggestion);
        button = view.findViewById(R.id.sendFeedback);
        Firebase.setAndroidContext(getContext());

        String uniqueID =  Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        firebase=new Firebase("https://feedback-2eb51.firebaseio.com/Users"+ uniqueID);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbacksent();
            }
        });
        mViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        // TODO: Use the ViewModel
    }

    public void feedbacksent() {
        String useremail=email.getText().toString();
        String usersuggestion=suggestion.getText().toString();
        Firebase reusername=firebase.child("email");
        reusername.setValue(useremail);
        Firebase resuggestion=firebase.child("suggestion");
        resuggestion.setValue(usersuggestion);


    }

}
