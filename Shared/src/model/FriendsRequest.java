package model;

import java.io.Serializable;
import java.util.LinkedList;

public class FriendsRequest implements Serializable {
    private String codi;
    private LinkedList<User> amics;

    public FriendsRequest(String codi) {
        this.codi = codi;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public LinkedList<User> getAmics() {
        return amics;
    }

    public void setAmics(LinkedList<User> amics) {
        this.amics = amics;
    }
}
