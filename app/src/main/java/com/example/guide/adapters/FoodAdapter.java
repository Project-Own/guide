package com.example.guide.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.guide.Modal.Food;
import com.example.guide.R;
import com.example.guide.activities.FoodDetail;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    private Context context;
    private Activity activity;
    private SpringyAdapterAnimator mAnimator;

    public FoodAdapter(List<Food> foodList, RecyclerView recyclerView, Context context, Activity activity) {
        this.foodList = foodList;
        this.context = context;
        this.activity = activity;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_LEFT);
        mAnimator.addConfig(85, 15);


    }

    public FoodAdapter(List<Food> foodList, Context context) {
        this.context = context;
        this.foodList = foodList;

    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.food_recycler_list, parent, false);
        mAnimator.onSpringItemCreate(view);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.description.setText(foodList.get(position).getDescription());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(foodList.get(position).getImage(), "drawable", context.getPackageName()))

                .into(holder.imageView);
        holder.bind(foodList.get(position));
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
        Boolean click;


        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.food_recycler_list_text);
            imageView = itemView.findViewById(R.id.food_recycler_list_image);
            cardView = itemView.findViewById(R.id.food_recycler_list_cardview);

        }

        void bind(Food food) {

            itemView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }

                Intent myanim = new Intent(context, FoodDetail.class);
                myanim.putExtra("description", food.getDescription());
                myanim.putExtra("image", food.getImage());
                myanim.putExtra("name", food.getName());

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, cardView, "image_this");


                context.startActivity(myanim, options.toBundle());


                Toast.makeText(itemView.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();


            });
        }
    }


}
