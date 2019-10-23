package com.example.guide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Users;
import com.example.guide.R;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.StudentViewHolder> {
    private List<Users> tilte;
    private FragmentManager fragmentManager;
    private FrameLayout frame;
    private AdapterView.OnItemClickListener listner;


    public AboutAdapter(List<Users> tilte, FragmentManager fragmentManager) {
        this.tilte = tilte;
        this.fragmentManager = fragmentManager;
    }

    public AboutAdapter(List<Users> tilte) {
        this.tilte = tilte;
    }

    public void setListner(AdapterView.OnItemClickListener listner) {
        this.listner = listner;
    }

    @NonNull
    @Override

    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate Layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_about_recyler_view_list, parent, false);

        //fragment
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.title.setText(tilte.get(position).getName());
        holder.bind(tilte.get(position));
    }

    @Override
    public int getItemCount() {
        return tilte.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
        //void onName(String id);
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private CardView recyclerId;
        private TextView title;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            recyclerId = itemView.findViewById(R.id.recyclerId);

        }

        void bind(final Users users) {

            itemView.setOnClickListener(view -> {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }

                Toast.makeText(itemView.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();


//                    fragmentManager.beginTransaction()
//
//                            .replace(R.id.frame, new ProjectsRecyclerFragment(users))
//                            .addToBackStack("true")
//                            .commit();

            });
        }
    }

}
