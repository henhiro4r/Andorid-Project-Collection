package com.example.aurigaaristowijaya.birthday;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class BirthdayList extends RecyclerView.Adapter<BirthdayList.ViewHolder> {
    private Context context;
    private ArrayList<Friend> listFriend;
    private StorageReference mStorage;

    public ArrayList<Friend> getListFriend() {
        return listFriend;
    }

    public void setListFriend(ArrayList<Friend> listFriend){
        this.listFriend = listFriend;
    }

    public BirthdayList(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_birthday, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        holder.friendName.setText(getListFriend().get(position).getName());
        holder.friendDate.setText(tanggal(position));
        holder.friendCountdown.setText(countdown(position));
        holder.friendTurns.setText(turns(position));

        String url = getListFriend().get(position).getPhoto();
        mStorage = FirebaseStorage.getInstance().getReference().child(url);
        mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .crossFade()
                        .into(holder.friendPhoto);
            }
        });
    }

    private String tanggal(int pos){
        String[] t = getListFriend().get(pos).getDoB().split("/");
        int tgl = Integer.parseInt(t[0]);
        int bln = Integer.parseInt(t[1]);
        int thn = Integer.parseInt(t[2]);
        String bulan = "";
        switch(bln){
            case 1: bulan = context.getResources().getString(R.string.january); break;
            case 2: bulan = context.getResources().getString(R.string.february); break;
            case 3: bulan = context.getResources().getString(R.string.march); break;
            case 4: bulan = context.getResources().getString(R.string.april); break;
            case 5: bulan = context.getResources().getString(R.string.may); break;
            case 6: bulan = context.getResources().getString(R.string.june); break;
            case 7: bulan = context.getResources().getString(R.string.july); break;
            case 8: bulan = context.getResources().getString(R.string.august); break;
            case 9: bulan = context.getResources().getString(R.string.september); break;
            case 10: bulan = context.getResources().getString(R.string.october); break;
            case 11: bulan = context.getResources().getString(R.string.november); break;
            case 12: bulan = context.getResources().getString(R.string.december); break;
        }
        return bulan + " " + tgl + ", " + thn;
    }
    private String countdown(int pos){
        String[] tgllahir = getListFriend().get(pos).getDoB().split("/");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String[] today = format.format(now).split("/");
        int year_days = 0;
        if (Integer.parseInt(today[2]) % 4 == 0){
            year_days = 366;
        } else {
            year_days = 365;
        }

        int lahirBulan = Integer.parseInt(tgllahir[1]);
        int lahirTanggal = Integer.parseInt(tgllahir[0]);
        int todayBulan = Integer.parseInt(today[1]);
        int todayTanggal = Integer.parseInt(today[0]);
        int currentDays = absoluteDay(todayBulan, todayTanggal);
        int birthDayDays = absoluteDay(lahirBulan, lahirTanggal);
        int hari = birthDayDays - currentDays >= 0 ? birthDayDays-currentDays: year_days - (currentDays-birthDayDays);

        if (hari == 1){
            return context.getResources().getString(R.string.tomorrow);
        } else if (hari == 0){
            return context.getResources().getString(R.string.today);
        } else {
            return hari + " " + context.getResources().getString(R.string.days_left);
        }
    }
    private int absoluteDay (int month, int day){
        int[] days = {0, 0, 31, 60, 91, 121, 91, 121, 152, 182,
                213, 244, 274, 305, 335};
        return days[month] + day;
    }
    private String turns(int pos){
        String[] tgllahir = getListFriend().get(pos).getDoB().split("/");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String[] sekarang;
        try {
            sekarang = format.format(now).split("/");
        } catch (Exception e){
            return "Wrong Format";
        }
        int umur = Integer.parseInt(sekarang[2]) - Integer.parseInt(tgllahir[2]) + 1;
        if (Integer.parseInt(sekarang[1]) < Integer.parseInt(tgllahir[1])){
            umur++;
        } else if (Integer.parseInt(sekarang[1]) == Integer.parseInt(tgllahir[1])){
            if (Integer.parseInt(sekarang[0]) < Integer.parseInt(tgllahir[0])){
                umur++;
            }
        }
        return umur + "";
    }

    @Override
    public int getItemCount(){
        return listFriend.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView friendName, friendDate, friendCountdown, friendTurns;
        CircleImageView friendPhoto;

        public ViewHolder(View itemView){
            super(itemView);
            friendName = (TextView)itemView.findViewById(R.id.friend_name);
            friendDate = (TextView)itemView.findViewById(R.id.friend_date);
            friendCountdown = (TextView)itemView.findViewById(R.id.friend_countdown);
            friendTurns = (TextView)itemView.findViewById(R.id.friend_turns);
            friendPhoto = (CircleImageView)itemView.findViewById(R.id.friend_photo);
        }
    }
}
