/**
 * Vista referent a eliminar el compte (Conte dos JButton i una JLabel)
 * @version 1.9 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.DeleteController;

import javax.swing.*;
import java.awt.*;

public class DeleteView extends JFrame {
        private JLabel jltitol;
        private JButton jbconfirm;
        private JButton jbcancel;

        public DeleteView(){
            setSize(600, 250);
            setLocationRelativeTo(null);
            setTitle("Delete View");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getContentPane().setBackground(new Color(42,41,41));
            getContentPane().setLayout(null);

            createDeleteView();
        }

        public void createDeleteView(){
            jltitol = new JLabel();
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


            jltitol.setBackground(new Color(245,182,12));
            jltitol.setBounds(160,30,300,50);
            jltitol.setText("Vols eliminar el teu compte?");
            jltitol.setBackground(new Color(42,41,41));
            jltitol.setFont(new Font("Arial",Font.BOLD,20));
            jltitol.setHorizontalTextPosition(SwingConstants.CENTER);
            jltitol.setForeground(new Color(255, 255, 255));

            getContentPane().add(jltitol);
            getContentPane().add(jbconfirm);
            getContentPane().add(jbcancel);

        }

        public void registerController(DeleteController DeleteController) {
            this.jbconfirm.setActionCommand("Confirmar");
            this.jbcancel.setActionCommand("Cancelar");
            this.jbconfirm.addActionListener(DeleteController);
            this.jbcancel.addActionListener(DeleteController);
        }

    }
