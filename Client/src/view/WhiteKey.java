/**
 * Vista referent a les tecles blanques
 * @version 1.1 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import javax.swing.*;
import java.awt.*;

public class WhiteKey  extends JButton implements Key {

    static int WWD = (WD * 3) / 2;
    static int WHT = (HT * 3) / 2;
    final int note;
    int left;

    public WhiteKey (int pos) {

        note = baseNote + 2 * pos - (pos + 4) / 7 - pos / 7;
        left = 10 + WWD * pos;
        //setBackground (Color.WHITE);
        setBounds (left, 10, WWD, WHT);

    }
    public void changeColorToWhite(){
        this.setBackground(Color.WHITE);
    }
    public void changeColorToOrange(){
        this.setBackground(Color.ORANGE);
    }

    public int getNote () {
        return note;
    }
    public int getXpos(){
        return left;
    }
}
