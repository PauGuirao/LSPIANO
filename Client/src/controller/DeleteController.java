/**
 * Controlador d'eliminar el compte (Utilitzem una DeleteView, un Manager, un NetworkManager i una LogoutView)
 * @version 1.7 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import model.Network.NetworkManager;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteController implements ActionListener {
    private DeleteView deleteView;
    private Manager model;
    private NetworkManager nm = new NetworkManager();
    private LogoutView logoutView;

    public DeleteController(DeleteView deleteView, Manager model,LogoutView logoutView){
        this.deleteView = deleteView;
        this.model = model;
        this.logoutView = logoutView;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Confirmar")) {
            nm.eliminarUser(model.getUser());
            deleteView.setVisible(false);
            logoutView.setVisible(false);
            showLoginView();

        } else if (actionEvent.getActionCommand().equals("Cancelar")) {
            deleteView.setVisible(false);
        }
    }

    public void showLoginView(){
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, model);
        loginView.setVisible(true);
        loginView.registerController(loginController);
    }
}
