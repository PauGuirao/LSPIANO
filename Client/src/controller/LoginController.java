/**
 * Controlador del Login a l'aplicació (Utilitzem una LoginView i un Manager)
 * @version 2.7 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

//import model.ConnectorDB;
import model.Manager;
import view.DeleteSongView;
import view.LoginView;
import view.MainView;
import view.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginController implements ActionListener {
    private LoginView loginView;
    private Manager clientManager;

    public LoginController(LoginView loginView,Manager clientManager) {
        this.loginView = loginView;
        this.clientManager = clientManager;
        loginView.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("Log in")){
            try {
                clientManager.loginUser(loginView.getNickname(),loginView.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(actionEvent.getActionCommand().equals("Register in")){
            showRegisterWindow();
        }
    }

    public void showMainWindow(LoginView lView) {
        MainView mainWindow = new MainView();
        MainController mainController = new MainController(mainWindow,clientManager);
        mainController.addPublicSongs();
        mainController.getPublicSongs();
        mainWindow.registerController(mainController);
        lView.setVisible(false);
        lView.repaintLabels();
        mainWindow.setVisible(true);
        clientManager.setmController(mainController);

        DeleteSongView dsView = new DeleteSongView();
        DeleteSongController dsController = new DeleteSongController(dsView,clientManager);
        dsView.registerController(dsController);
        clientManager.setDsController(dsController);

    }

    private void showRegisterWindow() {
        RegisterView registerView = new RegisterView();
        RegisterController registerController = new RegisterController(registerView,clientManager);
        registerView.registerController(registerController);
        this.loginView.setVisible(false);
        registerView.setVisible(true);
        clientManager.setrController(registerController);

    }

    public LoginView getLoginView() {
        loginView.repaintLabels();
        return loginView;
    }

    public void showError(){
        loginView.changeErrorText();

    }
}
