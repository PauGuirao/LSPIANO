package model;

import java.io.Serializable;

public class Peticio implements Serializable {
    private String myCode;
    private String friendCode;
    private String sender;

    public Peticio(String myCode,String friendCode,String sender){
        this.myCode = myCode;
        this.friendCode = friendCode;
        this.sender = sender;
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
