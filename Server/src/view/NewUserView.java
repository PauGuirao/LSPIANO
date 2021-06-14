package view;

import controller.NewUserController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Classe mostra finestra d'un nou usuari, funció opional, i genera els diferents panells.
 * (Hereda de la JFrame per funcionar.)
 * @version 0.21 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class NewUserView extends JFrame {
    private JMenuBar jmbar;
    private JMenuItem jmhome;
    private JMenuItem jmitocar;
    private JMenuItem jmedita;
    private JMenuItem jmespai;
    private JMenuItem jmespai2;
    private NewUserController controller;
    private JLabel jlmissatge;

    public NewUserView() {
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setTitle("Historic View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42, 41, 41));
        getContentPane().setLayout(null);
        createMenuBar();
    }

    private void createMenuBar() {
        jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100, 80));
        jmbar.setBackground(new Color(33, 33, 33));
        Border border = new LineBorder(Color.ORANGE, 2, false);
        jmbar.setBorder(border);

        jlmissatge = new JLabel();
        jlmissatge.setBounds(325,180,600,50);
        jlmissatge.setFont(new Font("Arial",Font.PLAIN,20));
        jlmissatge.setForeground(new Color(100,100,100));
        jlmissatge.setText("Dedicatòria especial als becaris i al Jordi per la vostra ajuda");
        getContentPane().add(jlmissatge);

        jmhome = add_button_menuBar("SERVER");
        jmbar.add(jmhome);
        jmitocar = add_button_menuBar("HISTORIC");
        jmbar.add(jmitocar);
        jmedita = add_button_menuBar("EDITA CANCONS");
        jmbar.add(jmedita);
        jmespai = add_button_menuBar("TOP 5 CANCONS");
        jmbar.add(jmespai);
        jmespai2 = add_button_menuBar("NOU USUARI");
        jmbar.add(jmespai2);
        setJMenuBar(jmbar);
    }

    public JMenuItem add_button_menuBar(String name) {
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setBackground(new Color(42,42,42));
        fileMenu.setPreferredSize(new Dimension(250, 80));
        fileMenu.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.setForeground(Color.ORANGE);
        fileMenu.setIconTextGap(65);
        return fileMenu;
    }

    public void registerController(NewUserController newUserController) {
        controller = newUserController;
        this.jmhome.setActionCommand("SERVER");
        this.jmhome.addActionListener(controller);
        this.jmedita.setActionCommand("EDITA");
        this.jmedita.addActionListener(controller);
        this.jmitocar.setActionCommand("HISTORIC");
        this.jmitocar.addActionListener(controller);
        this.jmespai.setActionCommand("TOP");
        this.jmespai.addActionListener(controller);
    }
}
