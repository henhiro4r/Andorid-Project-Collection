package com.example.aurigaaristowijaya.birthday;

import com.google.firebase.database.DatabaseReference;

public class SettingsData{
    private boolean chinese;
    private boolean western;
    private int zodiac;
    private String time;
    private boolean onBirthday;
    private boolean oneDay;
    private boolean twoDay;
    private boolean sevenDay;
    private boolean fourteenDay;
    private String msgTemp;
    private String msgSub;
    private String language;

    public SettingsData(){

    }
    public SettingsData(boolean chinese, boolean western, String time, boolean onbirthday, boolean oneday, boolean twodays, boolean sevendays, boolean fourteendays, String mbody, String mSubject, String lang){
        this.chinese = chinese;
        this.western = western;
        this.time = time;
        this.onBirthday = onbirthday;
        this.oneDay = oneday;
        this.twoDay = twodays;
        this.sevenDay = sevendays;
        this.fourteenDay = fourteendays;
        this.msgTemp = mbody;
        this.msgSub = mSubject;
        this.language = lang;
    }

    public void setDefault(){
        chinese = false;
        western = false;
        zodiac = 3;
        hour = 8;
        minute = 0;
        onBirthday = false;
        oneDay = false;
        twoDay = false;
        sevenDay = false;
        fourteenDay = false;
        msgTemp = "-";
        msgSub = "-";
        language = "0";
        time = "08:00";
    }

    //==============================================================================================
    public String getMessageTemp(){
        return msgTemp;
    }
    public void setMessageTemp(String msgTemp) {
        this.msgTemp = msgTemp;
    }
    public String getMessageSubject() {
        return msgSub;
    }
    public void setMessageSubject(String msgSubject) {
        this.msgSub = msgSubject;
    }
    //==============================================================================================
    public int getLanguage() {
        return Integer.parseInt(language);
    }
    public void setLanguage(int language) {
        this.language = language + "";
    }
    //==============================================================================================
    public boolean getChinese() {
        return chinese;
    }
    public boolean getWestern() {
        return western;
    }
    public void setZodiac(int zodiac){//0=west, 1=chi, 2=west+chi, 3=none
        switch (zodiac){
            case 0:
                western = true;
                chinese = false;
                break;
            case 1:
                western = false;
                chinese = true;
                break;
            case 2:
                western = true;
                chinese = true;
                break;
            case 3:
                western = false;
                chinese = false;
                break;
        }
        this.zodiac = zodiac;
    }
    public int getZodiac() {
        return zodiac;
    }
    //==============================================================================================
    private int hour, minute;

    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public void setTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
        String xhour = intToString(this.hour);
        String xminute = intToString(this.minute);
        time = xhour + ":" + xminute;
    }
    public String showTime(){
        return time;
    }

    private String intToString(int x){
        if (x > 0 && x < 10){
            return "0" + x;
        } else if(x == 0) {
            return "00";
        } else {
            return "" + x;
        }
    }
    //==============================================================================================
    public boolean getZero(){return onBirthday;}
    public void setZero(boolean zero){this.onBirthday = zero;}
    public boolean getOne(){return oneDay;}
    public void setOne(boolean one){this.oneDay = one;}
    public boolean getTwo(){return twoDay;}
    public void setTwo(boolean two){this.twoDay = two;}
    public boolean getSeven(){return sevenDay;}
    public void setSeven(boolean seven){this.sevenDay = seven;}
    public boolean getFourteen(){return fourteenDay;}
    public void setFourteen(boolean fourteen){this.fourteenDay = fourteen;}
}
