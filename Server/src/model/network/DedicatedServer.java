package model.network;

import model.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Clase herència de Thread que permet conectar-se amb un usuari i controlar tots els paràmetres de la connexió.
 * També es comunica amb el servidor per guardar i carregar informació de l'usuari. (Necesita Sockets,
 * ObjectInputStreams, ObjectOutputStreams, el Server, Manager i User, i la llista de clients)
 * @version 0.23 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class DedicatedServer extends Thread{
    private boolean isOn;
    private Socket sClient;
    private ObjectInputStream ois;
    private LinkedList<DedicatedServer> clients;
    private ObjectOutputStream oos;
    private Server server;
    private Manager manager;
    private User user;


    public DedicatedServer(Socket sClient, Server server,Manager manager,LinkedList<DedicatedServer> clients) {
        this.isOn = true;
        this.sClient = sClient;
        this.server = server;
        this.manager = manager;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(sClient.getInputStream());
            oos = new ObjectOutputStream(sClient.getOutputStream());
            System.out.println("Im ready!");

            while (true) {
                Object obj = ois.readObject();
                if (obj instanceof User) {
                    User userIntroduit = (User) obj;
                    User userRebut = null;
                    if (userIntroduit.getProcedencia().equals("LOGIN")) {
                        userRebut = manager.checkLogin(userIntroduit);
                    } else if(userIntroduit.getProcedencia().equals("DELETE")){
                        manager.deleteAccount(userIntroduit);
                        logoutDone("TANCAR");

                    } else if(userIntroduit.getProcedencia().equals("REGISTER")){
                        userRebut = manager.checkRegister(userIntroduit);
                    }

                    if (userRebut == null) {
                        if(userIntroduit.getProcedencia().equals("LOGIN")){
                            System.out.println("El usuari no esta a la base de dades!");
                            loginError("CIL");
                        }else{
                            System.out.println("El usuari esta a la base de dades!");
                            loginError("CIR");
                        }
                    } else {
                        System.out.println("El usuari esta a la base de dades");
                        LinkedList<Song> canconsUsuari = manager.checkSongs(userRebut.getCodiAmics());
                        canconsUsuari.add(new Song("Midi1",true,"Midi1","123456788"));
                        canconsUsuari.add(new Song("Midi2",true,"Midi2","123456788"));
                        canconsUsuari.add(new Song("Midi3",true,"Midi3","123456788"));
                        LinkedList<User> friends = manager.checkFriends(userRebut.getCodiAmics());
                        missatgeUserLogin(userRebut,canconsUsuari,friends);
                        user = userRebut;
                    }

                }else if(obj instanceof Song){
                    Song songRebuda = (Song) obj;
                    if(songRebuda.getProcedencia().equals("ADD")){
                        manager.addSong(songRebuda);
                    }else{
                        manager.deleteSong(songRebuda);
                    }

                }else if(obj instanceof Peticio){
                    Peticio p = (Peticio)obj;
                    manager.addFriend(p);
                }else if(obj instanceof String){
                    String code = (String) obj;
                    if(!code.equals("TANCAR")){
                        LinkedList<Song> friendSongs = manager.checkSongs(code);
                        sendFriendSongs(friendSongs);
                    }else {
                        stopDedicatedServer();
                        server.remove(this);
                        logoutDone(code);
                    }
                }else if(obj instanceof InfoKeys){
                    InfoKeys ik = (InfoKeys)obj;
                    manager.saveKeys(ik);
                }else if(obj instanceof InfoSong){
                    InfoSong infoSong = (InfoSong) obj;
                    manager.saveReproduccion(infoSong);
                }else if(obj instanceof FriendsRequest){
                    FriendsRequest fr = (FriendsRequest) obj;
                    fr.setAmics(manager.checkFriends(fr.getCodi()));
                    sendFriends(fr);

                }
            }

        } catch (IOException | ClassNotFoundException e1) {
            stopDedicatedServer();
            clients.remove(this);

        }finally {
            try {
                ois.close();
            } catch (IOException e) {e.printStackTrace();}
            try {
                oos.close();
            } catch (IOException e) {}
            try {
                sClient.close();
            } catch (IOException e) {}
        }
    }

    public void startDedicatedServer() {
        // iniciem el servidor dedicat
        this.isOn = true;
        this.start();
    }

    public void stopDedicatedServer(){
        this.interrupt();
        this.isOn = false;
    }

    private ObjectOutputStream getOutChannel() {
        return oos;
    }

    public void missatgeUserLogin(User userRebut,LinkedList<Song> songs,LinkedList<User> friends){
            try {
                LoginInfo li = new LoginInfo(userRebut,songs,friends);
                oos.flush();
                oos.writeObject(li);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void sendFriendSongs(LinkedList<Song> songs){
        try {
            oos.flush();
            oos.writeObject(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutDone(String logout){
        try {
            oos.flush();
            oos.writeObject(logout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendNoti(Song s){
        try {
            oos.flush();
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void loginError(String s){
        try {
            oos.flush();
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendFriends(FriendsRequest fr){
        try {
            oos.flush();
            oos.writeObject(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
