import controller.LoginController;
import model.Manager;
import model.Network.NetworkManager;
import view.KeyBindingView;
import view.LoginView;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NetworkManager nm = new NetworkManager();
                Manager clientManager = new Manager(nm);
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView,clientManager);
                clientManager.setLiController(loginController);
                nm.setManagerClient(clientManager);
                nm.setLoginController(loginController);
                loginView.registerController(loginController);
                loginView.setVisible(true);

            }
        });
    }
}
