package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe encarregada d'enviar i rebre informació a la BBDD sobre els usuaris. Permet afegir, eliminar i modificar.
 * @version 0.12 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class UserDAO {
    public void addUser(User u) {
        String query1 = "INSERT INTO usuari(codiAmic, password, email, nickname,key_bindings) VALUES ('"+u.getCodiAmics()+"', '"+u.getPassword()+"', '"+u.getEmail()+"', '"+u.getNickname()+"','q2w3er5t6y7uvgbhnmk,l.ñ-');";
        System.out.println(query1);
        ConnectorDB.getInstance().insertQuery(query1);

    }

    //i si fem el getUser?
    public LinkedList<User> getAllUsers() {

        LinkedList<User> users = new LinkedList<>();
        String query = "SELECT codiAmic, password, email, nickname FROM usuari;";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try{
            while(resultat.next()){
                String codiAmic = resultat.getString("codiAmic");
                String password = resultat.getString("password");
                String email = resultat.getString("email");
                String nickname = resultat.getString("nickname");
                users.add(new User(codiAmic, password, email, nickname,"PROVA",null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User checkUserCredentials(User u) {
        String query = "SELECT codiAmic, password, email, nickname,key_bindings FROM Usuari WHERE (nickname = "+"'"+u.getNickname()+"' OR email = "+"'"+u.getNickname()+"') AND password = "+"'"+u.getPassword()+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        User user = new User();
        try{
            if(resultat.next()){
                String codiAmic = resultat.getString("codiAmic");
                user.setCodiAmics(codiAmic);
                String password = resultat.getString("password");
                user.setPassword(password);
                String email = resultat.getString("email");
                user.setEmail(email);
                String nickname = resultat.getString("nickname");
                user.setNickname(nickname);
                String keys = resultat.getString("key_bindings");
                user.setKeyBindings(keys);
            }else{
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public User checkUserRegister(User u) {
        String query = "SELECT codiAmic, password, email, nickname FROM Usuari WHERE nickname = "+"'"+u.getNickname()+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        User user = new User();
        try{
            if(resultat.next()){
                user = null;
                System.out.println("eps");
            }else{
                addUser(u);
                user = new User(u.getCodiAmics(),u.getPassword(),u.getEmail(),u.getNickname(),"REGISTER",null);
                //aixo s'ha de cambiar per el que volguem
                user.setKeyBindings("q2w3er5t6y7uvgbhnmk,l.ñ-");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public void addFriend(Peticio p){
        String query = "SELECT codiAmic, password, email, nickname FROM Usuari WHERE codiAmic = "+"'"+p.getFriendCode()+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        User user = new User();
        try{
            if(resultat.next()){
                String query2 = "INSERT INTO amics(codiAmic1, codiAmic2) VALUES("+"'"+p.getMyCode()+"','"+p.getFriendCode()+"');";
                System.out.println(query2);
                ConnectorDB.getInstance().insertQuery(query2);
            }else{
                System.out.println("El codi aquest no existeix!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<User> checkFriends(String code){
        LinkedList<User> amics = new LinkedList<User>();
        LinkedList<String> codis = new LinkedList<>();
        String query = "SELECT codiAmic1, codiAmic2 FROM Amics WHERE codiAmic1 = "+"'"+code+"' OR codiAmic2 = "+"'"+code+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try{
            while (resultat.next()){
                String codi1 = resultat.getString("codiAmic1");
                String codi2 = resultat.getString("codiAmic2");
                if(!codi1.equals(code)){
                    codis.add(codi1);
                    System.out.println(codi1);
                }
                if(!codi2.equals(code)){
                    codis.add(codi2);
                    System.out.println(codi2);
                }
            }

            for (String codi:codis){
                try {
                    String query2 = "SELECT codiAmic,nickname FROM Usuari WHERE codiAmic = "+"'"+codi+"';";
                    ResultSet resultat2 = ConnectorDB.getInstance().selectQuery(query2);
                    User user = new User();
                    if(resultat2.next()){
                        user.setCodiAmics(resultat2.getString("codiAmic"));
                        user.setNickname(resultat2.getString("nickname"));
                        System.out.println("EL nom es:"+user.getNickname());
                        amics.add(user);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }

    public void deleteUser(User u){
        String query1 = "DELETE FROM amics WHERE codiAmic1 = "+"'"+u.getCodiAmics()+"';";
        ConnectorDB.getInstance().deleteQuery(query1);
        String query2 = "DELETE FROM amics WHERE codiAmic2 = "+"'"+u.getCodiAmics()+"';";
        ConnectorDB.getInstance().deleteQuery(query2);
        String query3 = "DELETE FROM usuari WHERE codiAmic = "+"'"+u.getCodiAmics()+"';";
        ConnectorDB.getInstance().deleteQuery(query3);
    }

    public void saveKeys(InfoKeys ik){
        String query = "UPDATE usuari SET key_bindings ='"+ik.getKeys()+"' WHERE codiAmic ='"+ik.getCodiAmic()+"';";
        ConnectorDB.getInstance().updateQuery(query);
    }

}

