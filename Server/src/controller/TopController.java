package controller;

import model.Manager;
import model.TopElement;
import model.network.Server;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Controller que se encarga de mostrar el Top5 de canciones más reproducidas. (Necesita el TopView, Manager i Server para funcionar)
 * @version 0.7 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class TopController implements ActionListener {
    private TopView topView;
    private Manager model;
    private Server server;

    public TopController(TopView topView, Manager model, Server server) {
        this.topView = topView;
        this.model = model;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("SERVER")){
            showServerWindow();
        }
        if(actionEvent.getActionCommand().equals("HISTORIC")){
            showHistoricView();
        }
        if(actionEvent.getActionCommand().equals("NEW")){
            showNewUserView();
        }
        if(actionEvent.getActionCommand().equals("EDITA")) {
            showEditView();
        }
    }

    private void showEditView(){
        EditView ev = new EditView();
        EditController ec = new EditController(ev,model,server);
        ec.getPublicSongs();
        ev.registerController(ec);
        ev.setVisible(true);
        topView.setVisible(false);
    }

    private void showNewUserView(){
        NewUserView nv = new NewUserView();
        NewUserController nc = new NewUserController(nv, model, server);
        nv.registerController(nc);
        nv.setVisible(true);
        topView.setVisible(false);
    }

    private void showHistoricView(){
        HistoricView hv = new HistoricView();
        HistoricController hc = new HistoricController(hv, model, server);
        model.setHistoricController(hc);
        hc.getPublicSongs();
        hv.registerController(hc);
        hv.setVisible(true);
        topView.setVisible(false);
    }

    public void showServerWindow(){
        MainView mainView = new MainView();
        MainViewController mainController = new MainViewController(mainView, server, model);
        mainView.registerController(mainController);
        topView.setVisible(false);
        mainView.setVisible(true);
    }
    public void getTop(){
        LinkedList<TopElement> top = model.getTop();
        topView.add_top5(top);
    }
}
