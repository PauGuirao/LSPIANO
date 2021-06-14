package view;

import controller.EditController;
import controller.MainViewController;
import model.Song;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

/**
 * Clase que mostra la EditView i disposa tant d'un GridBagConstraint com de diversos JPanels, JButtons i JLabels
 * per poder controlar totes les funcionalitats de l'editView.
 * @version 0.5 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class EditView extends JFrame {
    private JMenuBar jmbar;
    private JPanel jpgbpanelL;
    private JPanel jpgbpanelR;
    private JButton jbdelete;
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
    private JMenuItem jmespai;
    private JMenuItem jmespai2;
    private JButton im;
    private EditController controller;
    private JMenuItem jmedita;
    //el grid de publiques
    private JPanel gridSongs;
    private LinkedList<JButton> songButtons;

    public EditView() {
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setTitle("Main View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42, 41, 41));
        getContentPane().setLayout(new GridLayout(1, 2));
        generateConstants();
        controller = null;

        songButtons = new LinkedList<>();
        list_type = 0;
        createMenuBar();
        create_left_zone();
        create_right_zone();
    }

    private void generateConstants() {
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(hGap, vGap + 10, hGap, vGap + 10);


        gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc2.insets = new Insets(hGap - 80, vGap + 10, hGap, vGap + 10);
    }

    private void createMenuBar() {
        jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100,80));
        jmbar.setBackground(new Color(33,33,33));
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
        /*
        jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100, 80));
        jmbar.setBackground(new Color(33, 33, 33));
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
         */
    }

    public JMenuItem add_button_menuBar(String name) {
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(250,80));
        fileMenu.setBackground(new Color(42,42,42));
        fileMenu.setFont(new Font("Arial",Font.BOLD,20));
        fileMenu.setForeground(Color.ORANGE);
        fileMenu.setIconTextGap(65);
        return fileMenu;
        /*
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(200, 80));
        fileMenu.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.setBackground(new Color(42, 42, 42));
        fileMenu.setForeground(Color.ORANGE);
        fileMenu.setIconTextGap(65);
        return fileMenu;
         */
    }

    public JMenu add_user_menuBar(String name) {
        JMenu fileMenu = new JMenu(name);
        fileMenu.setPreferredSize(new Dimension(200,80));
        fileMenu.setFont(new Font("Arial",Font.BOLD,20));
        fileMenu.setForeground(Color.white);
        fileMenu.setIconTextGap(65);
        fileMenu.setHorizontalAlignment(SwingConstants.RIGHT);
        return fileMenu;
        /*
        JMenu fileMenu = new JMenu(name);
        fileMenu.setPreferredSize(new Dimension(200, 80));
        fileMenu.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.setForeground(Color.white);
        fileMenu.setIconTextGap(65);
        fileMenu.setHorizontalAlignment(SwingConstants.RIGHT);
        return fileMenu;
         */
    }

    public void create_left_zone() {
        jpgbpanelL = new JPanel(new GridBagLayout());
        jpgbpanelL.setBackground(new Color(42, 41, 41));
        Border border = new LineBorder(Color.ORANGE, 1, false);

        im = new JButton("");
        im.setBorder(border);
        im.setBackground(new Color(32, 31, 31));


        jbdelete = new JButton("DELETE");
        jbdelete.setBackground(new Color(249, 52, 52));
        jbdelete.setFont(new Font("Arial", Font.BOLD, 30));

        jbdelete.setBorder(border);
        addComp(jpgbpanelL, im, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.33, 0.66);
        addComp(jpgbpanelL, jbdelete, 1, 1, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        getContentPane().add(jpgbpanelL);
    }

    public void create_right_zone() {
        jpgbpanelR = new JPanel(new GridBagLayout());
        jpgbpanelR.setBackground(new Color(42, 41, 41));
        Border border = new LineBorder(Color.ORANGE, 1, false);

        if (list_type == 0) {
            create_public_list();
        }
        getContentPane().add(jpgbpanelR);
    }

    public void create_public_list() {
        gridSongs = new JPanel(new GridLayout(100, 1, hGap, vGap));
        gridSongs.setBackground(new Color(33, 33, 33));
        Border border = new LineBorder(Color.ORANGE, 1, false);
        gridSongs.setBorder(border);
        JScrollPane scrollPane = new JScrollPane(gridSongs);
        addComp2(jpgbpanelR, scrollPane, 0, 1, 2, 1, GridBagConstraints.BOTH, 0.3, 0.9);
    }

    public JButton create_song_button(String nom, String durada) {
        Border border = new LineBorder(Color.ORANGE, 1, false);
        JButton jb = new JButton(nom);
        jb.setPreferredSize(new Dimension(100, 60));
        jb.setHorizontalAlignment(SwingConstants.LEFT);
        jb.setBackground(new Color(42, 41, 41));
        jb.setFont(new Font("Arial", Font.BOLD, 20));
        jb.setBorder(border);
        jb.setForeground(Color.white);
        return jb;
    }


    public void add_public_songs(LinkedList<Song> songs) {
        gridSongs.removeAll();
        for (Song s : songs) {
            if(!s.getNom().equals("Midi1") && !s.getNom().equals("Midi2") && !s.getNom().equals("Midi3")){
                JButton jb = create_song_button(s.getNom(), "aa");
                songButtons.add(jb);
                gridSongs.add(jb);
            }

        }
        if (controller != null) {
            registerSongButtons(controller);
        }
        gridSongs.revalidate();
        gridSongs.repaint();
    }

    public void unselectSong(){
        im.setText("");
        im.setFont(new Font("Arial", Font.BOLD, 35));
        im.setForeground(Color.white);
        im.revalidate();
        im.repaint();
    }


    public void addSelectedSong(String nom) {
        im.setText(nom);
        im.setFont(new Font("Arial", Font.BOLD, 35));
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

    public void registerController(EditController editController) {
        controller = editController;
        this.jbdelete.setActionCommand("DELETE");
        this.jbdelete.addActionListener(editController);
        this.jmhome.setActionCommand("SERVER");
        this.jmhome.addActionListener(editController);
        this.jmitocar.setActionCommand("HISTORIC");
        this.jmitocar.addActionListener(controller);
        this.jmespai.setActionCommand("TOP");
        this.jmespai.addActionListener(controller);
        this.jmespai2.setActionCommand("NEW");
        this.jmespai2.addActionListener(controller);
        registerSongButtons(editController);
    }

    public void registerSongButtons(EditController editController) {
        for (JButton jb : songButtons) {
            jb.setActionCommand("SONGBUTTON");
            jb.addActionListener(editController);
        }
    }
}
