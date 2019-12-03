package com.example.guide.ui.about;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.Users;
import com.example.guide.R;
import com.example.guide.adapters.AboutAdapter;

import java.util.List;

public class AboutFragment extends Fragment {

    private AboutViewModel mViewModel;
    private RecyclerView recycleView;
    RecyclerView.LayoutManager mLayoutManager;
    TextView textView;
    private List<Users> users;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_fragment, container, false);
        recycleView = v.findViewById(R.id.aboutRecyclerView);
        textView = v.findViewById(R.id.text13);
        mLayoutManager = new LinearLayoutManager(getContext());



        String text12 = "Pictures credit to Sunil Shilpakar and Uday Prajapati. You can find them at instagram: shilpee_ss udaya.rise";
        SpannableString ss = new SpannableString(text12);

        ClickableSpan clickableSpan1  = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
            }

            @Override
            public void onClick(@NonNull View widget) {
                clicked("https://www.instagram.com/shilpee_ss/");

            }
        };

        ClickableSpan clickableSpan2  = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
            }

            @Override
            public void onClick(@NonNull View widget) {

                clicked("https://www.instagram.com/udaya.rise/");
            }
        };

        ss.setSpan(clickableSpan1,87,97, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,103,112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }

     public void clicked(String url){
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setData(Uri.parse(url));
         startActivity(intent);
     }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);

        mViewModel.loadUser().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                AboutAdapter adapter = new AboutAdapter(users, getContext());
                recycleView.setAdapter(adapter);
                recycleView.setLayoutManager(mLayoutManager);

            }
        });
    }

}
