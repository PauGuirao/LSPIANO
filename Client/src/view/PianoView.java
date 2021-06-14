/**
 * Vista referent a la pantalla Tocar de l'aplicació (Conte una JMenuBar amb el seus JMenuItem referents, diferents JButton i JLabel, WhiteKeys i BlackKeys)
 * @version 3.6 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import controller.RegisterController;
import model.GridLayout2;
import controller.PianoController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PianoView extends JFrame{

    private GridBagConstraints gbc;
    //view del piano
    private final int OCTAVES = 2; // change as desired
    private WhiteKey[] whites = new WhiteKey [7 * OCTAVES + 1];
    private BlackKey[] blacks = new BlackKey [5 * OCTAVES];
    private JButton jbtime;
    private JButton jbgrabar;
    private JButton jbstop;
    private JPanel jpgbpanelL = new JPanel(new GridBagLayout());
    private JLabel jltimer;
    private JMenuBar jmbar = new JMenuBar();
    private JMenuItem jmhome;
    private JMenuItem jmtocar;
    private JMenuItem jmlogout;
    private JMenuItem jmamics;
    private JMenuItem jmedit;
    private JMenuItem jmespai;
    private JMenuItem jmespai2;
    private JMenuItem jmusername;

    //JFrame de confirmacio
    private JFrame confirmationView;
    private JTextField jtfield;
    private JButton jbconfirm;
    private JButton jbcancel;
    private JComboBox jcbtipus;
    private PianoController pianoController;


    public PianoView() {
        setSize(1280,720);
        setLocationRelativeTo(null);
        setTitle("Main View");
        generateConstants();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(42,41,41));
        getContentPane().setLayout(new GridLayout2(2,1));
        createMenuBar();
        drawView();
        createConfirmationView();
    }

    private void generateConstants(){
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        int vGap = 20;
        int hGap = 20;
        gbc.insets = new Insets(hGap, vGap, hGap, vGap);

    }

    private void createMenuBar() {
        jmbar.setPreferredSize(new Dimension(100,80));
        jmbar.setBackground(new Color(33,33,33));
        Border border = new LineBorder(Color.ORANGE, 2, false);
        jmbar.setBorder(border);

        jmhome = add_button_menuBar("HOME");
        jmbar.add(jmhome);
        jmtocar = add_button_menuBar("TOCAR");
        jmbar.add(jmtocar);
        jmamics = add_button_menuBar("AMICS");
        jmbar.add(jmamics);
        jmedit = add_button_menuBar("EDIT");
        jmbar.add(jmedit);
        JMenuItem jmespai = add_button_menuBar("");
        jmbar.add(jmespai);
        JMenuItem jmespai2 = add_button_menuBar("");
        jmbar.add(jmespai2);
        setJMenuBar(jmbar);

    }

    public JMenuItem add_button_menuBar(String name){
        JMenuItem fileMenu = new JMenuItem(name);
        fileMenu.setPreferredSize(new Dimension(200,80));
        fileMenu.setBackground(new Color(33,33,33));
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

    public void create_buttons(){
        JPanel buttonsPane = new JPanel(new GridLayout(3,1,10,10))
        {
            @Override
            public Dimension getPreferredSize()
            {
                int count = getComponentCount();
                Component last = getComponent(count - 1);
                Rectangle bounds = last.getBounds();
                int width = 100 + bounds.x + bounds.width;
                int height = 200 + bounds.y + bounds.height;

                return new Dimension(width, height);
            }

            @Override
            public boolean isOptimizedDrawingEnabled()
            {
                return false;
            }
        };

        Border border = new LineBorder(Color.ORANGE, 2, false);
        buttonsPane.setBackground(new Color(42,42,42));
        //buttonsPane.setPreferredSize(new Dimension(10,100));
        jbgrabar = new JButton("GRABAR");
        jbgrabar.setBackground(new Color(249,52,52));
        jbgrabar.setForeground(Color.BLACK);
        jbgrabar.setFont(new Font("Arial",Font.BOLD,25));
        jbgrabar.setBorder(border);
        jbstop = new JButton("STOP");
        jbstop.setBackground(new Color(32,32,32));
        jbstop.setFont(new Font("Arial",Font.BOLD,25));
        jbstop.setForeground(Color.ORANGE);
        jbstop.setBorder(border);
        jltimer = new JLabel("00:00");
        jltimer.setFont(new Font("Arial",Font.BOLD,35));
        jltimer.setForeground(Color.ORANGE);
        buttonsPane.add(jbgrabar);
        buttonsPane.add(jbstop);
        buttonsPane.add(jltimer);
        addComp(jpgbpanelL, jbgrabar, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        addComp(jpgbpanelL, jbstop, 1, 0, 1, 1, GridBagConstraints.BOTH, 0.33, 0.08);
        addComp(jpgbpanelL, jltimer, 2, 0, 1, 1, GridBagConstraints.BOTH, 0.15, 0.08);
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
            blacks [i].addMouseListener (pianoController);
            blacks[i].addKeyListener(pianoController);
        }
        for (int i = 0; i < whites.length; i++) {
            whites [i] = new WhiteKey (i);
            contentPane.add (whites [i]);
            whites [i].addMouseListener (pianoController);
            whites[i].addKeyListener(pianoController);
        }
        addComp(jpgbpanelL, contentPane, 0, 1, 3, 1, GridBagConstraints.BOTH, 0.33, 0.8);
        getContentPane().add(jpgbpanelL);
    }

    public void drawView(){
        jpgbpanelL = new JPanel(new GridBagLayout());
        jpgbpanelL.setBackground(new Color(42,42,42));
    }
    public void registerController(PianoController pianoController){
        this.pianoController = pianoController;
        create_buttons();
        this.jbgrabar.setActionCommand("RECORD");
        this.jbstop.setActionCommand("SAVE");
        this.jbconfirm.setActionCommand("GUARDARSONG");
        this.jbcancel.setActionCommand("CANCEL");
        this.jcbtipus.setActionCommand("TIPUS");
        this.jmhome.setActionCommand("HOME");
        this.jmamics.setActionCommand("AMICS");
        this.jmedit.setActionCommand("EDIT");
        this.jmamics.addActionListener(pianoController);
        this.jbgrabar.addActionListener(pianoController);
        this.jbstop.addActionListener(pianoController);
        this.jbconfirm.addActionListener(pianoController);
        this.jbcancel.addActionListener(pianoController);
        this.jcbtipus.addActionListener(pianoController);
        this.jmhome.addActionListener(pianoController);
        this.jmedit.addActionListener(pianoController);
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

    private void createConfirmationView(){
        confirmationView = new JFrame();
        confirmationView.setSize(400,400);
        confirmationView.setLocationRelativeTo(null);
        confirmationView.setTitle("Confirmation View");

        confirmationView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        confirmationView.getContentPane().setBackground(new Color(42,41,41));
        confirmationView.getContentPane().setLayout(new GridLayout2(4,1));

        JLabel jltitol = new JLabel();
        jtfield = new JTextField();
        jbconfirm = new JButton();
        jbcancel = new JButton();

        jtfield.setBounds(100,250,300,50);
        jtfield.setBackground(new Color(64,64,64));
        jtfield.setFont(new Font("Arial",Font.PLAIN,20));
        jtfield.setForeground(new Color(112,112,112));
        TextPrompt placeHolder = new TextPrompt("Nom canço",jtfield);
        Border line = BorderFactory.createLineBorder(new Color(245,182,12));
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        jtfield.setBorder(border);


        jbconfirm.setBounds(100,450,300,50);
        jbconfirm.setBackground(new Color(245,182,12));
        jbconfirm.setText("Guardar");
        jbconfirm.setHorizontalAlignment(SwingConstants.CENTER);
        jbconfirm.setFont(new Font("Arial",Font.BOLD,20));

        jbcancel.setBounds(100,450,300,50);
        jbcancel.setBackground(new Color(245,182,12));
        jbcancel.setText("Cancelar");
        jbcancel.setHorizontalAlignment(SwingConstants.CENTER);
        jbcancel.setFont(new Font("Arial",Font.BOLD,20));

        String[] tipus = { "Publica", "Privada"};
        jcbtipus = new JComboBox(tipus);
        jcbtipus.setSelectedIndex(0);

        confirmationView.getContentPane().add(jtfield);
        confirmationView.getContentPane().add(jcbtipus);
        confirmationView.getContentPane().add(jbconfirm);
        confirmationView.getContentPane().add(jbcancel);
    }

    public void activateConfirmationView(){
        confirmationView.setVisible(true);
    }
    public void deactivateConfirmationView(){
        confirmationView.setVisible(false);
    }
    public String getNameSong(){
        return jtfield.getText();
    }
    public String getSongType(){
        String tipus = String.valueOf(jcbtipus.getSelectedItem());
        return tipus;
    }

    public void timerText(int time,int mins){
        if(mins < 10){
            jltimer.setText("0"+mins+":"+time);
        }else {
            jltimer.setText(mins+":"+time);
        }

    }
}