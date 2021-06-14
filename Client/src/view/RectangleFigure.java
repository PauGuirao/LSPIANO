/**
 * Vista referent al dibuix dels indicadors que van caient quan reproduim una canço (Utilitza coordenades del tipus int)
 * @version 1.4 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

public class RectangleFigure {
    private int xPos;
    private int yPos;
    private int ySize;
    private int xSize;
    private int tipus;

    public RectangleFigure(int xPos, int yPos, int ySize,int xSize,int tipus){
        this.xPos = xPos;
        this.yPos = yPos;
        this.ySize = ySize;
        this.xSize = xSize;
        this.tipus = tipus;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos += yPos;
    }

    public int getySize() {
        return ySize;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public int getTipus() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus = tipus;
    }
}

