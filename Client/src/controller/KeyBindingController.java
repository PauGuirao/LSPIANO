/**
 * Controlador del canvi de controls del teclat (Utilitzem una KeyBindingView i un Manager)
 * @version 1.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import view.FriendView;
import view.KeyBindingView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyBindingController implements ActionListener {
    private KeyBindingView view;
    private Manager model;

    public KeyBindingController(KeyBindingView view, Manager model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("SAVE")){
            String keys = getKeys();
            model.sendKeys(keys);
            model.getUser().setKeyBindings(keys);
            //comprovacio dalgun error?
            view.setVisible(false);
        }
    }

    public String getKeys(){
        String keyString = view.getKeysString();
        return keyString;
    }
    public void setKeys(){
        String keys = model.getKeys();
        view.setKeys(keys);
    }



}
