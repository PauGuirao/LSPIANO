/**
 * Vista referent a la reproducció de les cançons (Conte una JMenuBar amb el seus JMenuItem referents, un JButton, un JLPanel, WhiteKeys i BlackKeys)
 * @version 4.9 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.PlayController;
import controller.RegisterController;
import model.GridLayout2;
import controller.PianoController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayView extends JFrame{

    private GridBagConstraints gbc;
    //view del piano
    private final int OCTAVES = 2; // change as desired
    private WhiteKey[] whites = new WhiteKey [7 * OCTAVES + 1];
    private BlackKey[] blacks = new BlackKey [5 * OCTAVES];
    private JPanel jpgbpanelL;
    private NoteHolderPanel jpnotes;
    private PlayController playController;
    private JButton muteButton;
    private JMenuItem jmmute;
    private JMenuItem jmclose;
    private JMenuItem jmstart;

    public PlayView() {
        setSize(1240,1080);
        setLocationRelativeTo(null);
        setTitle("Main View");
        generateConstants();
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(new GridLayout(2,1));
        createMenuBar();
        drawView();
    }

    private void generateConstants(){
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        int vGap = 20;
        int hGap = 20;
        gbc.insets = new Insets(hGap, vGap, hGap, vGap);

    }

    private void createMenuBar() {
        JMenuBar jmbar = new JMenuBar();
        jmbar.setPreferredSize(new Dimension(100,80));
        jmbar.setBackground(new Color(33,33,33));
        Border border = new LineBorder(Color.ORANGE, 2, false);
        jmbar.setBorder(border);

        jmstart = add_button_menuBar("START");
        jmbar.add(jmstart);
        jmmute = add_button_menuBar("MUTE");
        jmbar.add(jmmute);
        jmclose = add_button_menuBar("CLOSE");
        jmclose.setBackground(new Color(249, 52, 52));
        jmbar.add(jmclose);
        setJMenuBar(jmbar);

    }

    public JMenuItem add_button_menuBar(String name){
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(200,80));
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



    public void create_piano(){
        JPanel contentPane = new JPanel(null)
        {
            @Override
            public Dimension getPreferredSize()
            {
                int count = getComponentCount();
                Component last = getComponent(count - 1);
                Rectangle bounds = last.getBounds();

                int width = 10 + bounds.x + bounds.width;
                int height = 10 + bounds.y + bounds.height;

                return new Dimension(width, height);
            }

            @Override
            public boolean isOptimizedDrawingEnabled()
            {
                return false;
            }
        };
        contentPane.setBackground(new Color(42,42,42));
        for (int i = 0; i < blacks.length; i++) {
            blacks [i] = new BlackKey (i);
            contentPane.add (blacks [i]);
            blacks [i].addMouseListener (playController);
            blacks[i].addKeyListener(playController);
        }
        for (int i = 0; i < whites.length; i++) {
            whites [i] = new WhiteKey (i);
            contentPane.add (whites [i]);
            whites [i].addMouseListener (playController);
            whites[i].addKeyListener(playController);
        }
        //addComp(jpgbpanelL, contentPane, 0, 1, 2, 1, GridBagConstraints.BOTH, 0.33, 0.8);
        getContentPane().add(contentPane);
    }
    public void create_note_holder(PlayController playController){
        jpnotes = new NoteHolderPanel(playController);
       //addComp(jpgbpanelL, jpnotes, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.33, 0.8);
        getContentPane().add(jpnotes);
    }

    public void drawView(){

    }
    public void registerController(PlayController playController){
        this.jmmute.setActionCommand("MUTE");
        this.jmmute.addActionListener(playController);
        this.jmclose.setActionCommand("CLOSE");
        this.jmclose.addActionListener(playController);
        this.jmstart.setActionCommand("START");
        this.jmstart.addActionListener(playController);
        this.playController = playController;
        create_note_holder(playController);
        create_piano();
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
    public int getPosNote(int note){
        int pos = 0;
        for(WhiteKey wk:whites){
            if(note == wk.getNote()){
                pos = wk.getXpos();
            }
        }
        for(BlackKey bk:blacks){
            if(note == bk.getNote()){
                pos = bk.getXpos();
            }
        }
        return pos;
    }

}


