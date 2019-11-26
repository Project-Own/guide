package com.example.guide.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.NearbySearch.Result;
import com.example.guide.R;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.guide.lib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ResultViewHolder> {
    private NavController navController;
    private List<Result> resultList;
    private Context context;
    private AppCompatActivity activity;
    private SpringyAdapterAnimator mAnimator;

    public NearbyAdapter(List<Result> resultList, RecyclerView recyclerView, Context context, AppCompatActivity activity) {
        this.resultList = resultList;
        this.context = context;
        this.activity = activity;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }
    public NearbyAdapter(List<Result> resultList, RecyclerView recyclerView, Context context) {
        this.resultList = resultList;
        this.context = context;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }
    public NearbyAdapter(List<Result> resultList, RecyclerView recyclerView, Context context, NavController navController) {
        this.resultList = resultList;
        this.context = context;
        this.navController = navController;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }

    public NearbyAdapter(List<Result> resultList, Context context) {
        this.context = context;
        this.resultList = resultList;

    }



    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.result_recycler_list, parent, false);

        mAnimator.onSpringItemCreate(view);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.name.setText(resultList.get(position).getName());
        holder.description.setText(resultList.get(position).getVicinity());

        //
//        Glide.with(holder.itemView)
//                .load(context.getResources()
//                        .getIdentifier(, "drawable", context.getPackageName()))
//                .into(holder.imageView);
//        holder.bind(resultList.get(position));

//        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final SpringyAnimator scaleY = new SpringyAnimator(SpringAnimationType.SCALEXY, 85, 15, 0.8f, 1);
//
//                scaleY.startSpring(holder.cardView);
//                return false;
//            }
//        });



        mAnimator.onSpringItemBind(holder.itemView, position);


    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        ImageView imageView;
        CardView cardView;
        TextView name;



        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.result_recycler_list_text);
          //  imageView = itemView.findViewById(R.id.Result_recycler_list_image);
           // cardView = itemView.findViewById(R.id.Result_recycler_list_cardview);
            name = itemView.findViewById(R.id.modalText);

        }

        void bind(Result Result) {

            itemView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }


//                }
                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(cardView, "image_this")
                        .addSharedElement(description, "text_this")
                        .build();


                itemView.playSoundEffect(SoundEffectConstants.CLICK);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        final Bundle bundle = new Bundle();
                        bundle.putString("name", Result.getName());
                        bundle.putString("description", Result.getVicinity());
//                        bundle.putString("image", Result.getImage());
                        navController.navigate(R.id.action_nav_place_to_placeDetailFragment3, bundle, null, extras);
                    }
                }, 100);

            });
        }
    }


}
