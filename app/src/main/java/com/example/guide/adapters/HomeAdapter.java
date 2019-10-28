package com.example.guide.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.Modal.Home;
import com.example.guide.R;
import com.example.guide.activities.CalendarActivity;
import com.example.guide.activities.FoodActivity;
import com.example.guide.activities.ForexActivity;
import com.example.guide.activities.MapsActivity;
import com.example.guide.activities.PlacesActivity;
import com.example.guide.activities.WeatherActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.AboutViewHolder> {
    private List<Home> homeList;
    private Context context;
    private Class aClass;

    public HomeAdapter(List<Home> homeList, Context context) {
        this.context = context;
        this.homeList = homeList;

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
        holder.button.setOnClickListener(v -> {
            int viewNo = position;
            switch (viewNo) {
                case 0:
                    aClass = MapsActivity.class;
                    break;
                case 1:
                    aClass = WeatherActivity.class;
                    break;
                case 2:
                    aClass = PlacesActivity.class;
                    break;
                case 3:
                    aClass = FoodActivity.class;
                    break;
                case 4:
                    aClass = CalendarActivity.class;
                    break;
                case 5:
                    aClass = ForexActivity.class;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + viewNo);
            }
            Intent intent = new Intent(context, aClass);
            context.startActivity(intent);
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