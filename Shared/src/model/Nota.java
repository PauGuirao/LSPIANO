package model;

import java.io.Serializable;

public class Nota implements Serializable {
    private int nota;
    private double inici;
    private double finali;
    private double tamany;

    public Nota(int nota, double inici,double finali,double tamany) {
        this.nota = nota;
        this.inici = inici;
        this.finali = finali;
        this.tamany = tamany;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public double getInici() {
        return inici;
    }

    public void setInici(double inici) {
        this.inici = inici;
    }

    public double getFinali() {
        return finali;
    }

    public void setFinali(double finali) {
        this.finali = finali;
    }

    public double getTamany() {
        return tamany;
    }

    public void setTamany(double tamany) {
        this.tamany = tamany;
    }
}
