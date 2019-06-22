package com.uc.contohbottomnav.model;

import java.io.Serializable;

public class Trans implements Serializable {
    public Trans(){}

    private String idtrans;
    private String iduser;
    private String action;
    private String time;
    private String desc;
    private String idbottle;
    private String idtrash;
    private String idbus;
    private String point;

    public Trans(String idtrans, String iduser, String action, String time, String desc, String idbottle, String idtrash, String idbus, String point) {
        this.idtrans = idtrans;
        this.iduser = iduser;
        this.action = action;
        this.time = time;
        this.desc = desc;
        this.idbottle = idbottle;
        this.idtrash = idtrash;
        this.idbus = idbus;
        this.point = point;
    }

    public String getIdtrans() {
        return idtrans;
    }

    public String getIduser() {
        return iduser;
    }

    public String getAction() {
        return action;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public String getIdbottle() {
        return idbottle;
    }

    public String getIdtrash() {
        return idtrash;
    }

    public String getIdbus() {
        return idbus;
    }

    public String getPoint() {
        return point;
    }
}
