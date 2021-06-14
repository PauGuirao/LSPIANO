package model;

import java.io.Serializable;

public class InfoSong implements Serializable {
    private String nom;
    private int temps;
    private String data;

    public InfoSong(String nom, int temps) {
        this.nom = nom;
        this.temps = temps;
    }

    public String getData() {
        return data;
    }

    public String getNom() {
        return nom;
    }

    public int getTemps() {
        return temps;
    }
}
