package com.example.guide.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Modal.Places;
import com.example.guide.R;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PlacesViewHolder> {
    private List<Places> placesList;
    private Context context;
    private Activity activity;
    private SpringyAdapterAnimator mAnimator;

    public GalleryAdapter(List<Places> placesList, RecyclerView recyclerView, Context context, Activity activity) {
        this.placesList = placesList;
        this.context = context;
        this.activity = activity;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);

    }

    public GalleryAdapter(List<Places> placesList, Context context) {
        this.context = context;
        this.placesList = placesList;

    }


    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gallery_recycler_list, parent, false);

        mAnimator.onSpringItemCreate(view);

        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(placesList.get(position).getImage(), "drawable", context.getPackageName()))
                .error(R.drawable.nirajan)
                .into(holder.imageView);
        holder.bind(placesList.get(position));
        mAnimator.onSpringItemBind(holder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;


        PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.places_recycler_list_image);
        }

        void bind(Places places) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }


        }
    }


}
