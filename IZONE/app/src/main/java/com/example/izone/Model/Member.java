package com.example.izone.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String name;
    private String photo;
    private String photo2;
    private String description;
    private String group;

    public Member(){

    }

    protected Member(Parcel in) {
        name = in.readString();
        photo = in.readString();
        photo2 = in.readString();
        description = in.readString();
        group = in.readString();
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(photo);
        parcel.writeString(photo2);
        parcel.writeString(description);
        parcel.writeString(group);
    }

    public static final Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>(){

        @Override
        public Member createFromParcel(Parcel parcel) {
            return new Member(parcel);
        }

        @Override
        public Member[] newArray(int i) {
            return new Member[i];
        }
    };
}
