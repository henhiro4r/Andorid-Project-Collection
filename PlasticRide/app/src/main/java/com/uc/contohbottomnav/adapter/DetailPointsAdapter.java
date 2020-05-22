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
import com.uc.contohbottomnav.model.DetailPoint;
import com.uc.contohbottomnav.model.Selectasset;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailPointsAdapter extends RecyclerView.Adapter<DetailPointsAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<DetailPoint> listDetailPoint;
    private ArrayList<DetailPoint> getListDetailPoint() {
        return listDetailPoint;
    }
    public void setListDetailPoint(ArrayList<DetailPoint> listDetailPoint) {
        this.listDetailPoint = listDetailPoint;
    }
    public DetailPointsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DetailPointsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_detail_points_adapter, parent, false);
        return new DetailPointsAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DetailPointsAdapter.CardViewViewHolder holder, int position) {
        DetailPoint s = getListDetailPoint().get(position);
        String desc = s.getDesc();
        String poin = s.getPoint();
        String bottleid = s.getIdbottle();
        try {
            if(bottleid.charAt(4) == 'A'){
                desc += " - Large Bottle";
            }else if(bottleid.charAt(4) == 'B'){
                desc += " - Medium Bottle";
            }else if(bottleid.charAt(4) == 'C'){
                desc += " - Small Bottle";
            }else if(bottleid.isEmpty()){}
        }catch (Exception e){}

        String action = s.getAction();
        String time = s.getTime();
        if(action.equalsIgnoreCase("w")){
            holder.txtTitle.setText("WITHDRAW");
            holder.txtdesc.setText(desc);
            holder.txtpoint.setText("+"+poin);
            holder.txtpoint.setTextColor(ContextCompat.getColor(context, R.color.green2));
            holder.txtTime.setText(time);
        }else if(action.equalsIgnoreCase("r")){
            holder.txtTitle.setText("RIDE");
            holder.txtdesc.setText(desc);
            holder.txtpoint.setText("-"+poin);
            holder.txtpoint.setTextColor(ContextCompat.getColor(context, R.color.deposit));
            holder.txtTime.setText(time);
        }

    }

    @Override
    public int getItemCount() {
        return getListDetailPoint().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtTime;
        TextView txtdesc;
        TextView txtpoint;

        CardViewViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_detail_point);
            txtTime = itemView.findViewById(R.id.txt_time_detail_point);
            txtdesc = itemView.findViewById(R.id.txt_desc_detail_point);
            txtpoint = itemView.findViewById(R.id.txt_point_detail_point);

        }
    }


}
