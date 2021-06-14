package view;

import model.EntradaGrafic;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Classe que mostra la finestra de gràfics i dibuixa l'històric de reproduccions i minuts de la cançó
 * que s'hagi sel.leccionat desde la vista Historic del servidor. Hereda de JFrame.
 * @version 0.7 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class GraficsView extends JFrame {
    private JFrame frame;

    public void createAndShowGui(LinkedList<EntradaGrafic> entrades) {
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setTitle("GRAPHS");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(1, 2));
        //frame = new JFrame(getClass().getSimpleName());
        GraphDrawer drawer = new GraphDrawer(entrades);
        GraphDrawer2 drawer2 = new GraphDrawer2(entrades);
        getContentPane().add(drawer);
        getContentPane().add(drawer2);
        setVisible(true);
        pack();
    }

    @SuppressWarnings("serial")
    class GraphDrawer extends JPanel {
        private LinkedList<EntradaGrafic> entrades;
        private int startX = 80;
        private int startY = 80;
        private int endX = 600;
        private int endY = 600;
        private int unitX = (endX - startX) / 30;
        private int unitY = (endY - startY) / 31;
        private int prevX = startX;
        private int prevY = endY;
        private int suma;
        private int minuts;

        public GraphDrawer(LinkedList<EntradaGrafic> entrades) {
            for (EntradaGrafic e:entrades){
                suma+=(int)e.getTemps();
            }
            System.out.println(suma);
            unitY = (endY - startY) / (suma+120);
            minuts = (unitY/60);
            System.out.println(unitY);
            this.entrades = entrades;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillRect(0,0,1280,720);

            //We draw in the following 2 loops the grid so it's visible what I explained before about each "unit"
            g2d.setColor(Color.gray);
            for (int i = startX; i <= endX; i += unitX) {
                g2d.drawLine(i, startY, i, endY);
            }

            for (int i = startY; i <= endY; i += unitY*60) {
                g2d.drawLine(startX, i, endX, i);
            }


            //We draw the axis here instead of before because otherwise they would become blue colored.
            g2d.setColor(Color.BLACK);
            g2d.drawLine(startX, startY, startX, endY);
            g2d.drawLine(startX, endY, endX, endY);

            g2d.setColor(Color.ORANGE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("GRAFIC MINUTS",260,50);

            //We draw each of our coords in red color
            g2d.setColor(Color.orange);
            g2d.setStroke(new BasicStroke(2));

            for (EntradaGrafic e: entrades) {
                g2d.drawLine(prevX, prevY, prevX += unitX, prevY = (int) (endY - (e.getTemps() * unitY)));
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(endX + 100, endY + 100);
        }
    }
    class GraphDrawer2 extends JPanel {
        private LinkedList<EntradaGrafic> entrades;
        private int startX = 80;
        private int startY = 80;
        private int endX = 600;
        private int endY = 600;
        private int unitX = (endX - startX) / 30;
        private int unitY = (endY - startY) / 30;
        private int prevX = startX;
        private int prevY = endY;

        public GraphDrawer2(LinkedList<EntradaGrafic> entrades) {

            this.entrades = entrades;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillRect(0,0,1280,720);
            //We draw in the following 2 loops the grid so it's visible what I explained before about each "unit"
            g2d.setColor(Color.gray);
            for (int i = startX; i <= endX; i += unitX) {
                g2d.drawLine(i, startY, i, endY);
            }

            for (int i = startY; i <= endY; i += unitY) {
                g2d.drawLine(startX, i, endX, i);
            }

            //We draw the axis here instead of before because otherwise they would become blue colored.
            g2d.setColor(Color.BLACK);
            g2d.drawLine(startX, startY, startX, endY);
            g2d.drawLine(startX, endY, endX, endY);

            g2d.setColor(Color.ORANGE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("GRAFIC REPRODUCCIONS",230,50);

            //We draw each of our coords in red color
            g2d.setColor(Color.orange);
            g2d.setStroke(new BasicStroke(2));
            for (EntradaGrafic e: entrades) {
                g2d.drawLine(prevX, prevY, prevX += unitX, prevY = (int) (endY - (e.getReproduccions() * unitY)));
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(endX + 100, endY + 100);
        }
    }
}