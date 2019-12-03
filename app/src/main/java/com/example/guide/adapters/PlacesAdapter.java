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
import androidx.appcompat.app.AppCompatActivity;
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

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {
    private NavController navController;
    private List<Places> placesList;
    private Context context;
    private AppCompatActivity activity;
    private SpringyAdapterAnimator mAnimator;

    public PlacesAdapter(List<Places> placesList, RecyclerView recyclerView, Context context, AppCompatActivity activity) {
        this.placesList = placesList;
        this.context = context;
        this.activity = activity;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }
    public PlacesAdapter(List<Places> placesList, RecyclerView recyclerView, Context context) {
        this.placesList = placesList;
        this.context = context;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }
    public PlacesAdapter(List<Places> placesList, RecyclerView recyclerView, Context context, NavController navController) {
        this.placesList = placesList;
        this.context = context;
        this.navController = navController;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }

    public PlacesAdapter(List<Places> placesList, Context context) {
        this.context = context;
        this.placesList = placesList;

    }



    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.places_recycler_list, parent, false);

        mAnimator.onSpringItemCreate(view);

        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        holder.name.setText(placesList.get(position).getName());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(placesList.get(position).getImage(), "drawable", context.getPackageName()))
                .into(holder.imageView);
        holder.bind(placesList.get(position));

        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 1, 50, 0.8f, 1);

                scaleY.startSpring(holder.cardView);
                return false;
            }
        });



        mAnimator.onSpringItemBind(holder.itemView, position);


    }


    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;
        TextView name;



        PlacesViewHolder(@NonNull View itemView) {
            super(itemView);

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
                        navController.navigate(R.id.action_nav_place_to_placeDetailFragment3, bundle );
                    }
                }, 50);

            });
        }
    }


}
