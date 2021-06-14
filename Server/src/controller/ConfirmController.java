package controller;

import model.Manager;
import view.ConfirmView;
import model.network.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Controller necesario para enviar la confirmación de eliminar la canción (Necesita la ConfirmView y el Manager para funcionar)
 * @version 1.3 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class ConfirmController implements ActionListener {
    private ConfirmView confirmView;
    private Manager model;

    public ConfirmController(ConfirmView deleteView, Manager model){
        this.confirmView = deleteView;
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Confirmar")) {
            // enviar confirmacio a l'usuari (enviem la string de la canço)
        } else if (actionEvent.getActionCommand().equals("Cancelar")) {
            confirmView.setVisible(false);
        }
    }
}
