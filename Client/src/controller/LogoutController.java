/**
 * Controlador del logout de l'aplicació (Utilitzem una LogoutView, un Manager i un NetworkManager)
 * @version 1.3 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import model.Network.NetworkManager;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LogoutController implements ActionListener {
    private LogoutView logoutView;
    private Manager model;
    private NetworkManager nm = new NetworkManager();


    public LogoutController(LogoutView logoutView, Manager model) {
        this.logoutView = logoutView;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Confirmar")) {
            nm.tancarSessio("TANCAR");
            model.hideAllToLogin();
            showLoginView();

        } else if (actionEvent.getActionCommand().equals("Cancelar")) {
            logoutView.setVisible(false);

        } else if (actionEvent.getActionCommand().equals("Eliminar Compte")) {
            showDeleteView();
        }

    }

    public void showLoginView(){
        model.showLoginView();
    }

    public void hideView() { logoutView.setVisible(false); }

    public void showDeleteView(){
        DeleteView deleteView = new DeleteView();
        DeleteController deleteController = new DeleteController(deleteView, model,logoutView);
        deleteView.setVisible(true);
        deleteView.registerController(deleteController);
    }
}
