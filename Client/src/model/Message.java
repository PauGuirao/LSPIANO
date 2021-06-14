/**
 * Missatges relacionats amb les cançons i la seva reproducció (Utilitza diferents int per saber les notes i el temps)
 * @version 1.1 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package model;

public class Message {
    private String tipus;
    private int nota;
    private int temps;

    public Message(String tipus, int nota, int temps) {
        this.tipus = tipus;
        this.nota = nota;
        this.temps = temps;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }
}