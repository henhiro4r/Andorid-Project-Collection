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
import com.uc.contohbottomnav.model.Trans;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<Trans> listTrans;
    private ArrayList<Trans> getListTrans() {
        return listTrans;
    }
    public void setListTrans(ArrayList<Trans> listTrans) {
        this.listTrans = listTrans;
    }
    public HistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_history_adapter, parent, false);
        return new CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.CardViewViewHolder holder, int position) {
        Trans t = getListTrans().get(position);
        String act = t.getAction();
        String des = t.getDesc();
        if(act.equalsIgnoreCase("d")){
            holder.cvImgHis.setCardBackgroundColor(ContextCompat.getColor(context, R.color.deposit));
            holder.txtCvImgHis.setText("D");
            holder.txtDescHis.setText(des);
            holder.txtDescHis.setTextColor(ContextCompat.getColor(context, R.color.deposit));
            holder.txtSubDescHis.setText("Asset point: +"+t.getPoint());
        }else if(act.equalsIgnoreCase("w")){
            holder.cvImgHis.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green2));
            holder.txtCvImgHis.setText("W");
            holder.txtDescHis.setText("+"+t.getPoint()+" Point");
            holder.txtDescHis.setTextColor(ContextCompat.getColor(context, R.color.green2));
            holder.txtSubDescHis.setText("Withdraw at "+des);
        }else if(act.equalsIgnoreCase("r")){
            holder.cvImgHis.setCardBackgroundColor(ContextCompat.getColor(context, R.color.ride));
            holder.txtCvImgHis.setText("R");
            holder.txtDescHis.setText(des);
            holder.txtDescHis.setTextColor(ContextCompat.getColor(context, R.color.ride));
            holder.txtSubDescHis.setText("Suroboyo Bus (point -3000)");
        }

        holder.txtTimeHis.setText(t.getTime());
    }

    @Override
    public int getItemCount() {
        return getListTrans().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        CardView cvImgHis;
        TextView txtCvImgHis;
        TextView txtDescHis;
        TextView txtSubDescHis;
        TextView txtTimeHis;
        CardViewViewHolder(View itemView) {
            super(itemView);
            cvImgHis = itemView.findViewById(R.id.cv_img_history);
            txtCvImgHis = itemView.findViewById(R.id.txt_in_cv_img_history);
            txtDescHis = itemView.findViewById(R.id.txt_desc_history);
            txtSubDescHis = itemView.findViewById(R.id.txt_subdesc_history);
            txtTimeHis = itemView.findViewById(R.id.txt_time_history);
        }
    }
}
