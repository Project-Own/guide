package com.example.guide.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.Model.Home;
import com.example.guide.R;
import com.example.guide.lib.SpringAnimationType;
import com.example.guide.lib.SpringyAnimator;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.AboutViewHolder> {
    private List<Home> homeList;
    private Context context;
    private int aClass;
    private NavController navController;
    public HomeAdapter(List<Home> homeList, Context context) {
        this.context = context;
        this.homeList = homeList;
    }
    public HomeAdapter(List<Home> homeList, Context context, NavController navController) {
        this.context = context;
        this.homeList = homeList;
        this.navController = navController;
    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_home_recyler_view_list, parent, false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        holder.button.setText(homeList.get(position).getName());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(homeList.get(position).getImage(), "drawable", context.getPackageName()))
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.button.setForeground(resource);
                        }

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                })
        ;

        holder.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 85, 15, 0.8f, 1);

                scaleY.startSpring(holder.button);



                return false;
            }

        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        aClass = R.id.action_nav_home_to_nav_map;
                        break;
                    case 4:
                        aClass = R.id.action_nav_home_to_nav_weather;
                        break;
                    case 2:
                        aClass = R.id.action_nav_home_to_nav_place;
                        break;
                    case 3:
                        aClass = R.id.action_nav_home_to_nav_food;
                        break;
                    case 5:
                        aClass = R.id.action_nav_home_to_nav_calendar;
                        break;
                    case 1:
                        aClass = R.id.action_nav_home_to_nav_currency;
                        break;
                    case 6:
                        aClass = R.id.action_nav_home_to_nav_translator;
                        break;
                    case 7:
                        aClass = R.id.action_nav_home_to_nav_gallery;
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navController.navigate(aClass);
                    }
                }, 50);
            }
        });




    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    class AboutViewHolder extends RecyclerView.ViewHolder {

        Button button;

        AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.recycleButton);
        }
    }


}
