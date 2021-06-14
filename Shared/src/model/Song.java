package model;

import model.Nota;
import model.User;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class Song implements Serializable {
    private String id;
    private boolean isPublic;
    private String nom;
    private String creador;
    private float durada;
    private int numReproduccions;
    private float totalEscoltat;
    private LinkedList<Nota> notas;
    private String procedencia;

    public Song(){
        this.id = "";
        this.nom = "";
        this.creador = "";
        this.durada = 0;
        this.numReproduccions = 0;
        this.totalEscoltat = 0;
        this.notas = new LinkedList<>(); //es null?
        this.procedencia = "";
    }

    public Song(String id, boolean isPublic, String nom, String creador) {
        this.id = id;
        this.isPublic = isPublic;
        this.nom = nom;
        this.creador = creador;
    }

    //Li hem de passar tots els atributs pel constructor?
    public Song(String id,boolean isPublic, String nom, String creador, float durada, int numReproduccions, float totalEscoltat, LinkedList<Nota> notas){
        this.isPublic = isPublic;
        this.nom = nom;
        this.creador = creador;
        this.durada = durada;
        this.numReproduccions = numReproduccions;
        this.totalEscoltat = totalEscoltat;
        this.notas = notas;
        this.id = id;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public float getDurada() {
        return durada;
    }

    public void setDurada(float durada) {
        this.durada = durada;
    }

    public LinkedList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(LinkedList<Nota> notas) {
        this.notas = notas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public int getNumReproduccions() {
        return numReproduccions;
    }

    public void sumaReproduccions(){
        numReproduccions += 1;
    }
}
