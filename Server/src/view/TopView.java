package view;

import controller.TopController;
import model.TopElement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Vista que s'encarrega de mostrar el Top5 de cançons més reproduïdes. (Hereda de JFrame)
 * @version 0.8 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class TopView extends JFrame {
    private JMenuBar jmbar;
    private JMenuItem jmhome;
    private JMenuItem jmitocar;
    private JMenuItem jmedita;
    private JMenuItem jmespai;
    private JMenuItem jmespai2;
    private TopController controller;

    public TopView() {
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setTitle("Historic View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42, 41, 41));
        getContentPane().setLayout(new GridLayout(5, 1));
        createMenuBar();
    }

    private void createMenuBar() {
        jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100, 80));
        jmbar.setBackground(new Color(33, 33, 33));
        Border border = new LineBorder(Color.ORANGE, 2, false);
        jmbar.setBorder(border);

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
        fileMenu.setPreferredSize(new Dimension(250, 80));
        fileMenu.setBackground(new Color(42,42,42));
        fileMenu.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.setPreferredSize(new Dimension(200,80));
        fileMenu.setForeground(Color.ORANGE);
        fileMenu.setIconTextGap(65);
        return fileMenu;
    }

    public void add_top5(LinkedList<TopElement> top){
        int num = 0;
        for (int i = top.size()-1;i >= 0;i--){
            num++;
            JButton jb = new JButton(top.get(i).getName()+"                 "+top.get(i).getReproduccions());
            jb.setBackground(new Color(42,42,42));
            jb.setForeground(Color.orange);
            jb.setFont(new Font("Arial",Font.BOLD,30));
            getContentPane().add(jb);
            if(num == 5){
                break;
            }

        }
    }

    public void registerController(TopController topController) {
        controller = topController;
        this.jmhome.setActionCommand("SERVER");
        this.jmhome.addActionListener(controller);
        this.jmedita.setActionCommand("EDITA");
        this.jmedita.addActionListener(controller);
        this.jmitocar.setActionCommand("HISTORIC");
        this.jmitocar.addActionListener(controller);
        this.jmespai2.setActionCommand("NEW");
        this.jmespai2.addActionListener(controller);
    }
}
