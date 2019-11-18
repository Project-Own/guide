package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Model.Places;
import com.example.guide.R;
import com.example.guide.interfaces.GalleryTagsListInterface;
import com.example.guide.lib.SpringAnimationType;
import com.example.guide.lib.SpringyAnimator;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PlacesViewHolder> {
    private List<Places> placesList;
    private Context context;
    private AppCompatActivity activity;
    private SpringyAdapterAnimator mAnimator;
    private GalleryTagsListInterface galleryTagsListInterface;

    public GalleryAdapter(List<Places> placesList, RecyclerView recyclerView, Context context, GalleryTagsListInterface galleryTagsListInterface) {
        this.placesList = placesList;
        this.context = context;

        this.galleryTagsListInterface = galleryTagsListInterface;
//        mAnimator = new SpringyAdapterAnimator(recyclerView);
//        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SCALE);
//        mAnimator.addConfig(85, 15);

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

//        mAnimator.onSpringItemCreate(view);

        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .asBitmap()
                .load(context.getResources()
                        .getIdentifier(placesList.get(position).getImage(), "drawable", context.getPackageName()))
                .error(R.drawable.nirajan)
                .override(200, 200)
                .fitCenter()
                .into(holder.imageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                galleryTagsListInterface.onTagClicked(placesList.get(position).getImage(), placesList.get(position).getDescription(), position);
            }
        });

        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 5, 10, 0.8f, 1);

                scaleY.startSpring(holder.cardView);

                return false;
            }
        });


//        mAnimator.onSpringItemBind(holder.itemView, position);


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
            cardView = itemView.findViewById(R.id.places_recycler_list_cardview);
        }

        void bind(Places places) {

//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }


        }
    }


}
