package model;


import controller.EditController;
import controller.HistoricController;
import model.network.DedicatedServer;

import java.util.LinkedList;

/**
 * Classe principal que controla els UserDAOs, SongDAOs i fa les comprovacions inicials com el login
 * o a l'hora d'afegir cançons. També conté funcions essencials pel funcionament del programa a les que accedeixen altres classes.
 * @version 0.42 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class Manager {
    private UserDAO userDAO;
    private SongDAO songDAO;
    private LinkedList<Song> songsInServer;
    private Song selectedSong;

    private EditController editController;
    private HistoricController historicController;

    public Manager(){
        userDAO = new UserDAO();
        songDAO = new SongDAO();
        songsInServer = new LinkedList<>();
        selectedSong = null;
    }
    public void addUser(User u){
        userDAO.addUser(u);
    }
    public User getFirstUser(){
        LinkedList<User> users = new LinkedList<>(this.userDAO.getAllUsers());
        return users.getFirst();
    }

    public User checkLogin(User u){
        return userDAO.checkUserCredentials(u);
    }
    public User checkRegister(User u){
        return userDAO.checkUserRegister(u);
    }
    public void addSong(Song s){
        songDAO.addSong(s);
    }
    public void deleteSong(Song s){
        for (Song song:songsInServer){
            if(s.getNom().equals(song.getNom())){
                songsInServer.remove(song);
                editController.actulizeView(songsInServer);
            }
        }
        selectedSong = null;
        songDAO.deleteSong(s);
    }

    public LinkedList<Song> checkSongs(String codiAmic){
        return songDAO.checkSongs(codiAmic);
    }

    public void addFriend(Peticio p){
        userDAO.addFriend(p);
    }
    public LinkedList<User> checkFriends(String code){
        return userDAO.checkFriends(code);
    }

    public void deleteAccount(User u){
        userDAO.deleteUser(u);
        songDAO.deleteSongsUser(u);
    }

    public LinkedList<Song> getAllSongs(){
        LinkedList<Song> songs = songDAO.getSongs();
        return songs;
    }
    public void deleteSong(){
        for (Song song:songsInServer){

        }
    }

    public LinkedList<Song> getSongsInServer() {
        return songsInServer;
    }

    public void setSongsInServer(LinkedList<Song> songsInServer) {
        this.songsInServer = songsInServer;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    public void setHistoricController(HistoricController historicController) {
        this.historicController = historicController;
    }

    public LinkedList<EntradaGrafic> getInfo(){
        //Song s = new Song();
        //s.setNom("pepe");
        LinkedList<EntradaGrafic> entrades = songDAO.selectInfoSong(selectedSong);
        return entrades;
    }

    public LinkedList<TopElement> getTop(){
        LinkedList<TopElement> top = songDAO.getTop5();
        return top;
    }

    public void saveKeys(InfoKeys ik){
        userDAO.saveKeys(ik);
    }
    public void saveReproduccion(InfoSong infoSong){
        songDAO.saveReproduccion(infoSong);
    }


}
