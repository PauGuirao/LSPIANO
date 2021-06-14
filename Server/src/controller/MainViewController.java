package controller;

import model.Manager;
import model.network.DedicatedServer;
import model.network.Server;
import view.*;

import javax.xml.stream.events.EndDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que controla la Main View del servidor. Desde aquí se puede inicializar el servidor y acceder a las distintas Views del menú. (Necesita el MainView, Manager y Server para funcionar)
 * @version 0.12 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class MainViewController implements ActionListener {
    private MainView mainView;
    private Manager model;
    private Server server;

    public MainViewController(MainView mainView, Server server, Manager model) {
        this.mainView = mainView;
        this.server = server;
        this.model = model;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if(e.getActionCommand().equals("START")) {
            startServer();
        }
        if(e.getActionCommand().equals("STOP")) {
            server.stopServer();
        }
        if(e.getActionCommand().equals("EDITA")) {
            showEditView();
        }
        if(e.getActionCommand().equals("HISTORIC")){
            showHistoricView();
        }
        if(e.getActionCommand().equals("TOP")){
            showTopView();
        }
        if(e.getActionCommand().equals("NEW")){
            showNewUserView();
        }
    }

    private void startServer() {
        // Metode per iniciar el servidor

        if (!server.getIsOn()) {
            System.out.println("Server starting...");
            server.startServer();
        }
    }

    private void stopServer(){
        //Metode per interrumpir el servidor

        if(server.getIsOn()){
            System.out.println("Server stopping...");
            server.stopServer();
        }
    }

    private void showEditView(){
        EditView ev = new EditView();
        EditController ec = new EditController(ev,model,server);
        model.setEditController(ec);
        ec.getPublicSongs();
        ev.registerController(ec);
        ev.setVisible(true);
        mainView.setVisible(false);
    }

    private void showHistoricView(){
        HistoricView hv = new HistoricView();
        HistoricController hc = new HistoricController(hv, model, server);
        model.setHistoricController(hc);
        hc.getPublicSongs();
        hv.registerController(hc);
        hv.setVisible(true);
        mainView.setVisible(false);
    }

    private void showTopView(){
        TopView tv = new TopView();
        TopController tc = new TopController(tv, model, server);
        tv.registerController(tc);
        tc.getTop();
        tv.setVisible(true);
        mainView.setVisible(false);
    }

    private void showNewUserView(){
        NewUserView nv = new NewUserView();
        NewUserController nc = new NewUserController(nv, model, server);
        nv.registerController(nc);
        nv.setVisible(true);
        mainView.setVisible(false);
    }
}
