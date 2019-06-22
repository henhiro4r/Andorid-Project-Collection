package com.example.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GridPresidentAdapter extends RecyclerView.Adapter<GridPresidentAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<President> lisPresident;

    public GridPresidentAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<President> getLisPresident() {
        return lisPresident;
    }

    public void setLisPresident(ArrayList<President> lisPresident) {
        this.lisPresident = lisPresident;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_president, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, int i) {
        Glide.with(context)
                .load(getLisPresident().get(i).getPhoto())
                .apply(new RequestOptions().override(350, 350))
                .into(gridViewHolder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return getLisPresident().size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
