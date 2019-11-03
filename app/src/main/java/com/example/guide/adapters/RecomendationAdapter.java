package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Modal.Recomendation;
import com.example.guide.R;
import com.example.guide.lib.SpringAnimationType;
import com.example.guide.lib.SpringyAnimator;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.AboutViewHolder>  {
    private List<Recomendation> recomendation;
    private Context context;
    private SpringyAdapterAnimator mAnimator;
    private RecyclerView recyclerView;

    public RecomendationAdapter(List<Recomendation> recomendation, Context context, RecyclerView recyclerView) {
        this.context=context;
        this.recomendation= recomendation;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_RIGHT);
        mAnimator.addConfig(85, 15);

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
        holder.description.setText("Description:\n" + recomendation.get(position).getDescription());
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
        TextView description;
        TextView price;
        TextView vicinity;
        ImageView photo;

        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recommendationName);
            rating = itemView.findViewById(R.id.recommendationRating);
            phone = itemView.findViewById(R.id.recommendationPhone);
            description = itemView.findViewById(R.id.recommendationDescription);
            price = itemView.findViewById(R.id.recommendationPrice);
            vicinity = itemView.findViewById(R.id.recommendationVicinity);
            photo = itemView.findViewById(R.id.recommendationImage);
        }
    }



}
