package com.example.recyclerview;

import java.util.ArrayList;

public class PresidentData {
    public static String[][] data = new String[][]{
            {"Soekarno", "Presiden Pertama RI", "https://fusionsvisual.com/henry/kkura.jpg"},
            {"Soeharto", "Presiden Kedua RI", "https://fusionsvisual.com/henry/yujin.jpg"},
            {"Bacharuddin Jusuf Habibie", "Presiden Ketiga RI", "https://fusionsvisual.com/henry/nako.jpg"},
            {"Abdurrahman Wahid", "Presiden Keempat RI", "https://fusionsvisual.com/henry/minjuu.jpg"},
            {"Megawati Soekarnoputri", "Presiden Kelima RI", "https://fusionsvisual.com/henry/kwangbae.jpg"},
            {"Susilo Bambang Yudhoyono", "Presiden Keenam RI", "https://upload.wikimedia.org/wikipedia/commons/5/58/Presiden_Susilo_Bambang_Yudhoyono.png"},
            {"Joko Widodo", "Presiden Ketujuh RI", "https://upload.wikimedia.org/wikipedia/commons/1/1c/Joko_Widodo_2014_official_portrait.jpg"}
    };

    public static ArrayList<President> getListData(){
        President president = null;
        ArrayList<President> list = new ArrayList<>();
        for (String[] aData : data) {
            president = new President();
            president.setName(aData[0]);
            president.setRemarks(aData[1]);
            president.setPhoto(aData[2]);

            list.add(president);
        }

        return list;
    }
}
