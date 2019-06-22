package com.example.izone.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.izone.Model.Member;
import com.example.izone.R;

import java.util.ArrayList;

public class CardViewMemberAdapter extends RecyclerView.Adapter<CardViewMemberAdapter.CardViewVIewHolder> {

    private Context context;
    private ArrayList<Member> listMember;

    public CardViewMemberAdapter(Context context){
        this.context = context;
    }

    public ArrayList<Member> getListMember(){
        return listMember;
    }

    public void setListMember(ArrayList<Member> listMember){
        this.listMember = listMember;
    }

    @NonNull
    @Override
    public CardViewVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_member, viewGroup, false);
        return new CardViewVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewVIewHolder cardViewVIewHolder, int i) {
        Member m = getListMember().get(i);
        Glide.with(context)
                .load(m.getPhoto())
                .apply(new RequestOptions().override(150,150))
                .into(cardViewVIewHolder.imgPhoto);

        cardViewVIewHolder.tvName.setText(m.getName());
        cardViewVIewHolder.tvGroup.setText(m.getGroup());

    }

    @Override
    public int getItemCount() {
        return getListMember().size();
    }

    public class CardViewVIewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvGroup;
        public CardViewVIewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvGroup = itemView.findViewById(R.id.tv_item_group);
        }
    }
}
