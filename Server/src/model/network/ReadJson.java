package model.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase necessària per llegir el JSON.(conté la IPDB, el portDB, el nomDB i també
 *  * el nickname i password de la BBDD i el port del client)
 * @version 0.3 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class ReadJson {
    @SerializedName("portDB")
    @Expose
    private Integer portDB;
    @SerializedName("IPDB")
    @Expose
    private String iPDB;
    @SerializedName("nomDB")
    @Expose
    private String nomDB;
    @SerializedName("nicknameDB")
    @Expose
    private String nicknameDB;
    @SerializedName("passwordDB")
    @Expose
    private String passwordDB;
    @SerializedName("portClientDB")
    @Expose
    private Integer portClientDB;

    public Integer getPortDB() {
        return portDB;
    }

    public void setPortDB(Integer portDB) {
        this.portDB = portDB;
    }

    public String getIPDB() {
        return iPDB;
    }

    public void setIPDB(String iPDB) {
        this.iPDB = iPDB;
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

    public Integer getPortClientDB() {
        return portClientDB;
    }

    public void setPortClientDB(Integer portClientDB) {
        this.portClientDB = portClientDB;
    }
}