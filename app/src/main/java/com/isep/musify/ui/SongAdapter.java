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

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private List<Item> dataList;
    private static SongClickListener itemClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1, textView2;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.songName);
            textView2 = v.findViewById(R.id.artistName);
            imageView = v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public SongAdapter(List<Item> myDataset) {
        dataList = myDataset;
    }

    @Override
    public SongAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTrack= layoutInflater.inflate(R.layout.custom_song_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listTrack);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView1.setText(dataList.get(position).getName());
        holder.textView2.setText(dataList.get(position).getArtistName());
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


    public void setClickListener(SongClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    Item getItem(int id) {
        return dataList.get(id);
    }

    public interface SongClickListener {
        void onClick(View view, int position);
    }
}