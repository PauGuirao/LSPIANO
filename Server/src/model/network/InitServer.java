package model.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Clase que conté tots els paràmetres d'inici de servidor. (conté la IPDB, el portDB, el nomDB i també
 * el nickname i password de la BBDD i el port del client)
 * @version 0.2 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class InitServer {
    private String IPDB;
    private int portDB;
    private String nomDB;
    private String nicknameDB;
    private String passwordDB;
    private int portClientDB;

    public String getIPDB() {
        return IPDB;
    }

    public void setIPDB(String IPDB) {
        this.IPDB = IPDB;
    }

    public int getPortDB() {
        return portDB;
    }

    public void setPortDB(int portDB) {
        this.portDB = portDB;
    }

    public String getNomDB() {
        return nomDB;
    }

    public void setNomDB(String nomDB) {
        this.nomDB = nomDB;
    }

    public String getNicknameDB() {
        return nicknameDB;
    }

    public void setNicknameDB(String nicknameDB) {
        this.nicknameDB = nicknameDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB) {
        this.passwordDB = passwordDB;
    }

    public int getPortClientDB() {
        return portClientDB;
    }

    public void setPortClientDB(int portClientDB) {
        this.portClientDB = portClientDB;
    }

    public void loadFromFile(){
        Gson gson = new Gson();
        BufferedReader rd;

        try {
            
            rd = new BufferedReader(new FileReader("config.json"));
            ReadJson read = gson.fromJson(rd, ReadJson.class);
            this.IPDB = read.getIPDB();
            this.portDB = read.getPortDB();
            this.nomDB = read.getNomDB();
            this.nicknameDB = read.getNicknameDB();
            this.passwordDB = read.getPasswordDB();
            this.portClientDB = read.getPortClientDB();
        } catch (FileNotFoundException e) {
            System.out.println("No s'ha trobat el fitxer.");
        }
    }

    //public int initDBConnector(){

}
