package com.example.samplestickerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterCollectedStickers extends RecyclerView.Adapter<RecyclerAdapterCollectedStickers.ImageViewHolder> {

    private int[] imagesStickersCollected;
    public RecyclerAdapterCollectedStickers(int[] imagesStickersCollected){
        this.imagesStickersCollected = imagesStickersCollected;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        int image_id = imagesStickersCollected[position];
        holder.Album.setImageResource(image_id);


    }

    @Override
    public int getItemCount() {
        return imagesStickersCollected.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Album;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Album = itemView.findViewById(R.id.album);

        }
    }
}
