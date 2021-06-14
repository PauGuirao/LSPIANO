package controller;

import model.EntradaGrafic;
import model.Manager;
import model.Song;
import model.network.Server;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Clase que se encarga de mostrar el histórico de caciones de todos los usuarios. (Necesita el HistoricView, Manager y Server para funcionar)
 * @version 0.2 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class HistoricController implements ActionListener {
    private HistoricView historicView;
    private Manager model;
    private Server server;

    public HistoricController(HistoricView historicView, Manager model, Server server) {
        this.historicView = historicView;
        this.model = model;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("SERVER")){
            showServerWindow();
        }
        if(actionEvent.getActionCommand().equals("EDITA")) {
            showEditView();
        }
        if(actionEvent.getActionCommand().equals("TOP")){
            showTopView();
        }
        if(actionEvent.getActionCommand().equals("NEW")){
            showNewUserView();
        }
        if(actionEvent.getActionCommand().equals("SONGBUTTON")){
            JButton jb = (JButton) actionEvent.getSource();
            historicView.addSelectedSong(jb.getText());
            for(Song s:model.getAllSongs()){
                if(s.getNom().equals(jb.getText())){
                    model.setSelectedSong(s);
                }
            }
        }
        if(actionEvent.getActionCommand().equals("GRAPH")){
            if(model.getSelectedSong() != null){
                LinkedList<EntradaGrafic> entrades = model.getInfo();
                GraficsView gm = new GraficsView();
                gm.createAndShowGui(entrades);
            }else {
                System.out.println("eps no tens cap canço seleccionada");
            }
        }
    }

    private void showNewUserView(){
        NewUserView nv = new NewUserView();
        NewUserController nc = new NewUserController(nv, model, server);
        nv.registerController(nc);
        nv.setVisible(true);
        historicView.setVisible(false);
    }

    private void showTopView(){
        TopView tv = new TopView();
        TopController tc = new TopController(tv, model, server);
        tc.getTop();
        tv.registerController(tc);
        tv.setVisible(true);
        historicView.setVisible(false);
    }

    private void showEditView(){
        EditView ev = new EditView();
        EditController ec = new EditController(ev,model,server);
        ec.getPublicSongs();
        ev.registerController(ec);
        ev.setVisible(true);
        historicView.setVisible(false);
    }

    public void showServerWindow(){
        MainView mainView = new MainView();
        MainViewController mainController = new MainViewController(mainView, server, model);
        mainView.registerController(mainController);
        historicView.setVisible(false);
        mainView.setVisible(true);
    }

    public void getPublicSongs(){
        LinkedList<Song> cancons = model.getAllSongs();
        if(!cancons.isEmpty()){
            historicView.add_public_songs(cancons);
            model.setSongsInServer(cancons);
        }
    }
}
