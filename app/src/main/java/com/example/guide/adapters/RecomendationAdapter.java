package com.example.guide.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Model.Recommendation;
import com.example.guide.R;
import com.example.guide.lib.SpringAnimationType;
import com.example.guide.lib.SpringyAnimator;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.AboutViewHolder>  {
    private List<Recommendation> recomendation;
    private Context context;
    private SpringyAdapterAnimator mAnimator;
    private RecyclerView recyclerView;
    private NavController navController;

    public RecomendationAdapter(List<Recommendation> recomendation, Context context, RecyclerView recyclerView, NavController navController) {
        this.context=context;
        this.recomendation= recomendation;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_RIGHT);
        mAnimator.addConfig(85, 15);
        this.navController = navController;
    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_recomendation_list, parent, false);
        mAnimator.onSpringItemCreate(view);

        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        if (recomendation.get(position).getRating().equals("")) {
            holder.rating.setVisibility(View.GONE);
        }
        if (recomendation.get(position).getPhone().equals("")) {
            holder.phone.setVisibility(View.GONE);
        }
        if (recomendation.get(position).getVicinity().equals("")) {
            holder.vicinity.setVisibility(View.GONE);
        }
        if (recomendation.get(position).getPrice().equals("")) {
            holder.price.setVisibility(View.GONE);
        }
        holder.name.setText("Name: " + recomendation.get(position).getName());
        holder.rating.setText("Rating: " + recomendation.get(position).getRating());
        holder.price.setText("Price: " + recomendation.get(position).getPrice());
        holder.phone.setText("Phone No: " + recomendation.get(position).getPhone());
        holder.vicinity.setText("Vicinity: " + recomendation.get(position).getVicinity());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(recomendation.get(position).getPhoto(), "drawable", context.getPackageName()))
                .override(200, 200)
                .error(R.drawable.humidity)
                .into(holder.photo);

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 5, 10, 0.95f, 1);
                scaleY.setDelay(200);
                scaleY.startSpring(holder.itemView);

                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("name", recomendation.get(position).getName());
                bundle.putString("description", recomendation.get(position).getDescription());
                bundle.putString("image", recomendation.get(position).getPhoto());
                bundle.putDouble("lat", recomendation.get(position).getLat());
                bundle.putDouble("long", recomendation.get(position).getLang());
                navController.navigate(R.id.action_nav_home_to_placeDetailFragment3, bundle);
            }
        });

        mAnimator.onSpringItemBind(holder.itemView, position);


    }

    @Override
    public int getItemCount() {
        return recomendation.size() ;
    }

    public  class AboutViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView rating;
        TextView phone;
        TextView price;
        TextView vicinity;
        ImageView photo;

        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recommendationName);
            rating = itemView.findViewById(R.id.recommendationRating);
            phone = itemView.findViewById(R.id.recommendationPhone);
            price = itemView.findViewById(R.id.recommendationPrice);
            vicinity = itemView.findViewById(R.id.recommendationVicinity);
            photo = itemView.findViewById(R.id.recommendationImage);
        }
    }



}
