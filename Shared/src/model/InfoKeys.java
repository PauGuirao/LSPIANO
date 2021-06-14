package model;

import java.io.Serializable;

public class InfoKeys implements Serializable {
    private String codiAmic;
    private String keys;

    public InfoKeys(String keys,String codiAmic) {
        this.codiAmic = codiAmic;
        this.keys = keys;
    }

    public String getCodiAmic() {
        return codiAmic;
    }

    public String getKeys() {
        return keys;
    }
}

