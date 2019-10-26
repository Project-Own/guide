package com.example.guide.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Modal.Home;
import com.example.guide.R;
import com.example.guide.activities.CalendarActivity;
import com.example.guide.activities.ForexActivity;
import com.example.guide.activities.MapsActivity;
import com.example.guide.activities.PlacesActivity;
import com.example.guide.activities.WeatherActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.AboutViewHolder> {
    private List<Home> homeList;
    private Context context;
    private Class aClass;

    public HomeAdapter(List<Home> homeList, Context context) {
        this.context = context;
        this.homeList = homeList;

    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_home_recyler_view_list, parent, false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        holder.name.setText(homeList.get(position).getName());
        if (homeList.get(position).getImage() == null) {
            Glide.with(holder.itemView)
                    .load(context.getResources()
                            .getIdentifier("logo", "drawable", context.getPackageName()))
                    .fitCenter()
                    .override(20, 20)
                    .into(holder.imageView);

        } else {
            Glide.with(holder.itemView)
                    .load(context.getResources()
                            .getIdentifier(homeList.get(position).getImage(), "drawable", context.getPackageName()))

                    .into(holder.imageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int viewNo = position;
                switch (viewNo) {
                    case 0:
                        aClass = MapsActivity.class;
                        break;
                    case 1:
                        aClass = WeatherActivity.class;
                        break;
                    case 2:
                        aClass = PlacesActivity.class;
                        break;
                    case 3:
                        aClass = PlacesActivity.class;
                        break;
                    case 4:
                        aClass = CalendarActivity.class;
                        break;
                    case 5:
                        aClass = ForexActivity.class;
                        break;
                }
                Intent intent = new Intent(context, aClass);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class AboutViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;


        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_text);
            imageView = itemView.findViewById(R.id.home_image);

        }
    }


}
