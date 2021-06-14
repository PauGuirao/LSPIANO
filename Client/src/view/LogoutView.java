/**
 * Vista referent al logout a l'aplicació (Utilitzem tres JButton i una JLabel)
 * @version 2.3 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.LogoutController;

import javax.swing.*;
import java.awt.*;

public class LogoutView extends JFrame {
    private JLabel jltitol;
    private JButton jbconfirm;
    private JButton jbcancel;
    private JButton jbdelete;

    public LogoutView() {
        setSize(600, 300);
        setLocationRelativeTo(null);
        setTitle("Logout View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(null);

        createLogoutView();
    }

    public void createLogoutView(){

        jltitol = new JLabel();
        jbconfirm = new JButton();
        jbcancel = new JButton();
        jbdelete = new JButton();

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

        jbdelete.setBounds(200,175,200,50);
        jbdelete.setBackground(new Color(42,41,41));
        jbdelete.setBorderPainted(false);
        jbdelete.setText("Eliminar Compte");
        jbdelete.setHorizontalAlignment(SwingConstants.CENTER);
        jbdelete.setFont(new Font("Arial",Font.BOLD,20));
        jbdelete.setForeground(new Color(71,71,71));

        jltitol.setBackground(new Color(245,182,12));
        jltitol.setBounds(225,30,200,50);
        jltitol.setText("Tancar sessió?");
        jltitol.setBackground(new Color(42,41,41));
        jltitol.setFont(new Font("Arial",Font.BOLD,20));
        jltitol.setHorizontalTextPosition(SwingConstants.CENTER);
        jltitol.setForeground(new Color(255, 255, 255));

        getContentPane().add(jltitol);
        getContentPane().add(jbconfirm);
        getContentPane().add(jbcancel);
        getContentPane().add(jbdelete);

    }

    public void registerController(LogoutController logoutController) {
        this.jbconfirm.setActionCommand("Confirmar");
        this.jbcancel.setActionCommand("Cancelar");
        this.jbdelete.setActionCommand("Eliminar Compte");
        this.jbconfirm.addActionListener(logoutController);
        this.jbcancel.addActionListener(logoutController);
        this.jbdelete.addActionListener(logoutController);

    }
}