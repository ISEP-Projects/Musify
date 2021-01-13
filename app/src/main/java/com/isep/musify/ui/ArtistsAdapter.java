package com.isep.musify.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.MyViewHolder> {

    private List<Item> dataList;
    private static ArtistClickListener itemClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.artistName);
            imageView = v.findViewById(R.id.artistIcon);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onArtistClick(view, getAdapterPosition());
        }
    }

    public ArtistsAdapter(List<Item> myDataset) {
        dataList = myDataset;
    }

    @Override
    public ArtistsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTrack= layoutInflater.inflate(R.layout.custom_artist_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listTrack);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(dataList.get(position).getName());
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


    public void setClickListener(ArtistClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    Item getItem(int id) {
        return dataList.get(id);
    }

    public interface ArtistClickListener {
        void onArtistClick(View view, int position);
    }
}