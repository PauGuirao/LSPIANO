/**
 * Comunicació amb el bloc Servidor (Utilitzem un Manager, diferents ObjectOutputStream i ObjectInputStream, Sockets)
 * @version 2.9 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package model.Network;

import controller.FriendController;
import controller.LoginController;
import model.*;

import javax.sound.midi.MidiDevice;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;


public class NetworkManager extends Thread {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 37000;

    private Manager managerClient;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socketToServer;
    private boolean running;
    private boolean loggedIn = false;
    private LoginController loginController;
    private FriendController friendController;
    private boolean logged;

    public NetworkManager () {
        try {
            this.managerClient = null;
            this.running = false;
            this.socketToServer = new Socket(IP, PORT);
            this.oos = new ObjectOutputStream(socketToServer.getOutputStream());
            this.ois = new ObjectInputStream(socketToServer.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServerConnection (Manager managerClient) {
        this.managerClient = managerClient;
        this.running = true;
        this.start();
    }

    public void stopServerConnection () {
        this.running = false;
        this.interrupt();
    }

    @Override
    public void run () {
        try {
            while (true) {
                Object obj = ois.readObject();
                if(obj instanceof LoginInfo){
                    LoginInfo li = (LoginInfo)obj;
                    User user = li.getUser();
                    LinkedList<Song> songs = li.getSongs();
                    LinkedList<User> friends = li.getFriends();
                    if (user != null) {
                        loggedIn = true;
                        user.setSongs(songs);
                        user.setAmics(friends);
                        managerClient.setUser(user);
                        loginController.showMainWindow(loginController.getLoginView());
                        System.out.println(managerClient.getUser().getNickname());
                    } else {
                        loggedIn = false;
                        System.out.println("THE USER IS NOT IN THE DATABASE");
                    }
                }
                if (obj instanceof String) {
                    String s = (String)obj;
                    if(s.equals("CIL")){
                        managerClient.showErrorLogin();
                    }else if(s.equals("CIR")){
                        managerClient.showErrorRegister(1);
                    } else{
                        managerClient.showLoginView();
                    }
                }
                if(obj instanceof LinkedList) {
                    LinkedList<Song> songs = (LinkedList<Song>)obj;
                    if (songs != null) {
                        managerClient.showFriends(songs);
                    }
                }
                if(obj instanceof Song){
                    Song s = (Song)obj;
                    managerClient.showDeleteView(s);
                }
                if(obj instanceof FriendsRequest){
                    FriendsRequest fr = (FriendsRequest) obj;
                    managerClient.getUser().setAmics(fr.getAmics());
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            //stopServerConnection();
        }


    }

    public void loginUser (User u, LoginController lController){
        try {
            this.loginController = lController;
            System.out.println("Im sending a user called: "+u.getNickname());
            oos.flush();
            oos.writeObject(u);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }

    }

    public void registerUser (User u){
        try {
            System.out.println("Im trying to register a user called: "+u.getNickname());
            oos.flush();
            oos.writeObject(u);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }

    }

    public void tancarSessio(String tancar){
        try {
            oos.flush();
            oos.writeObject(tancar);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    
    public void eliminarUser(User u){
        try {
            u.setProcedencia("DELETE");
            oos.flush();
            oos.writeObject(u);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }

    public void checkCodi(String codi){
        try {
            oos.flush();
            oos.writeObject(codi);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void setManagerClient(Manager managerClient) {
        this.managerClient = managerClient;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void sendSong(Song s){
        try {
            System.out.println("Im sending a song called: "+s.getNom());
            oos.flush();
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void deleteSong(Song s){
        try {
            oos.flush();
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }

    public void addFriend(String friendCodi,String myCodi){
        try {
            Peticio p = new Peticio(myCodi,friendCodi,myCodi);
            oos.flush();
            oos.writeObject(p);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void getFriendSongs(String code){
        try {
            oos.flush();
            oos.writeObject(code);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void sendKeys(InfoKeys ik){
        try {

            oos.flush();
            oos.writeObject(ik);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void saveReproduccion(InfoSong infoSong){
        try {

            oos.flush();
            oos.writeObject(infoSong);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }
    public void getFriends(String codi){
        try {

            FriendsRequest fr = new FriendsRequest(codi);
            oos.flush();
            oos.writeObject(fr);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerConnection();
        }
    }

}
