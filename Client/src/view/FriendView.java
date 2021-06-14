/**
 * Vista referent a la pantalla d'amics (Conte una JMenuBar amb el seus JMenuItem referents, diferents JButton i JLabel, dos JPanel, i dos GridBagConstraints)
 * @version 3.5 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.FriendController;
import controller.MainController;
import model.Song;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.CompactNumberFormat;
import java.util.LinkedList;

public class FriendView extends JFrame {
    private JMenuBar jmbar;
    private JPanel jpgbpanelL;
    private JPanel jpgbpanelR;
    private JButton jbaddfriend;
    private JTextField jtaddfriend;
    private JButton jbdelete;
    private JButton jbpublic;
    private JButton jbprivate;
    private JButton jbfriends;
    private JLabel jlimage;

    private GridBagConstraints gbc;
    private GridBagConstraints gbc2;
    private final int hGap = 60;
    private final int vGap = 30;
    private int list_type;
    private JMenuItem jmhome;
    private JMenuItem jmtocar;
    private JMenuItem jmlogout;
    //el grid de friends
    private JPanel gridFriends;
    private JPanel gridSongs;
    private LinkedList<JButton> friendButtons;
    private FriendController controller;
    private LinkedList<JButton> songButtons;

    public FriendView() {
        setSize(1280,720);
        setLocationRelativeTo(null);
        setTitle("Friend View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(new GridLayout(1,2));
        generateConstants();
        friendButtons = new LinkedList<>();
        list_type = 0;
        songButtons = new LinkedList<>();
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
        jmtocar = add_button_menuBar("TOCAR");
        jmbar.add(jmtocar);
        JMenuItem jmamics = add_button_menuBar("AMICS");
        jmbar.add(jmamics);
        jmlogout = add_button_menuBar("LOGOUT");
        jmbar.add(jmlogout);
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

        jbfriends = new JButton("  ELS MEUS AMICS  ");
        jbfriends.setForeground(Color.white);
        jbfriends.setBackground(new Color(33,33,33));
        jbfriends.setFont(new Font("Arial",Font.BOLD,20));
        jbfriends.setBorder(border);

        JButton im = new JButton("MY FRIENDS");
        im.setBorder(border);
        im.setBackground(new Color(32,31,31));

        jbaddfriend = new JButton("  ADD FRIEND  ");
        jbaddfriend.setBackground(Color.ORANGE);
        jbaddfriend.setFont(new Font("Arial",Font.BOLD,30));
        jbaddfriend.setBorder(border);

        jtaddfriend = new JTextField();

        jtaddfriend.setBounds(100,250,300,50);
        jtaddfriend.setBackground(new Color(64,64,64));
        jtaddfriend.setFont(new Font("Arial",Font.PLAIN,20));
        jtaddfriend.setForeground(new Color(112,112,112));
        TextPrompt placeHolder = new TextPrompt("Nickname...",jtaddfriend);
        jtaddfriend.setBorder(border);

        getContentPane().add(jtaddfriend);
        create_friend_list();

        addComp(jpgbpanelL, jbfriends, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        addComp(jpgbpanelL, im, 0, 1, 1, 1, GridBagConstraints.BOTH, 0.33, 0.66);
        addComp(jpgbpanelL, jbaddfriend, 0, 2, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        addComp(jpgbpanelL, jtaddfriend, 1, 2, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        getContentPane().add(jpgbpanelL);
    }


    public void create_right_zone(){
        jpgbpanelR = new JPanel(new GridBagLayout());
        jpgbpanelR.setBackground(new Color(42,41,41));
        Border border = new LineBorder(Color.ORANGE, 1, false);

        jbpublic = new JButton("  CANÇONS  ");
        jbpublic.setForeground(Color.white);
        jbpublic.setBackground(new Color(33,33,33));
        jbpublic.setFont(new Font("Arial",Font.BOLD,20));
        jbpublic.setBorder(border);

        addComp(jpgbpanelR, jbpublic, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.33, 0.1);

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

    public void add_public_songs(LinkedList<Song> songs){
        gridSongs.removeAll();
        for (Song s:songs){
            JButton jb = create_song_button(s.getNom(),String.valueOf(s.getDurada()));
            songButtons.add(jb);
            gridSongs.add(jb);
        }
        if(controller != null){
            registerSongButtons(controller);
        }
        gridSongs.revalidate();
        gridSongs.repaint();
    }
    public void repaintGrid(){
        gridSongs.removeAll();
        gridSongs.revalidate();
        gridSongs.repaint();
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

    public void create_friend_list(){
        gridFriends = new JPanel(new GridLayout(10, 2, hGap, vGap));
        gridFriends.setBackground(new Color(33,33,33));
        Border border = new LineBorder(Color.ORANGE, 1, false);
        gridFriends.setBorder(border);
        JScrollPane scrollPane = new JScrollPane(gridFriends);
        addComp2(jpgbpanelL, scrollPane, 0, 1, 2, 1, GridBagConstraints.BOTH, 0.3, 0.9);
    }

    public JButton create_friend_button(String nom){
        System.out.println(nom);
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

    public void add_friends(LinkedList<User> friends){
        gridFriends.removeAll();
        for (User u:friends){
            System.out.println(u.getCodiAmics());
            JButton jb = create_friend_button(u.getNickname());
            friendButtons.add(jb);
            gridFriends.add(jb);
        }
        if(controller != null){
            registerFriendButtons(controller);
        }
        gridFriends.revalidate();
        gridFriends.repaint();
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

    public String getCodiAmic(){
        return jtaddfriend.getText();
    }

    public void registerController(FriendController friendController) {
        controller = friendController;
        this.jmhome.setActionCommand("HOME");
        this.jmhome.addActionListener(friendController);
        this.jmtocar.setActionCommand("TOCAR");
        this.jmtocar.addActionListener(friendController);
        this.jbaddfriend.setActionCommand("ADDFRIEND");
        this.jbaddfriend.addActionListener(friendController);
        this.jmlogout.setActionCommand("LOGOUT");
        this.jmlogout.addActionListener(friendController);
        registerFriendButtons(friendController);
        registerSongButtons(friendController);
    }
    public void registerFriendButtons(FriendController controller){
        for (JButton fb:friendButtons){
            fb.setActionCommand("FRIENDBUTTON");
            fb.addActionListener(controller);
        }
    }
    public void registerSongButtons(FriendController controller){
        for(JButton jb:songButtons){
            jb.setActionCommand("SONGBUTTON");
            jb.addActionListener(controller);
        }
    }

}

