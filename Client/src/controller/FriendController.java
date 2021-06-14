/**
 * Controlador de la pestanya amics (Utilitzem una FriendView i un Manager)
 * @version 2.2 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import model.Song;
import model.User;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PortUnreachableException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class FriendController implements ActionListener {
    private FriendView fv;
    private Manager model;
    private String friendSelected;

    public FriendController(FriendView fv,Manager model) {
        this.fv = fv;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("HOME")){
            showMainView();
        }
        if(actionEvent.getActionCommand().equals("PLAY")){
            showPlayView();
        }
        if(actionEvent.getActionCommand().equals("TOCAR")){
            showTocarView();
        }
        if(actionEvent.getActionCommand().equals("ADDFRIEND")){
            model.addFriend(fv.getCodiAmic());
            model.getFriends(model.getUser().getCodiAmics());
            addFriends();
        }
        if(actionEvent.getActionCommand().equals("FRIENDBUTTON")){
            JButton jb = (JButton) actionEvent.getSource();
            String nick = jb.getText();
            model.getFriendSongs(nick);
            friendSelected = nick;
        }
        if(actionEvent.getActionCommand().equals("LOGOUT")){
            showLogoutView();
        }
        if(actionEvent.getActionCommand().equals("SONGBUTTON")){
            JButton jb = (JButton) actionEvent.getSource();
            String songName = jb.getText();
            User friend = model.getUser().getAmic(friendSelected);
            for(Song s:friend.getSongs()){
                if(s.getNom().equals(songName)){
                    Song song = model.analizeSong("Midis/"+s.getNom()+".mid");
                    song.setNom(s.getNom());
                    model.setSelectedSong(song);
                    System.out.println(model.getSelectedSong().getNom());
                    showPlayView();
                    break;
                }

            }

        }

    }


    public void showMainView() {
        //MainView mainView = new MainView();
        //MainController mainController = new MainController(mainView,model);
        //mainView.registerController(mainController);
        //mainView.setVisible(true);
        //fv.setVisible(false);

        MainView mainWindow = new MainView();
        MainController mainController = new MainController(mainWindow,model);
        mainController.addPublicSongs();
        mainController.getPublicSongs();
        mainWindow.registerController(mainController);
        fv.setVisible(false);
        mainWindow.setVisible(true);
        model.setmController(mainController);
    }

    public void showTocarView() {
        PianoView pianoView = new PianoView();
        PianoController pianoController = new PianoController(pianoView, model);
        pianoView.registerController(pianoController);
        pianoView.setVisible(true);
        fv.setVisible(false);
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
        logoutView.registerController(logoutController);
        logoutView.setVisible(true);
        model.setloController(logoutController);
    }

    public void hideView() { fv.setVisible(false); }

    public void addFriends(){
        LinkedList<User> friends = model.getUser().getAmics();
        if(!friends.isEmpty()){
            fv.add_friends(friends);
        }

    }
    public void addSongs(LinkedList<Song> songs){
        if(!songs.isEmpty()){
            fv.add_public_songs(songs);
        } else {
            fv.repaintGrid();
        }
    }




}
