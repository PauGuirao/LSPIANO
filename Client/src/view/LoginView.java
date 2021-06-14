/**
 * Vista referent al login a l'aplicació (Utilitzem dos JTextField, dos JButton i dos JLabel)
 * @version 2.8 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {
    private JLabel jltitol;
    private JLabel jlerror;
    private JTextField jtuser;
    private JTextField jtpassword;
    private JButton jblogin;
    private JButton jbregister;

    public LoginView(){
        setSize(500,650);
        setLocationRelativeTo(null);
        setTitle("Main View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(null);
        createTextFiles();
        createButtons();
    }

    public void createTextFiles(){
        jltitol = new JLabel();
        jtuser = new JTextField();
        jtpassword = new JTextField();
        jblogin = new JButton();
        jbregister = new JButton();
        jlerror = new JLabel();

        jlerror.setBounds(125,180,300,50);
        jlerror.setFont(new Font("Arial",Font.PLAIN,20));
        jlerror.setForeground(new Color(100,100,100));

        jtuser.setBounds(100,250,300,50);
        jtuser.setBackground(new Color(64,64,64));
        jtuser.setFont(new Font("Arial",Font.PLAIN,20));
        jtuser.setForeground(new Color(112,112,112));
        TextPrompt placeHolder = new TextPrompt("Email Adress/Nick",jtuser);
        Border line = BorderFactory.createLineBorder(new Color(245,182,12));
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        jtuser.setBorder(border);

        jtpassword.setBounds(100,350,300,50);
        jtpassword.setBackground(new Color(64,64,64));
        jtpassword.setFont(new Font("Arial",Font.PLAIN,20));
        jtpassword.setForeground(new Color(112,112,112));
        TextPrompt placeHolder2 = new TextPrompt("Password",jtpassword);
        jtpassword.setBorder(border);

        jltitol.setIcon(new ImageIcon("Images/1.jpg"));
        jltitol.setBounds(100,45,300,140);

        getContentPane().add(jtuser);
        getContentPane().add(jtpassword);
        getContentPane().add(jltitol);
        getContentPane().add(jlerror);
    }
    public void createButtons(){
        //jltitol.setIcon(new ImageIcon("Images/1.png"));
        //jltitol.setBounds(100,50,300,150);


        jblogin.setBounds(100,450,300,50);
        jblogin.setBackground(new Color(245,182,12));
        jblogin.setText("Login");
        jblogin.setHorizontalAlignment(SwingConstants.CENTER);
        jblogin.setFont(new Font("Arial",Font.BOLD,20));


        jbregister.setBounds(270,510,150,25);
        jbregister.setBorderPainted(false);
        jbregister.setBackground(new Color(42,41,41));
        jbregister.setText("Create account");
        jbregister.setHorizontalAlignment(SwingConstants.CENTER);
        jbregister.setFont(new Font("Arial",Font.BOLD,15));
        jbregister.setForeground(new Color(71,71,71));

        getContentPane().add(jblogin);
        getContentPane().add(jbregister);
    }

    public void registerController(LoginController loginController) {
        this.jblogin.setActionCommand("Log in");    //al boto login li assignem una accio que es diu Log in
        this.jbregister.setActionCommand("Register in");
        this.jblogin.addActionListener(loginController);
        this.jbregister.addActionListener(loginController);
    }

    public void repaintLabels(){
        jtuser.setText("");
        jtpassword.setText("");
        jtuser.revalidate();
        jtpassword.revalidate();
        jtuser.repaint();
        jtpassword.repaint();
    }

    public String getNickname(){
        return jtuser.getText();
    }
    public String getPassword(){
        return jtpassword.getText();
    }
    public void changeErrorText(){
        jlerror.setText("Credencials incorrectes!");
        jlerror.revalidate();
        jlerror.repaint();
    }

}
