package com.example.guide.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Modal.Places;
import com.example.guide.R;
import com.example.guide.activities.PlacesDetail;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {
    private List<Places> placesList;
    private Context context;
    private Activity activity;
    private SpringyAdapterAnimator mAnimator;

    public PlacesAdapter(List<Places> placesList, RecyclerView recyclerView, Context context, Activity activity) {
        this.placesList = placesList;
        this.context = context;
        this.activity = activity;
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
        holder.description.setText(placesList.get(position).getDescription());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(placesList.get(position).getImage(), "drawable", context.getPackageName()))

                .into(holder.imageView);
        holder.bind(placesList.get(position));
        mAnimator.onSpringItemBind(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        ImageView imageView;
        CardView cardView;



        PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.places_recycler_list_text);
            imageView = itemView.findViewById(R.id.places_recycler_list_image);
            cardView = itemView.findViewById(R.id.places_recycler_list_cardview);
        }

        void bind(Places places) {

            itemView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }

                Intent myanim = new Intent(context, PlacesDetail.class);
                myanim.putExtra("description", places.getDescription());
                myanim.putExtra("image", places.getImage());
                myanim.putExtra("name", places.getName());
                try {

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, cardView, "image_this");


                    context.startActivity(myanim, options.toBundle());


                    Toast.makeText(itemView.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                } catch (Error e) {

                    Log.i("TransitionAdapterPlaces", e.getMessage());
                    context.startActivity(myanim);
                }
            });
        }
    }


}
