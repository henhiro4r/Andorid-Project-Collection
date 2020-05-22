package com.uc.contohbottomnav.model;

import java.io.Serializable;

public class DetailPoint implements Serializable {

    private String idbottle;
    private String action;
    private String desc;
    private String time;
    private String point;

    public DetailPoint(){}

    public DetailPoint(String idbottle, String action, String desc, String time, String point) {
        this.idbottle = idbottle;
        this.action = action;
        this.desc = desc;
        this.time = time;
        this.point = point;
    }

    public String getIdbottle() {
        return idbottle;
    }

    public String getAction() {
        return action;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }

    public String getPoint() {
        return point;
    }
}
