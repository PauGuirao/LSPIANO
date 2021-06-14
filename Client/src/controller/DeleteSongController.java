/**
 * Segon controlador de l'eliminador de cançons (Utilitzem una DeleteSongView i un Manager)
 * @version 1.3 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import model.Song;
import view.DeleteSongView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteSongController implements ActionListener {
    private DeleteSongView deleteSongView;
    private Manager model;

    public DeleteSongController(DeleteSongView deleteSongView, Manager model) {
        this.deleteSongView = deleteSongView;
        this.model = model;
    }

    public void showDeleteView(Song s){
        deleteSongView.setSongText(s.getNom());
        deleteSongView.setVisible(true);
        deleteSongView.toFront();
        deleteSongView.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("DELETE")){
            deleteSong();
        }
        deleteSongView.setVisible(false);
    }

    public void deleteSong(){
        model.deleteSong();
    }
}
