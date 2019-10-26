package com.example.guide.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.MapsButton;
import com.example.guide.R;
import com.example.guide.activities.TagsListInterface;

import java.util.List;

public class MapsButtonAdapter extends RecyclerView.Adapter<MapsButtonAdapter.AboutViewHolder> {
    private List<MapsButton> MapsButtonList;
    private Context context;
    private Activity activity;
    private TagsListInterface tagsListInterface;


    public MapsButtonAdapter(List<MapsButton> MapsButtonList, Context context, Activity activity, TagsListInterface tagsListInterface) {
        this.MapsButtonList = MapsButtonList;
        this.context = context;
        this.activity = activity;
        this.tagsListInterface = tagsListInterface;
    }

    public MapsButtonAdapter(List<MapsButton> MapsButtonList, Context context) {
        this.context = context;
        this.MapsButtonList = MapsButtonList;

    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.maps_button_recycler_list, parent, false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        holder.button.setText(MapsButtonList.get(position).getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsListInterface.onTagClicked(MapsButtonList.get(position).getName(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MapsButtonList.size();
    }

    class AboutViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        ImageView imageView;
        CardView cardView;
        Button button;


        AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.maps_button);
        }

        void bind() {

            itemView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }
//                Intent myanim = new Intent(context, MapsButtonDetail.class);
//                myanim.putExtra("description", description.getText());
//
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, cardView, "image_this");
//
//
//                context.startActivity(myanim, options.toBundle());
//
//
//                Toast.makeText(itemView.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();


            });
        }
    }


}
