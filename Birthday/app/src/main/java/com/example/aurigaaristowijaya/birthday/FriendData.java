package com.example.aurigaaristowijaya.birthday;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FriendData {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    static ArrayList<Friend> friends = new ArrayList<Friend>();
    static String u;
    static DatabaseReference mDatabase;
    static StorageReference mStorage;

    public static ArrayList<Friend> getListData(String u){//untuk recyclerview saja
        return friends;
    }

    public static void getData(String u){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(u).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datSnapshot : dataSnapshot.getChildren()){
                    Friend f = datSnapshot.getValue(Friend.class);
                    friends.add(f);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setData(ArrayList<Friend> f){
        friends = f;
    }

    public static int getListSize(){
        return getListData(u).size();
    }

//==================================================================================================
    public Friend getFriend(String id){
        int x = searchFriend(id);
        Friend f = getListData(u).get(x);
        return f;
    }

    public Friend getFriend(int id){
        Friend f = getListData(u).get(id);
        return f;
    }

    public int searchFriend(String id){
        for (int x = 0; x < friends.size(); x++){
            Friend f = getListData(u).get(x);
            String idi = f.getId();
            if (idi.equals(id)){
                return x;
            }
        }
        return -1;
    }

    public void deleteFriend(int id){
        Friend f = getFriend(id);
        getListData(u).remove(f);
    }
    public void deleteAll(){
        friends.clear();
    }
}
