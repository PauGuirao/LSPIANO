/**
 * Vista referent a la pantalla Home de l'plicació (Conte una JMenuBar amb el seus JMenuItem referents, diferents JButton i JLabel, dos JPanel, i dos GridBagConstraints)
 * @version 3.1 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.MainController;
import model.Song;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class MainView extends JFrame {
    private JMenuBar jmbar;
    private JPanel jpgbpanelL;
    private JPanel jpgbpanelR;
    private JButton jbplay;
    private JButton jbpublic;
    private JButton jbprivate;
    private JButton[] jbsong;
    private JLabel jlimage;
    private JLabel jlsongName;
    private JLabel jlsongDuration;
    private GridBagConstraints gbc;
    private GridBagConstraints gbc2;
    private final int hGap = 60;
    private final int vGap = 30;
    private int list_type;
    private JMenuItem jmhome;
    private JMenuItem jmitocar;
    private JMenuItem jmamics;
    private JMenuItem jmlogout;
    private JButton im;
    private MainController controller;
    //el grid de publiques
    private JPanel gridSongs;
    private LinkedList<JButton> songButtons;

    public MainView() {
        setSize(1280,720);
        setLocationRelativeTo(null);
        setTitle("Main View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(new GridLayout(1,2));
        generateConstants();
        controller = null;

        songButtons = new LinkedList<>();
        list_type = 0;
        createMenuBar();
        create_left_zone();
        create_right_zone();
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

        jmhome = add_button_menuBar("HOME");
        jmbar.add(jmhome);
        jmitocar = add_button_menuBar("TOCAR");
        jmbar.add(jmitocar);
        jmamics = add_button_menuBar("AMICS");
        jmbar.add(jmamics);
        jmlogout = add_button_menuBar("LOGOUT");
        jmbar.add(jmlogout);

        JMenuItem jmespai = add_button_menuBar("");
        jmbar.add(jmespai);
        JMenuItem jmespai2 = add_button_menuBar("");
        jmbar.add(jmespai2);
        JMenuItem jmusername = add_user_menuBar("          pepito");
        jmbar.add(jmusername);
        setJMenuBar(jmbar);

    }

    public JMenuItem add_button_menuBar(String name){
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(200,80));
        fileMenu.setFont(new Font("Arial",Font.BOLD,20));
        fileMenu.setBackground(new Color(42,42,42));
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

        im = new JButton("");
        im.setBorder(border);
        im.setBackground(new Color(32,31,31));

        jbplay = new JButton("  PLAY  ");
        jbplay.setBackground(Color.ORANGE);
        jbplay.setFont(new Font("Arial",Font.BOLD,30));
        jbplay.setBorder(border);

        addComp(jpgbpanelL, im, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.33, 0.66);
        addComp(jpgbpanelL, jbplay, 0, 1, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        getContentPane().add(jpgbpanelL);
    }

    public void create_right_zone(){
        jpgbpanelR = new JPanel(new GridBagLayout());
        jpgbpanelR.setBackground(new Color(42,41,41));
        Border border = new LineBorder(Color.ORANGE, 1, false);

        jbpublic = new JButton("  PUBLIQUES  ");
        jbpublic.setForeground(Color.white);
        jbpublic.setBackground(new Color(33,33,33));
        jbpublic.setFont(new Font("Arial",Font.BOLD,20));
        jbpublic.setBorder(border);

        jbprivate = new JButton("    PRIVADES   ");
        jbprivate.setForeground(Color.white);
        jbprivate.setBackground(new Color(33,33,33));
        jbprivate.setFont(new Font("Arial",Font.BOLD,20));
        jbprivate.setBorder(border);

        addComp(jpgbpanelR, jbpublic, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.33, 0.1);
        addComp(jpgbpanelR, jbprivate, 1, 0, 1, 1, GridBagConstraints.BOTH, 0.33, 0.1);

        if(list_type == 0){
            create_public_list();
        }
        getContentPane().add(jpgbpanelR);
    }

    public void create_public_list(){
        gridSongs = new JPanel(new GridLayout(100, 1, hGap, vGap));
        gridSongs.setBackground(new Color(33,33,33));
        Border border = new LineBorder(Color.ORANGE, 1, false);
        gridSongs.setBorder(border);
        JScrollPane scrollPane = new JScrollPane(gridSongs);
        addComp2(jpgbpanelR, scrollPane, 0, 1, 2, 1, GridBagConstraints.BOTH, 0.3, 0.9);
    }

    public JButton create_song_button(String nom,String durada){
        Border border = new LineBorder(Color.ORANGE, 1, false);
        JButton jb = new JButton(nom);
        jb.setPreferredSize(new Dimension(100,60));
        jb.setHorizontalAlignment(SwingConstants.LEFT);
        jb.setBackground(new Color(42,41,41));
        jb.setFont(new Font("Arial",Font.BOLD,20));
        jb.setBorder(border);
        jb.setForeground(Color.white);
        return jb;
    }


    public void add_public_songs(LinkedList<Song> songs){
        gridSongs.removeAll();
        for (Song s:songs){
            if(s.getIsPublic()){
                JButton jb = create_song_button(s.getNom(),String.valueOf(s.getDurada()));
                songButtons.add(jb);
                gridSongs.add(jb);
            }
        }
        if(controller != null){
            registerSongButtons(controller);
        }
        gridSongs.revalidate();
        gridSongs.repaint();
    }

    public void add_private_songs(LinkedList<Song> songs){
        gridSongs.removeAll();
        for (Song s:songs){
            if(!s.getIsPublic()){
                JButton jb = create_song_button(s.getNom(),String.valueOf(s.getDurada()));
                songButtons.add(jb);
                gridSongs.add(jb);
            }
        }
        if(controller != null){
            registerSongButtons(controller);
        }
        gridSongs.revalidate();
        gridSongs.repaint();
    }
    public void addSelectedSong(String nom){
        im.setText(nom);
        im.setFont(new Font("Arial",Font.BOLD,35));
        im.setForeground(Color.white);
        im.revalidate();
        im.repaint();
    }
    public void unselectSong(){
        im.setText("");
        im.setFont(new Font("Arial",Font.BOLD,35));
        im.setForeground(Color.white);
        im.revalidate();
        im.repaint();
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

    private void addComp2(JPanel panel, JComponent comp, int x, int y, int gWidth, int gHeight, int fill, double weightx, double weighty) {
        gbc2.gridx = x;
        gbc2.gridy = y;
        gbc2.gridwidth = gWidth;
        gbc2.gridheight = gHeight;
        gbc2.fill = fill;
        gbc2.weightx = weightx;
        gbc2.weighty = weighty;

        panel.add(comp, gbc2);
    }
    public void registerController(MainController mainController) {
        controller = mainController;
        this.jmhome.setActionCommand("HOME");
        this.jmhome.addActionListener(mainController);
        this.jbplay.setActionCommand("PLAY");
        this.jbplay.addActionListener(mainController);
        this.jmitocar.setActionCommand("TOCAR");
        this.jmitocar.addActionListener(mainController);
        this.jbpublic.setActionCommand("SHOWPUBLIC");
        this.jbprivate.setActionCommand("SHOWPRIVATE");
        this.jbpublic.addActionListener(mainController);
        this.jbprivate.addActionListener(mainController);
        this.jmamics.setActionCommand("AMICS");
        this.jmamics.addActionListener(mainController);
        this.jmlogout.setActionCommand("LOGOUT");
        this.jmlogout.addActionListener(mainController);
        registerSongButtons(mainController);
    }

    public void registerSongButtons(MainController mainController){
        for(JButton jb:songButtons){
            jb.setActionCommand("SONGBUTTON");
            jb.addActionListener(mainController);
        }
    }

}
