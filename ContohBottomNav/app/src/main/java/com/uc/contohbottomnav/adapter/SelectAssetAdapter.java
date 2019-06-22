package com.uc.contohbottomnav.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uc.contohbottomnav.R;
import com.uc.contohbottomnav.model.Selectasset;


import java.util.ArrayList;

public class SelectAssetAdapter extends RecyclerView.Adapter<SelectAssetAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<Selectasset> listSelAsset;
    private ArrayList<Selectasset> getListSelAsset() {
        return listSelAsset;
    }
    public void setListSelAsset(ArrayList<Selectasset> listSelAsset) {
        this.listSelAsset = listSelAsset;
    }
    public SelectAssetAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_select_asset_adapter, parent, false);
        return new CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectAssetAdapter.CardViewViewHolder holder, int position) {
        Selectasset s = getListSelAsset().get(position);
        String desc = s.getDesc();
        String poin = "+"+s.getPoin();
        if(desc.equalsIgnoreCase("Large Bottle")){
            holder.cvselasset.setCardBackgroundColor(ContextCompat.getColor(context, R.color.deposit));
            holder.txtdesc.setText(desc);
            holder.txtpoint.setText(poin);
        }else if(desc.equalsIgnoreCase("Medium Bottle")){
            holder.cvselasset.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green2));
            holder.txtdesc.setText(desc);
            holder.txtpoint.setText(poin);
        }else if(desc.equalsIgnoreCase("Small Bottle")){
            holder.cvselasset.setCardBackgroundColor(ContextCompat.getColor(context, R.color.ride));
            holder.txtdesc.setText(desc);
            holder.txtpoint.setText(poin);
        }

    }

    @Override
    public int getItemCount() {
        return getListSelAsset().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        CardView cvselasset;
        TextView txtdesc;
        TextView txtpoint;

        CardViewViewHolder(View itemView) {
            super(itemView);
            cvselasset = itemView.findViewById(R.id.cv_selasset_adapter);
            txtdesc = itemView.findViewById(R.id.txt_desc_selasset);
            txtpoint = itemView.findViewById(R.id.txt_point_selasset);

        }
    }
}
