package model;

import java.io.FileWriter;
import java.io.Serializable;
import java.util.LinkedList;

public class LoginInfo implements Serializable {
    private User user;
    private LinkedList<Song> songs;
    private LinkedList<User> friends;

    public LoginInfo(User user, LinkedList<Song> songs, LinkedList<User> friends) {
        this.user = user;
        this.songs = songs;
        this.friends = friends;
    }

    public User getUser() {
        return user;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public LinkedList<User> getFriends() {
        return friends;
    }

}
