package view;

import controller.ConfirmController;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que mostra la ConfirmView hedreda de JFrame per crear la vista.(Necesita diferents JButtons i actionEvents)
 * @version 1.3 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class ConfirmView extends JFrame {
    private JLabel jltitol;
    private JButton jbconfirm;
    private JButton jbcancel;

    public ConfirmView(){
        setSize(600, 250);
        setLocationRelativeTo(null);
        setTitle("Confirm View");
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
        jltitol.setText("Eliminar canco?");
        jltitol.setBackground(new Color(42,41,41));
        jltitol.setFont(new Font("Arial",Font.BOLD,20));
        jltitol.setHorizontalTextPosition(SwingConstants.CENTER);
        jltitol.setForeground(new Color(255, 255, 255));

        getContentPane().add(jltitol);
        getContentPane().add(jbconfirm);
        getContentPane().add(jbcancel);

    }

    public void registerController(ConfirmController confirmController) {
        this.jbconfirm.setActionCommand("Confirmar");
        this.jbcancel.setActionCommand("Cancelar");
        this.jbconfirm.addActionListener(confirmController);
        this.jbcancel.addActionListener(confirmController);
    }

}
