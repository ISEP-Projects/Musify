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
//reference:
//https://www.11zon.com/zon/android/horizontal-recyclerview-with-cardview-android.php
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<Item> dataList;
    private static GalleryAdapter.GalleryClickListener itemClickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.Name);
            imageView = v.findViewById(R.id.Icon);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onGalleryClick(view, getAdapterPosition());
        }
    }

    public GalleryAdapter(List<Item> myDataset) {
        dataList = myDataset;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTrack= layoutInflater.inflate(R.layout.card_album_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listTrack);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(GalleryAdapter.MyViewHolder holder, int position) {
        holder.textView1.setText(dataList.get(position).getName());
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

    public void setClickListener(GalleryAdapter.GalleryClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    Item getItem(int id) {
        return dataList.get(id);
    }

    public interface GalleryClickListener {
        void onGalleryClick(View view, int position);
    }


}
