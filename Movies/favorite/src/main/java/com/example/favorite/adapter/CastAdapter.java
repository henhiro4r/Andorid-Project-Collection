package com.example.favorite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favorite.R;
import com.example.favorite.model.Cast;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private Context context;
    private ArrayList<Cast> cast = new ArrayList<>();

    public CastAdapter(Context context) {
        this.context = context;
    }

    public void setCasts(ArrayList<Cast> casts) {
        cast.clear();
        cast.addAll(casts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, viewGroup, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder castViewHolder, int i) {
        Cast c = cast.get(i);
        Glide.with(context).load(c.getImg_url()).into(castViewHolder.iv_cast);
        castViewHolder.tv_name.setText(c.getName());
        castViewHolder.tv_role.setText(c.getRole());
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_cast;
        TextView tv_name, tv_role;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cast = itemView.findViewById(R.id.cast_img);
            tv_name = itemView.findViewById(R.id.cast_name);
            tv_role = itemView.findViewById(R.id.cast_role);
        }
    }
}
