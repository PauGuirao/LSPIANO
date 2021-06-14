/**
 * Vista referent a l'eliminació de cançons (Conté dos JButton i una JLabel)
 * @version 2.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.DeleteSongController;

import javax.swing.*;
import java.awt.*;

public class DeleteSongView extends JFrame {
    private JLabel jltitol;
    private JButton jbconfirm;
    private JButton jbcancel;

    public DeleteSongView(){
        setSize(600, 250);
        setLocationRelativeTo(null);
        setTitle("Confirm View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(null);

        createDeleteView();
    }

    public void createDeleteView(){
        jbconfirm = new JButton();
        jbcancel = new JButton();

        jbconfirm.setBounds(75,100,200,50);
        jbconfirm.setBackground(new Color(245,182,12));
        jbconfirm.setText("Confirmar");
        jbconfirm.setHorizontalAlignment(SwingConstants.CENTER);
        jbconfirm.setFont(new Font("Arial",Font.BOLD,20));

        jbcancel.setBounds(325,100,200,50);
        jbcancel.setBackground(new Color(245,182,12));
        jbcancel.setText("Cancelar");
        jbcancel.setHorizontalAlignment(SwingConstants.CENTER);
        jbcancel.setFont(new Font("Arial",Font.BOLD,20));


        getContentPane().add(jbconfirm);
        getContentPane().add(jbcancel);

    }

    public void setSongText(String songName){
        jltitol = new JLabel();
        jltitol.revalidate();
        jltitol.repaint();
        jltitol.setBackground(new Color(245,182,12));
        jltitol.setBounds(160,30,400,50);
        jltitol.setBackground(new Color(42,41,41));
        jltitol.setFont(new Font("Arial",Font.BOLD,20));
        jltitol.setHorizontalTextPosition(SwingConstants.CENTER);
        jltitol.setForeground(new Color(255, 255, 255));
        jltitol.setText("Vols eliminar la canco: "+songName+"?");
        getContentPane().add(jltitol);
    }
    public void registerController(DeleteSongController deleteSongController) {
        this.jbconfirm.setActionCommand("DELETE");
        this.jbconfirm.addActionListener(deleteSongController);
        this.jbcancel.setActionCommand("CANCEL");
        this.jbcancel.addActionListener(deleteSongController);

    }


}
