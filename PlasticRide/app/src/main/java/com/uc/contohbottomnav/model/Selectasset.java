package com.uc.contohbottomnav.model;

import com.uc.contohbottomnav.SelectAsset;

import java.io.Serializable;

public class Selectasset implements Serializable {

    private String idbottle;
    private String desc;
    private String poin;

    public Selectasset(){}

    public Selectasset(String idbottle, String desc, String poin) {
        this.idbottle = idbottle;
        this.desc = desc;
        this.poin = poin;
    }

    public String getIdbottle() {
        return idbottle;
    }

    public String getDesc() {
        return desc;
    }

    public String getPoin() {
        return poin;
    }
}
