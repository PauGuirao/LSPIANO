/**
 * Vista referent a la creació dels indicadors que van caient quan reproduim una cançó (Utilitzem una PlayController)
 * @version 2.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.PlayController;

import javax.swing.*;
import java.awt.*;

public class NoteHolderPanel extends JPanel {

    private PlayController playController;
    public NoteHolderPanel(PlayController playController) {
        this.playController = playController;
        setPreferredSize(new Dimension(300,300));
    }

    @Override
    public void paintComponent(Graphics g) {
        for(RectangleFigure r:playController.getRectangleFigures()){
            Graphics2D g2d = (Graphics2D)g;
            int sizeX = r.getxPos();
            int sizeY = r.getyPos();
            int tamanyY = r.getySize();
            int tamanyX = r.getxSize();


            if(r.getTipus() == 0){
                //g2d.setColor(Color.RED);
                //g2d.drawRect(sizeX, sizeY, tamanyX, tamanyY);

                g2d.setColor(new Color(31, 31, 31));
                g2d.fillRect(sizeX, sizeY, tamanyX, tamanyY);
            }else{
                //g2d.setColor(Color.RED);
                //g2d.drawRect(sizeX, sizeY, tamanyX, tamanyY);

                g2d.setColor(Color.orange);
                g2d.fillRect(sizeX, sizeY, tamanyX, tamanyY);
            }



        }
    }
}