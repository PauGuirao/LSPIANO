package view;

import controller.MainViewController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Classe mostra la MainView i crea els diferents panells del servidor (Hereda de la JFrame per funcionar.)
 * @version 0.21 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class MainView extends JFrame {
    private JMenuBar jmbar;
    private JPanel jpgbpanelL;
    private JPanel jpgbpanelR;
    private JButton jbplay;
    private JButton jbdelete;
    private JButton[] jbsong;
    private GridBagConstraints gbc;
    private GridBagConstraints gbc2;
    private final int hGap = 60;
    private final int vGap = 30;
    private int list_type;
    private JMenuItem jmedita;
    private JMenuItem jmhome;
    private JMenuItem jmtocar;
    private JMenuItem jmespai;
    private JMenuItem jmespai2;

    public MainView() {
        setSize(1280,720);
        setLocationRelativeTo(null);
        setTitle("Main Server View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(new GridLayout(1,2));
        generateConstants();

        jbsong = new JButton[16];
        list_type = 0;
        createMenuBar();
        create_left_zone();
    }

    private void generateConstants(){
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(hGap, vGap+10, hGap, vGap+10);
        gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc2.insets = new Insets(hGap-80, vGap+10, hGap, vGap+10);
    }

    private void createMenuBar() {
        jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100,80));
        jmbar.setBackground(new Color(33,33,33));
        Border border = new LineBorder(Color.ORANGE, 2, false);
        jmbar.setBorder(border);

        jmhome = add_button_menuBar("SERVER");
        jmbar.add(jmhome);
        jmtocar = add_button_menuBar("HISTORIC");
        jmbar.add(jmtocar);
        jmedita = add_button_menuBar("EDITA CANCONS");
        jmbar.add(jmedita);
        jmespai = add_button_menuBar("TOP 5 CANCONS");
        jmbar.add(jmespai);
        jmespai2 = add_button_menuBar("NOU USUARI");
        jmbar.add(jmespai2);
        setJMenuBar(jmbar);
    }

    public JMenuItem add_button_menuBar(String name){
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(250,80));
        fileMenu.setBackground(new Color(42,42,42));
        fileMenu.setFont(new Font("Arial",Font.BOLD,20));
        fileMenu.setForeground(Color.ORANGE);
        fileMenu.setIconTextGap(65);
        return fileMenu;
    }

    public JMenu add_user_menuBar(String name){
        JMenu fileMenu = new JMenu(name);
        fileMenu.setPreferredSize(new Dimension(200,80));
        fileMenu.setFont(new Font("Arial",Font.BOLD,20));
        fileMenu.setForeground(Color.white);
        fileMenu.setIconTextGap(65);
        fileMenu.setHorizontalAlignment(SwingConstants.RIGHT);
        return fileMenu;
    }

    public void create_left_zone(){
        jpgbpanelL = new JPanel(new GridBagLayout());
        jpgbpanelL.setBackground(new Color(42,41,41));
        Border border = new LineBorder(Color.ORANGE, 1, false);

        JButton im = new JButton("BENVINGUT AL SERVER!");
        im.setFont(new Font("Arial",Font.BOLD,30));
        im.setBorder(border);
        im.setBackground(new Color(32,31,31));

        jbplay = new JButton("START");
        jbplay.setBackground(Color.ORANGE);
        jbplay.setFont(new Font("Arial",Font.BOLD,30));
        jbplay.setBorder(border);

        jbdelete = new JButton("STOP");
        jbdelete.setBackground(new Color(249,52,52));
        jbdelete.setFont(new Font("Arial",Font.BOLD,30));

        jbdelete.setBorder(border);
        addComp(jpgbpanelL, im, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.33, 0.66);
        addComp(jpgbpanelL, jbplay, 0, 1, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        addComp(jpgbpanelL, jbdelete, 1, 1, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        getContentPane().add(jpgbpanelL);
    }

    private void addComp(JPanel panel, JComponent comp, int x, int y, int gWidth, int gHeight, int fill, double weightx, double weighty) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gWidth;
        gbc.gridheight = gHeight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        panel.add(comp, gbc);
    }

    public void registerController(MainViewController mainViewController) {
        this.jbplay.setActionCommand("START");
        this.jbplay.addActionListener(mainViewController);
        this.jbdelete.setActionCommand("STOP");
        this.jbdelete.addActionListener(mainViewController);
        this.jmedita.setActionCommand("EDITA");
        this.jmedita.addActionListener(mainViewController);
        this.jmtocar.setActionCommand("HISTORIC");
        this.jmtocar.addActionListener(mainViewController);
        this.jmespai.setActionCommand("TOP");
        this.jmespai.addActionListener(mainViewController);
        this.jmespai2.setActionCommand("NEW");
        this.jmespai2.addActionListener(mainViewController);
    }
}