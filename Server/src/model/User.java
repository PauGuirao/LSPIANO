package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe que guarda tota la informació que necessita l'usuari al llarg del programa.
 * (Necessita una linkedlist de cançons i un altra d'amics, a part dels atributs identificatius per
 * autenticar-lo a la BBDD i trobar els seus amics)
 * @version 0.35 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class User implements Serializable {
    public final static int serialVersionUID = 1238;
    private String codiAmics;
    private String password;
    private String email;
    private String nickName;
    private String procedencia;
    private LinkedList<Song> songs;
    private LinkedList<User> amics;
    private String keyBindings;

    public User(){
        codiAmics = "";
        password = "";
        email = "";
        nickName = "";
        procedencia = "";
        amics = new LinkedList<User>();
    }
    public User(String codiAmics, String password, String email, String nickName,String procedencia,LinkedList<User> amics) {
        this.codiAmics = codiAmics;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.procedencia = procedencia;
        this.amics = amics;
    }

    public String getCodiAmics() {
        return codiAmics;
    }

    public void setCodiAmics(String codiAmics) {
        this.codiAmics = codiAmics;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickName;
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public void setSongs(LinkedList<Song> songs) {
        this.songs = songs;
    }

    public LinkedList<User> getAmics() {
        return amics;
    }

    public void setAmics(LinkedList<User> amics) {
        this.amics = amics;
    }

    public String getKeyBindings() {
        return keyBindings;
    }

    public void setKeyBindings(String keyBindings) {
        this.keyBindings = keyBindings;
    }
}
