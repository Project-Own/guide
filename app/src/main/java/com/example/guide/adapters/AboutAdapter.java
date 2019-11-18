package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guide.Model.Users;
import com.example.guide.R;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder>  {
    private List<Users> usersList;
    private Context context;

    public AboutAdapter(List<Users> usersList, Context context) {
        this.context=context;
        this.usersList = usersList;

    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.fragment_about_recyler_view_list,parent,false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        holder.name.setText(usersList.get(position).getName().toString());
        holder.roll.setText(usersList.get(position).getRoll().toString());
        holder.email.setText(usersList.get(position).getEmail().toString());
        if(usersList.get(position).getImage() == null){
            Glide.with(holder.itemView)
                    .load(context.getResources()
                            .getIdentifier("code_icon", "drawable", context.getPackageName()))
                            .fitCenter()
                    .override(20,20)
                    .into(holder.imageView);

        }else {
            Glide.with(holder.itemView)
                    .load(context.getResources()
                            .getIdentifier(usersList.get(position).getImage(), "drawable", context.getPackageName()))

                    .into(holder.imageView);
        }



    }

    @Override
    public int getItemCount() {
        return usersList.size() ;
    }

    public  class AboutViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView roll;
        TextView email;
        ImageView imageView;


        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            roll=itemView.findViewById(R.id.roll);
            email=itemView.findViewById(R.id.email);
            imageView=itemView.findViewById(R.id.about_image);

        }
    }



}
