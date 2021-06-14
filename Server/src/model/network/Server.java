package model.network;

import model.Manager;
import model.network.DedicatedServer;
import model.network.InitServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Clase herència de Thread que controla tots els DedicatedServers i es manté esperant una
 * nova connexió d'usuari per crear un servidor dedicaat.(Necesita Sockets, una llista de servidorsDedicats i Manager)
 * @version 0.11 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class Server extends Thread {
    private String IPDB;
    private int portDB;
    private String nomDB;
    private String nicknameDB;
    private String passwordDB;
    private int portClientDB;
    private boolean isOn;
    private ServerSocket sSocket;
    private LinkedList<DedicatedServer> dedicatedServers;
    private InitServer init;
    private Manager manager;
    private static final int PORT = 37000;

    protected String mensajeServidor;
    protected DataOutputStream salidaServidor, salidaCliente;

    public Server (Manager manager){
        try{
            this.isOn = false;
            this.sSocket = new ServerSocket(PORT); //RECORDAR
            this.dedicatedServers = new LinkedList<>();
            this.manager = manager;

            init = new InitServer();
            this.IPDB = init.getIPDB();
            this.portDB = init.getPortDB();
            this.nomDB = init.getNomDB();
            this.nicknameDB = init.getNicknameDB();
            this.passwordDB = init.getPasswordDB();
            this.portClientDB = init.getPortClientDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startServer (){
        if(!isOn){
            this.isOn = true;
            this.start();
        }

    }

    public void stopServer (){
        this.interrupt();
        this.isOn = false;
    }


    public void run(){
        while (isOn) {
            try {
                Socket sClient = sSocket.accept();
                DedicatedServer d = new DedicatedServer(sClient, this,manager,dedicatedServers);
                dedicatedServers.add(d);
                d.startDedicatedServer();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void remove (DedicatedServer dedicatedServer) {
        dedicatedServers.remove(dedicatedServer);
        //showClients(); ho mostrem?
    }

    public void updateAllClients () {
        ObjectOutputStream outStream;
        /*for (DedicatedServer dServer : dedicatedServers) {
            dServer.sendMessage (model.getServerState()); enviem missatge?
        }
        */
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean on) {
        isOn = on;
    }

    public LinkedList<DedicatedServer> getDedicatedServers() {
        return dedicatedServers;
    }
}
