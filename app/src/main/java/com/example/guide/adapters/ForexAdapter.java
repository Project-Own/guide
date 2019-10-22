package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Weather.WeatherData;
import com.example.guide.R;

import java.util.List;

public class ForexAdapter extends RecyclerView.Adapter<ForexAdapter.StudentViewHolder> {

    private List<Object> forexList;
    private List<String> pairList;

    private FrameLayout frame;
    private AdapterView.OnItemClickListener listner;

    private Context context;


    public ForexAdapter(List<String> pairList, List<Object> forexList, Context context) {
        this.forexList = forexList;
        this.pairList = pairList;
        this.context = context;
    }

    public ForexAdapter(List<String> pairList, List<Object> forexList) {
        this.forexList = forexList;
        this.pairList = pairList;
    }

    public void setListner(AdapterView.OnItemClickListener listner) {
        this.listner = listner;
    }

    @NonNull
    @Override

    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate Layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forex_recyler_list, parent, false);

        //fragment
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {


        holder.forexPairTextView.setText(pairList.get(position));
        holder.forexRateTextView.setText(forexList.get(position).toString());
        //     Glide.with(holder.itemView).load(weatherData.get(position).getThumbnailUrl()).into(holder.photo);
        //     holder.bind(weatherData.get(position));
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
        //void onName(String id);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView forexPairTextView;
        private TextView forexRateTextView;
        private ImageView photo;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            forexPairTextView = itemView.findViewById(R.id.forexPairTextView);
            forexRateTextView = itemView.findViewById(R.id.forexRateTextView);
            //     photo = itemView.findViewById(R.id.textImage);
        }

        public void bind(final WeatherData forexPairTextView) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                if(listner != null){
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onItemClick();
//                    }

//                }

                    Toast.makeText(itemView.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

}
