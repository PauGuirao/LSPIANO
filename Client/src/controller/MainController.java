/**
 * Controlador de la pantalla Home (Utilitzem una MainView, un Manager, una PianoView i un FriendView)
 * @version 3.1 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import model.Song;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.Timer;

public class MainController implements ActionListener {
    private MainView mainView;
    private Manager model;
    private PianoView pianoView = new PianoView();
    private FriendView fv = new FriendView();


    public MainController(MainView mainView, Manager model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("HOME")){
            //No fa falta afegir res perque estem al home
        }
        if(actionEvent.getActionCommand().equals("PLAY")){
            if(model.getSelectedSong() != null){
                showPlayView();
            }else{
                System.out.println("Eps, no hi ha cap canço selecionada");
            }

        }
        if(actionEvent.getActionCommand().equals("DELETE")){
            deleteSong();
        }
        if(actionEvent.getActionCommand().equals("TOCAR")){
            showTocarView();
        }

        if(actionEvent.getActionCommand().equals("SHOWPUBLIC")){
            getPublicSongs();
        }

        if(actionEvent.getActionCommand().equals("SHOWPRIVATE")){
            getPrivateSongs();
        }

        if(actionEvent.getActionCommand().equals("AMICS")){
            showFriendView();
        }

        if(actionEvent.getActionCommand().equals("SONGBUTTON")){
            JButton jb = (JButton) actionEvent.getSource();
            for(Song s:model.getUser().getSongs()){
                if(s.getNom().equals(jb.getText())){
                    System.out.println(jb.getText());
                    model.setSelectedSong(s);
                }
            }
            mainView.addSelectedSong(jb.getText());
        }

        if(actionEvent.getActionCommand().equals("LOGOUT")){
            showLogoutView();
        }
        
    }

    public void hideView() { mainView.setVisible(false); }

    public void showTocarView() {
        //PianoView pianoView = new PianoView();
        PianoController pianoController = new PianoController(pianoView, model);
        pianoView.registerController(pianoController);
        model.setpiController(pianoController);
        pianoView.setVisible(true);
        mainView.setVisible(false);
    }

    public void showFriendView() {
        //FriendView fv = new FriendView();
        FriendController fc = new FriendController(fv, model);
        model.setfController(fc);
        fc.addFriends();
        fv.registerController(fc);
        fv.setVisible(true);
        mainView.setVisible(false);
    }


    public void showPlayView() {
        PlayView playView = new PlayView();
        PlayController playController = new PlayController(playView,model);
        playView.registerController(playController);
        playController.createRectangles();
        playView.setVisible(true);
        playView.toFront();
    }

    public void showLogoutView(){
        LogoutView logoutView = new LogoutView();
        LogoutController logoutController = new LogoutController(logoutView, model);
        model.setloController(logoutController);
        logoutView.registerController(logoutController);
        logoutView.setVisible(true);
    }

    public void addPublicSongs(){
        Song s1 = model.analizeSong("Midis/Midi1.mid");
        Song s2 = model.analizeSong("Midis/Midi2.mid");
        Song s3 = model.analizeSong("Midis/Midi3.mid");

        LinkedList<Song> cancons = new LinkedList<>();

        cancons.add(s1);
        cancons.add(s2);
        cancons.add(s3);

        mainView.add_public_songs(cancons);
    }

    public void getPublicSongs(){
        LinkedList<Song> cancons = model.getUser().getSongs();
        if(!cancons.isEmpty()){
            for(Song s:cancons){
                Song songAux = model.analizeSong("Midis/" + s.getNom() + ".mid");
                s.setNotas(songAux.getNotas());
                s.setDurada(songAux.getDurada());
            }
            mainView.add_public_songs(cancons);

        }
    }

    public void actualizeView(LinkedList<Song> songs){
        mainView.add_public_songs(songs);
    }

    public void getPrivateSongs(){
        LinkedList<Song> cancons = model.getUser().getSongs();
        if(!cancons.isEmpty()){
            for(Song s:cancons){
                Song songAux = model.analizeSong("Midis/"+s.getNom()+".mid");
                s.setNotas(songAux.getNotas());
                s.setDurada(songAux.getDurada());
            }
            mainView.add_private_songs(cancons);
        }
    }

    public void deleteSong(){
        model.deleteSong();
    }

    public void unselecSong(){
        mainView.unselectSong();
    }
}
