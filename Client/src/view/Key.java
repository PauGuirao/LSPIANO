/**
 * Interficie amb un metode general que implementem a BlackKey i WhiteKey
 * @version 1.4 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package view;

public interface Key {
    // change WD to suit your screen
    int WD = 18*3;
    int HT = (WD * 11) / 2;
    // change baseNote for starting octave
    // multiples of 16 only
    int baseNote = 48;

    int getNote ();
}
