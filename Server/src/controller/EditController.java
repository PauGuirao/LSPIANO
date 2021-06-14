package controller;

import model.Manager;
import model.Song;
import model.network.DedicatedServer;
import model.network.Server;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase que controla la EditView y llama a las distintas clases en función del actionEvent. (Necesita el EditView, Manager y Server para funcionar)
 * @version 0.5 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class EditController implements ActionListener {
    private EditView editView;
    private Manager model;
    private Server server;


    public EditController(EditView editView,Manager model,Server server) {
        this.editView = editView;
        this.model = model;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getActionCommand().equals("DELETE")){
            if(model.getSelectedSong() != null){
                sendDeleteNoti();
            }else {
                System.out.println("eps no tens cap canço seleccionada");
            }
        }

        if(actionEvent.getActionCommand().equals("SONGBUTTON")){
            JButton jb = (JButton) actionEvent.getSource();
            editView.addSelectedSong(jb.getText());
            for(Song s:model.getAllSongs()){
                if(s.getNom().equals(jb.getText())){
                    model.setSelectedSong(s);
                }
            }
        }

        if (actionEvent.getActionCommand().equals("SERVER")){
            showServerWindow();
        }
        if(actionEvent.getActionCommand().equals("HISTORIC")){
            showHistoricView();
        }
        if(actionEvent.getActionCommand().equals("TOP")){
            showTopView();
        }
        if(actionEvent.getActionCommand().equals("NEW")){
            showNewUserView();
        }

    }

    private void showNewUserView(){
        NewUserView nv = new NewUserView();
        NewUserController nc = new NewUserController(nv, model, server);
        nv.registerController(nc);
        nv.setVisible(true);
        editView.setVisible(false);
    }

    private void showTopView(){
        TopView tv = new TopView();
        TopController tc = new TopController(tv, model, server);
        tv.registerController(tc);
        tc.getTop();
        tv.setVisible(true);
        editView.setVisible(false);
    }

    private void showHistoricView(){
        HistoricView hv = new HistoricView();
        HistoricController hc = new HistoricController(hv, model, server);
        model.setHistoricController(hc);
        hc.getPublicSongs();
        hv.registerController(hc);
        hv.setVisible(true);
        editView.setVisible(false);
    }

    public void showServerWindow(){
        MainView mainView = new MainView();
        MainViewController mainController = new MainViewController(mainView, server, model);
        mainView.registerController(mainController);
        editView.setVisible(false);
        mainView.setVisible(true);
    }


    public void getPublicSongs(){
        LinkedList<Song> cancons = model.getAllSongs();
        if(!cancons.isEmpty()){
            editView.add_public_songs(cancons);
            model.setSongsInServer(cancons);
        }
    }

    public void actulizeView(LinkedList<Song> songs){
        editView.add_public_songs(songs);
        editView.unselectSong();
    }

    public void sendDeleteNoti(){
        for (DedicatedServer d:server.getDedicatedServers()){
            if(d.getUser().getCodiAmics().equals(model.getSelectedSong().getCreador())){
                d.sendNoti(model.getSelectedSong());
            }
        }
    }

}
