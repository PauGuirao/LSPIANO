package model;

public class EntradaGrafic {
    private int dia;
    private float temps;
    private int reproduccions;

    public EntradaGrafic(int dia) {
        this.dia = dia;
        temps = 0;
        reproduccions = 0;
    }
    public void sumaReproduccio(){
        reproduccions +=1;
    }
    public void sumaTemps(float temps){
        this.temps+=temps;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public float getTemps() {
        return temps;
    }

    public void setTemps(float temps) {
        this.temps = temps;
    }

    public int getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(int reproduccions) {
        this.reproduccions = reproduccions;
    }
}
