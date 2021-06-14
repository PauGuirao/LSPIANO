/**
 * Vista referent al registre de l'aplicació (Conte diferents JLabel, JTextField per cada camp i un JButton)
 * @version 2.8 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;


import controller.RegisterController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JFrame {

    private JLabel jltitol;
    private JLabel jlerror;
    private JTextField jtuser;
    private JTextField jtmail;
    private JTextField jtpassword;
    private JTextField jtpasswordCheck;
    private JButton jblogin;


    public RegisterView() {
        setSize(500,750);
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
        jtmail = new JTextField();
        jtpasswordCheck = new JTextField();
        jlerror = new JLabel();

        jlerror.setBounds(125,180,300,50);
        jlerror.setFont(new Font("Arial",Font.PLAIN,20));
        jlerror.setForeground(new Color(100,100,100));

        jtuser.setBounds(100,250,300,50);
        jtuser.setBackground(new Color(64,64,64));
        jtuser.setFont(new Font("Arial",Font.PLAIN,20));
        jtuser.setForeground(new Color(112,112,112));
        TextPrompt placeHolder = new TextPrompt("Nickname",jtuser);
        Border line = BorderFactory.createLineBorder(new Color(245,182,12));
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        jtuser.setBorder(border);

        jtmail.setBounds(100,325,300,50);
        jtmail.setBackground(new Color(64,64,64));
        jtmail.setFont(new Font("Arial",Font.PLAIN,20));
        jtmail.setForeground(new Color(112,112,112));
        TextPrompt placeHolder2 = new TextPrompt("Email Adress",jtmail);
        jtmail.setBorder(border);

        jtpassword.setBounds(100,400,300,50);
        jtpassword.setBackground(new Color(64,64,64));
        jtpassword.setFont(new Font("Arial",Font.PLAIN,20));
        jtpassword.setForeground(new Color(112,112,112));
        TextPrompt placeHolder3 = new TextPrompt("Password",jtpassword);
        jtpassword.setBorder(border);

        jtpasswordCheck.setBounds(100,475,300,50);
        jtpasswordCheck.setBackground(new Color(64,64,64));
        jtpasswordCheck.setFont(new Font("Arial",Font.PLAIN,20));
        jtpasswordCheck.setForeground(new Color(112,112,112));
        TextPrompt placeHolder4 = new TextPrompt("Repeat Password",jtpasswordCheck);
        jtpasswordCheck.setBorder(border);

        jltitol.setIcon(new ImageIcon("Images/1.png"));
        jltitol.setBounds(100,50,300,150);

        getContentPane().add(jtuser);
        getContentPane().add(jtmail);
        getContentPane().add(jtpasswordCheck);
        getContentPane().add(jtpassword);
        getContentPane().add(jltitol);
        getContentPane().add(jlerror);
    }
    public void createButtons(){
        jblogin.setBounds(100,575,300,50);
        jblogin.setBackground(new Color(245,182,12));
        jblogin.setText("Register");
        jblogin.setHorizontalAlignment(SwingConstants.CENTER);
        jblogin.setFont(new Font("Arial",Font.BOLD,20));
        jltitol.setIcon(new ImageIcon("Images/1.png"));
        jltitol.setBounds(100,50,300,150);


        getContentPane().add(jblogin);

    }

    public void registerController(RegisterController rController) {
        this.jblogin.setActionCommand("Register");
        this.jblogin.addActionListener(rController);
    }

    public String getNickname(){
        return jtuser.getText();
    }
    public String getPassword(){
        return jtpassword.getText();
    }
    public String getMail(){
        return jtmail.getText();
    }
    public String getPasswordCheck(){
        return jtpasswordCheck.getText();
    }

    public void changeErrorText(int numError){

        if (numError == 0) {
            jlerror.setText("Credencials incorrectes!");
        } else {
            jlerror.setText("Aquest usuari ja existeix!");
        }
        jlerror.revalidate();
        jlerror.repaint();
    }
}
