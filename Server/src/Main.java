import controller.MainViewController;
import model.Manager;
import model.network.Server;
import view.MainView;

/**
 * Classe principal que crea els controllers i fa les primeres crides per iniciar el bucle
 * principal del programa. (Carrega els controllers de MainView, server, manager i la vista MainView)
 * @version 1.3 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class Main {
    public static void main (String[] args) {

        MainView mainWindow = new MainView();
        Manager manager = new Manager();
        Server server = new Server(manager);
        MainViewController mainController = new MainViewController(mainWindow,server,manager);
        mainWindow.registerController(mainController);
        mainWindow.setVisible(true);
        //LinkedList<EntradaGrafic> entrades = manager.getInfo();
        //GraficReproduccions gr = new GraficReproduccions();
        //GraficMinuts gm = new GraficMinuts();
        //gr.createAndShowGui(entrades);
        //gm.createAndShowGui(entrades);
    }
}
