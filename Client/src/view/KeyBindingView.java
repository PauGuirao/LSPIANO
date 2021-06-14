/**
 * Vista referent al canvi de controls del teclat (Utilitzem diferents JLabels i JTextFields i un JButton)
 * @version 2.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.HistoricController;
import controller.KeyBindingController;
import controller.LoginController;
import model.JTextFieldLimit;
import model.Manager;
import model.Network.NetworkManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;

public class KeyBindingView extends JFrame {


    private LinkedList<JTextField> jtList = new LinkedList<>();
    private LinkedList<JLabel> jlList = new LinkedList<>();

    private KeyBindingController controller;
    private JButton jbsave;

    public KeyBindingView () {
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setTitle("Key Binding View");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(null);
        createTextFiles();
        createButton();
    }

    public void createTextFiles () {


        create_key_label(100,20,300,50,"C3");
        create_key_field(160,27,100,30);

        create_key_label(100,60,300,50,"C3#");
        create_key_field(160,67,100,30);

        create_key_label(100,100,300,50,"D3");
        create_key_field(160,107,100,30);

        create_key_label(100,140,300,50,"D3#");
        create_key_field(160,147,100,30);

        create_key_label(100,180,300,50,"E3");
        create_key_field(160,187,100,30);

        create_key_label(100,220,300,50,"F3");
        create_key_field(160,227,100,30);

        create_key_label(100,260,300,50,"F3#");
        create_key_field(160,267,100,30);

        create_key_label(100,300,300,50,"G3");
        create_key_field(160,307,100,30);

        create_key_label(100,340,300,50,"G3#");
        create_key_field(160,347,100,30);

        create_key_label(100,380,300,50,"A3");
        create_key_field(160,387,100,30);

        create_key_label(100,420,300,50,"A3#");
        create_key_field(160,427,100,30);

        create_key_label(100,460,300,50,"B3");
        create_key_field(160,467,100,30);

        create_key_label(100,500,300,50,"C4");
        create_key_field(160,507,100,30);



        create_key_label(350,20,300,50,"C4#");
        create_key_field(410,27,100,30);

        create_key_label(350,60,300,50,"D4");
        create_key_field(410,67,100,30);

        create_key_label(350,100,300,50,"D4#");
        create_key_field(410,107,100,30);

        create_key_label(350,140,300,50,"E4");
        create_key_field(410,147,100,30);

        create_key_label(350,180,300,50,"F4");
        create_key_field(410,187,100,30);

        create_key_label(350,220,300,50,"F4#");
        create_key_field(410,227,100,30);

        create_key_label(350,260,300,50,"G4");
        create_key_field(410,267,100,30);

        create_key_label(350,300,300,50,"G4#");
        create_key_field(410,307,100,30);

        create_key_label(350,340,300,50,"A4");
        create_key_field(410,347,100,30);

        create_key_label(350,380,300,50,"A4#");
        create_key_field(410,387,100,30);

        create_key_label(350,420,300,50,"B4");
        create_key_field(410,427,100,30);

    }

    public void createButton () {
        jbsave = new JButton();
        jbsave.setBounds(650, 260, 250, 50);
        jbsave.setBackground(new Color(245,182,12));
        jbsave.setText("Save");
        jbsave.setHorizontalAlignment(SwingConstants.CENTER);
        jbsave.setFont(new Font("Arial",Font.BOLD,20));
        getContentPane().add(jbsave);
    }

    public void create_key_label(int x,int y,int w,int h,String text){
        JLabel jl = new JLabel();
        jl.setBounds(x,y,w,h);
        jl.setFont(new Font("Arial",Font.PLAIN,20));
        jl.setForeground(new Color(100,100,100));
        jl.setText(text);
        jlList.add(jl);
        getContentPane().add(jl);
    }

    public void create_key_field(int x,int y,int w,int h){
        JTextField jt = new JTextField();
        jt.setBounds(x,y,w,h);
        jt.setDocument(new JTextFieldLimit(1));
        jt.setBackground(new Color(64,64,64));
        jt.setFont(new Font("Arial", Font.PLAIN,20));
        jt.setForeground(new Color(112,112,112));
        jtList.add(jt);
        getContentPane().add(jt);
    }


    public void registerController(KeyBindingController keyBindingController) {
        controller = keyBindingController;
        this.jbsave.setActionCommand("SAVE");
        this.jbsave.addActionListener(keyBindingController);

    }

    public String getKeysString(){
        String[] keysChars = new String[26];
        for(int i = 0;i < jtList.size();i++){
            keysChars[i] = jtList.get(i).getText();
        }
        return String.join("",keysChars);
    }
    public void setKeys(String keys){
        System.out.println(keys);
        for(int i = 0; i < 24;i++){
            jtList.get(i).setText(String.valueOf(keys.charAt(i)));
        }
    }

}


