/**
 * Vista referent a les tecles negres
 * @version 1.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

import javax.swing.*;
import java.awt.*;

public class BlackKey extends JButton implements Key {

    private final int note;
    int left;

    public BlackKey (int pos) {
        note = baseNote + 1 + 2 * pos + (pos + 3) / 5 + pos / 5;
        left = 10 + WD
                + ((WD * 3) / 2) * (pos + (pos / 5)
                + ((pos + 3) / 5));
        setBackground (Color.BLACK);
        setBounds (left, 10, WD, HT);
    }

    public int getNote () {
        return note;
    }
    public int getXpos(){
        return left;
    }
}
