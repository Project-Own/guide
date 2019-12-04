package com.example.guide.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Model.Places;
import com.example.guide.R;
import com.example.guide.lib.SpringAnimationType;
import com.example.guide.lib.SpringyAnimator;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Places> foodList;
    private Context context;
    private NavController navController;
    private SpringyAdapterAnimator mAnimator;

    public FoodAdapter(List<Places> foodList, RecyclerView recyclerView, Context context, NavController navController) {
        this.foodList = foodList;
        this.context = context;
        this.navController = navController;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_LEFT);
        mAnimator.addConfig(85, 15);


    }

    public FoodAdapter(List<Places> foodList, Context context) {
        this.context = context;
        this.foodList = foodList;

    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.places_recycler_list, parent, false);
        mAnimator.onSpringItemCreate(view);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.description.setText(foodList.get(position).getDescription());
        holder.name.setText(foodList.get(position).getName());

        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(foodList.get(position).getImage(), "drawable", context.getPackageName()))
                .into(holder.imageView);
        holder.bind(foodList.get(position));
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 85, 15, 0.8f, 1);

                scaleY.startSpring(holder.cardView);
                return false;
            }
        });
        mAnimator.onSpringItemBind(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        ImageView imageView;
        CardView cardView;
        TextView name;
        Boolean click;


        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.places_recycler_list_description);
            imageView = itemView.findViewById(R.id.places_recycler_list_image);
            cardView = itemView.findViewById(R.id.places_recycler_list_cardview);
            name = itemView.findViewById(R.id.modalText);

        }

        void bind(Places places) {

            cardView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }


//                }

                cardView.playSoundEffect(SoundEffectConstants.CLICK);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        final Bundle bundle = new Bundle();
                        bundle.putString("name", places.getName());
                        bundle.putString("description", places.getDescription());
                        bundle.putString("image", places.getImage());
                        bundle.putDouble("lat", places.getLat());
                        bundle.putDouble("long", places.getLang());
                        navController.navigate(R.id.action_nav_food_to_placeDetailFragment3, bundle);
                    }
                }, 50);

            });
        }
    }


}
