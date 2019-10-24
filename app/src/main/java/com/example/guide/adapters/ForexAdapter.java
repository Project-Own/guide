package com.example.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.R;

import java.util.List;
import java.util.Map;

public class ForexAdapter extends RecyclerView.Adapter<ForexAdapter.StudentViewHolder> {

    private List<Object> forexList;
    private List<String> pairList;

    private Map<String, String> countryNameMap;


    public ForexAdapter(List<Object> forexList, List<String> pairList, Map<String, String> countryNameMap, Context context) {
        this.forexList = forexList;
        this.pairList = pairList;
        this.countryNameMap = countryNameMap;
        this.context = context;
    }

    private FrameLayout frame;
    private AdapterView.OnItemClickListener listner;

    private Context context;



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
        String from = pairList.get(position).substring(3);
        String to = pairList.get(position).substring(0, 3);

        if (countryNameMap != null) {
            holder.forexPairTextView.setText(String.format("%s ---> %s :", countryNameMap.get(from), countryNameMap.get(to)));
            holder.forexRateTextView.setText(forexList.get(position).toString());

        } else {
            holder.forexPairTextView.setText(String.format("%s ---> %s :", from, to));
            holder.forexRateTextView.setText(forexList.get(position).toString());

        }
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

    public void filterList(List<Object> forexList, List<String> pairList) {
        this.forexList = forexList;
        this.pairList = pairList;
        notifyDataSetChanged();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView forexPairTextView;
        private TextView forexRateTextView;
        private ImageView photo;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            forexPairTextView = itemView.findViewById(R.id.forexPairTextView);
            forexRateTextView = itemView.findViewById(R.id.forexRateTextView);
            //     photo = itemView.findViewById(R.id.textImage);
        }

    }

}
