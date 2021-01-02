package com.isep.musify.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.R;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
    REFERENCES:
    https://developer.android.com/guide/topics/ui/layout/recyclerview
    https://www.javatpoint.com/android-recyclerview-list-example
    https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.MyViewHolder> {

    private List<Item> dataList;
    private static TrackClickListener itemClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1, textView2;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.trackName);
            textView2 = v.findViewById(R.id.trackDescription);
            imageView = v.findViewById(R.id.trackIcon);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onTrackClick(view, getAdapterPosition());
        }
    }

    public TracksAdapter(List<Item> myDataset) {
        dataList = myDataset;
    }

    @Override
    public TracksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTrack= layoutInflater.inflate(R.layout.custom_track_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listTrack);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(dataList.get(position).getName());
        holder.textView2.setText(dataList.get(position).getDescription());
        Image image = dataList.get(position).getIcon();
        Picasso.get()
                .load(image.getUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if(dataList != null) {
            return dataList.size();
        }else {
            return 0;
        }
    }


    public void setClickListener(TrackClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    Item getItem(int id) {
            return dataList.get(id);
    }

    public interface TrackClickListener {
        void onTrackClick(View view, int position);
    }
}