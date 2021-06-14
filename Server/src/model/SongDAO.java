package model;

import view.TopView;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;




/**
 * Classe Encarregada d'enviar i rebre informació a la BBDD sobre les cançons. Permet afegir, eliminar i modificar.
 * @version 0.21 28 Jun 2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */
public class SongDAO {

    private static String SAVE_DIR = "Midis";      // Directory where .mid files will be stored

    public void deleteSong(Song song){
        String query = "DELETE FROM canco WHERE nom LIKE \"" + song.getNom() + "\"";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    public void addSong(Song song) {
        String query = "INSERT INTO canco(id_canco,public,nom,creador) VALUES ('"+song.getId()+"',"+song.getIsPublic()+",'"+song.getNom()+"','"+song.getCreador()+"');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    public LinkedList<Song> getSongs(){
        LinkedList<Song> songs = new LinkedList<>();
        String query = "SELECT nom, creador FROM canco;";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try{
            while(resultat.next()){
                String nom = resultat.getString("nom");
                String creador = resultat.getString("creador");
                Song s = new Song();
                s.setNom(nom);
                s.setCreador(creador);
                songs.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public LinkedList<Song> checkSongs(String codiAmic){
        LinkedList<Song> songs = new LinkedList<>();
        String query = "SELECT id_canco,public,nom FROM canco WHERE creador = '"+codiAmic+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);

        // Comprovar que la canco segueixi existint al directori
        boolean trobat;
        File dir = new File(SAVE_DIR);
        File[] files = dir.listFiles((dirl, name) -> name.endsWith(".mid"));

        try{
            while(resultat.next()){
                String id = resultat.getString("id_canco");
                String nom = resultat.getString("nom");
                boolean tipus = resultat.getBoolean("public");
                System.out.println(nom);
                System.out.println(tipus);
                String creador = codiAmic;
                Song s = new Song();
                s.setId(id);
                s.setNom(nom);
                s.setCreador(creador);
                s.setIsPublic(tipus);

                // Segueix existint?
                if(files.length != 0) {
                    trobat = false;
                    for (int j = 0; j < files.length && !trobat; j++) {
                        if (files[j].getName().toLowerCase().contains(s.getNom().toLowerCase())) {
                            trobat = true;
                        }
                    }
                    if (!trobat) {
                        deleteSong(s);
                    }else{
                        songs.add(s);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public void deleteSongsUser(User u){
        String query = "DELETE FROM canco WHERE creador = '"+u.getCodiAmics()+"';";
        ConnectorDB.getInstance().deleteQuery(query);
    }
    public LinkedList<EntradaGrafic> selectInfoSong(Song s){
        LinkedList<EntradaGrafic> entrades = new LinkedList<>();

        for (int i = 1;i <= 31;i++){
            EntradaGrafic eg = new EntradaGrafic(i);
            entrades.add(eg);
        }
        String query = "SELECT id_canco,data_reproduccio,temps_reproduccio FROM reprodueix WHERE id_canco = '"+s.getNom()+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try{
            while(resultat.next()){
                String nom = resultat.getString("id_canco");
                System.out.println(nom);
                String data = resultat.getString("data_reproduccio");
                System.out.println(data);
                String[] data_separada;
                data_separada = data.split("/");

                String diaBBDD = data_separada[0];
                String mesBBDD = data_separada[1];
                //String anyBBDD = data_separada[2];

                //agafo la data en el que estem
                String today = getCurrentDate();
                String[] today_separada = today.split("/");
                String mesActual = today_separada[1];
                //String anyActual = today_separada[2];

                if (Integer.parseInt(mesActual) == Integer.parseInt(mesBBDD)){
                    for (EntradaGrafic e:entrades){
                        if(e.getDia() == Integer.parseInt(diaBBDD)){
                            String temps_reproduccio = resultat.getString("temps_reproduccio");
                            e.sumaReproduccio();
                            e.sumaTemps(Float.parseFloat(temps_reproduccio));
                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(entrades.get(20).getTemps());
        return entrades;
    }

    public String getCurrentDate(){
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return todayAsString;
    }

    public LinkedList<TopElement> getTop5(){
        LinkedList<Song> songs = new LinkedList<>();
        LinkedList<TopElement> topElements = new LinkedList<>();
        String query = "SELECT id_canco,public,nom FROM canco";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);

        // Comprovar que la canco segueixi existint al directori
        boolean trobat;
        File dir = new File(SAVE_DIR);
        File[] files = dir.listFiles((dirl, name) -> name.endsWith(".mid"));

        try{
            while(resultat.next()){
                String id = resultat.getString("id_canco");
                String nom = resultat.getString("nom");
                Song s = new Song();
                s.setId(id);
                s.setNom(nom);

                // Segueix existint?
                if(files.length != 0) {
                    trobat = false;
                    for (int j = 0; j < files.length && !trobat; j++) {
                        if (files[j].getName().toLowerCase().contains(s.getNom().toLowerCase())) {
                            trobat = true;
                        }
                        System.out.println("Hola!");
                    }
                    if (!trobat) {
                        deleteSong(s);
                    }else{
                        songs.add(s);
                    }
                }

            }
            for (Song s:songs){
                String query2 = "SELECT id_canco,data_reproduccio,temps_reproduccio FROM reprodueix WHERE id_canco = '"+s.getNom()+"';";
                ResultSet resultat2 = ConnectorDB.getInstance().selectQuery(query2);
                while(resultat2.next()){
                    s.sumaReproduccions();
                }
                TopElement te = new TopElement(s.getNom(),s.getNumReproduccions());
                topElements.add(te);
            }
            //aqui tinc totes les cançons amb les seves reproduccions totals, s'har dordenar
            Collections.sort(topElements);
            System.out.println(topElements.getLast().getReproduccions());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topElements;
    }
    public void saveReproduccion(InfoSong infoSong){
        String today = getCurrentDate();
        String query = "INSERT INTO Reprodueix(id_canco,data_reproduccio,temps_reproduccio) VALUES ('"+infoSong.getNom()+"','"+today+"','"+infoSong.getTemps()+"');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

}
