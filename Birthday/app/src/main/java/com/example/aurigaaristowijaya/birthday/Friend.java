package com.example.aurigaaristowijaya.birthday;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Friend {
    private String name;
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String DoB;
    private String id;

    public Friend(){}

    public Friend(String name, String mail, String phone, String dob, String add, String img, String id) {
        this.name = name;
        this.email = mail;
        this.phone = phone;
        this.DoB = dob;
        this.address = add;
        this.photo = img;
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("phone", phone);
        result.put("doB", DoB);
        result.put("address", address);
        result.put("photo", photo);
        result.put("id", id);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        this.DoB = doB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
