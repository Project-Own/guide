package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Recomendation;
import com.example.guide.Modal.Users;
import com.example.guide.R;

import java.util.List;

public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.AboutViewHolder>  {
    private List<Recomendation> recomendation;
    private Context context;

    public RecomendationAdapter(List<Recomendation> recomendation, Context context) {
        this.context=context;
        this.recomendation= recomendation;

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
        holder.name.setText(recomendation.get(position).getName().toString());
        holder.roll.setText(recomendation.get(position).getRoll().toString());
        holder.email.setText(recomendation.get(position).getEmail().toString());


    }

    @Override
    public int getItemCount() {
        return recomendation.size() ;
    }

    public  class AboutViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView roll;
        TextView email;


        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            roll=itemView.findViewById(R.id.roll);
            email=itemView.findViewById(R.id.email);

        }
    }



}