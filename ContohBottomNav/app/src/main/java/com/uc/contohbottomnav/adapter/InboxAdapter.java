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
import com.uc.contohbottomnav.model.Inbox;
import com.uc.contohbottomnav.model.Trans;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<Inbox> listInbox;
    private ArrayList<Inbox> getListInbox() {
        return listInbox;
    }
    public void setListInbox(ArrayList<Inbox> listInbox) {
        this.listInbox = listInbox;
    }
    public InboxAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InboxAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_inbox_adapter, parent, false);
        return new InboxAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.CardViewViewHolder holder, int position) {
        Inbox i = getListInbox().get(position);
        String title = i.getTitle();
        String desc = i.getContent();
        holder.txttitle.setText(title);
        holder.txtdesc.setText(desc);

    }

    @Override
    public int getItemCount() {
        return getListInbox().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView txttitle;
        TextView txtdesc;
        CardViewViewHolder(View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txt_title_inbox);
            txtdesc = itemView.findViewById(R.id.txt_desc_inbox);
        }
    }
}