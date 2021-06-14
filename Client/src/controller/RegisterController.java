/**
 * Controlador del registre (Utilitza una RegisterView i un Manager)
 * @version 2.4 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import view.MainView;
import view.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.IntPredicate;

public class RegisterController implements ActionListener {
    private RegisterView registerView;
    private Manager clientManager;
    public RegisterController(RegisterView registerView,Manager clientManager){
        this.registerView = registerView;
        this.clientManager = clientManager;
    }
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("Register")){
            checkRegistration(registerView.getNickname(),registerView.getPassword(),registerView.getPasswordCheck(),registerView.getMail());
        }
    }

    private void showMainWindow() {
        MainView mainWindow = new MainView();
        MainController mainController = new MainController(mainWindow,clientManager);
        this.registerView.setVisible(false);
        mainWindow.setVisible(true);
    }

    public void checkRegistration(String nick,String pass,String pass2,String mail){
        if(isValid(pass, pass2)){
            clientManager.registerUser(nick,pass,mail);
            registerView.setVisible(false);
        }else{
            showError(0);
        }
    }
    public boolean isValid(String value, String value2) {
        return containsLowerCase(value) &&
                containsUpperCase(value) &&
                containsNumber(value) &&
                value.equals(value2) &&
                value.length() >= 8;
    }

    private boolean containsLowerCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    private boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    public void showError(int numError){
        registerView.changeErrorText(numError);
    }

    public void showView(){
        registerView.setVisible(true);
    }

}
