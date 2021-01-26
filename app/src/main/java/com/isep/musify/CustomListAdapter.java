package com.isep.musify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.ui.LatestListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {
    private List<Item> dataList;
    private static CustomListAdapter.CustomListClickListner itemClickListener;
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
            if (itemClickListener != null) itemClickListener.onCustomListClick(view, getAdapterPosition());

        }

    }

    public CustomListAdapter(List<Item> myDataset) {
        dataList = myDataset;
    }

    @Override
    public CustomListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTrack= layoutInflater.inflate(R.layout.card_album_view, parent, false);
        CustomListAdapter.MyViewHolder viewHolder = new CustomListAdapter.MyViewHolder(listTrack);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomListAdapter.MyViewHolder holder, int position) {
        holder.textView1.setText(dataList.get(position).getName());
        Image image = dataList.get(position).getCover();
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

    public void setClickListener(CustomListAdapter.CustomListClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    Item getItem(int id) {
        return dataList.get(id);
    }

    public interface CustomListClickListner {
        void onCustomListClick(View view, int position);

    }


}