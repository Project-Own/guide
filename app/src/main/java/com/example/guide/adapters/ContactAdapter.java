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
import com.example.guide.Model.Contact;
import com.example.guide.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.AboutViewHolder>  {
    private List<Contact> contact;
    private Context context;

    public ContactAdapter(List<Contact> contact, Context context) {
        this.context=context;
        this.contact =contact;

    }


    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.fragment_contact_list,parent,false);
        return new AboutViewHolder(view);
    }


    @Override

    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        holder.name.setText(contact.get(position).getName());
        holder.number.setText(contact.get(position).getNumber());
        Glide.with(holder.itemView)
                .load(context.getResources()
                        .getIdentifier(contact.get(position).getPhoto(), "drawable", context.getPackageName()))
                .fitCenter()
                .override(150, 150)
                .into(holder.photo);



    }

    @Override
    public int getItemCount() {
        return contact.size() ;
    }

    public  class AboutViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        TextView name;
        TextView number;
        TextView location;



        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
            photo=itemView.findViewById(R.id.photo);
        }
    }

    public void filterList(List<Contact> contactList) {
        this.contact = contactList;
        notifyDataSetChanged();
    }



}
