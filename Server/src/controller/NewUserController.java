package controller;

import model.Manager;
import model.network.Server;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que controla la NewUserView. Clase opcional que permite añadir a un nuevo usuario desde el servidor. (Necesita el NewUserView, Manager y Server para funcionar)
 * @version 0.1 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class NewUserController implements ActionListener {
    private NewUserView newUserView;
    private Manager model;
    private Server server;

    public NewUserController(NewUserView newUserView, Manager model, Server server) {
        this.newUserView = newUserView;
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
        if(actionEvent.getActionCommand().equals("TOP")){
            showTopView();
        }
        if(actionEvent.getActionCommand().equals("EDITA")) {
            showEditView();
        }
    }

    private void showEditView(){
        EditView ev = new EditView();
        EditController ec = new EditController(ev,model,server);
        model.setEditController(ec);
        ec.getPublicSongs();
        ev.registerController(ec);
        ev.setVisible(true);
        newUserView.setVisible(false);
    }

    private void showTopView(){
        TopView tv = new TopView();
        TopController tc = new TopController(tv, model, server);
        tv.registerController(tc);
        tc.getTop();
        tv.setVisible(true);
        newUserView.setVisible(false);
    }

    private void showHistoricView(){
        HistoricView hv = new HistoricView();
        HistoricController hc = new HistoricController(hv, model, server);
        model.setHistoricController(hc);
        hc.getPublicSongs();
        hv.registerController(hc);
        hv.setVisible(true);
        newUserView.setVisible(false);
    }

    public void showServerWindow(){
        MainView mainView = new MainView();
        MainViewController mainController = new MainViewController(mainView, server, model);
        mainView.registerController(mainController);
        newUserView.setVisible(false);
        mainView.setVisible(true);
    }
}
