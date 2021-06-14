/**
 * Classe que parla amb tothom i dona accés a la informació demandada
 * @version 3.5 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package model;

import controller.*;
import model.Network.NetworkManager;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Manager {
    private NetworkManager nm;
    //COSES QUE ENS INTERESA DE LA BASE DE DADES
    private User user;
    private Song selectedSong;
    private Song songToDelete;
    //Controllers
    private LoginController liController;
    private LogoutController loController;
    private RegisterController rController;
    private MainController mController;
    private FriendController fController;
    private DeleteSongController dsController;
    private PianoController piController;
    private KeyBindingController kController;

    
    //PROVA PAU
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;



    public Manager(NetworkManager nm){
        this.nm = nm;
        nm.startServerConnection(this);
        songToDelete = null;
    }

    public void addUser(User u){

    }
    public void loginUser(String nom,String pass) throws IOException {
        User user = new User("",pass,"",nom,"LOGIN",null);
        nm.loginUser(user, liController);
    }

    public void registerUser(String nick,String pass,String mail){
        String codi = generate_random_code();
        User user = new User(codi,pass,mail,nick,"REGISTER",null);
        nm.registerUser(user);
    }

    public void addFriend(String codi){
        nm.addFriend(codi,user.getCodiAmics());
    }

    public String generate_random_code(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(9);

        for (int i = 0; i < 9; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    int calculaXPos(int nota){
        int notaBase = 48;
        int resultat = nota - notaBase;
        return resultat * 40;
    }

    public Song analizeSong(String nomMidi){
        Song s = new Song();
        LinkedList<Nota> notasSong = new LinkedList<>();
        LinkedList<Message> missatges = new LinkedList<>();
        Sequence sequence = null;
        try {
            sequence = MidiSystem.getSequence(new File(nomMidi));
        } catch (InvalidMidiDataException e) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Track track :  sequence.getTracks()) {
            int key;
            long startTime = 0;
            long stopTime;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    switch(sm.getCommand()){
                        case NOTE_ON:
                            startTime = event.getTick();
                            key = sm.getData1();
                            missatges.add(new Message("NOTE ON",key, (int)startTime));
                            break;
                        case NOTE_OFF:
                            stopTime = event.getTick();
                            key = sm.getData1();
                            missatges.add(new Message("NOTE OFF",key, (int)stopTime));
                            break;
                    }
                }
            }
        }
        boolean trobada = false;
        for(int i = 0;i < missatges.size();i++){
            if(missatges.get(i).getTipus().equals("NOTE ON")){
                for(int j = i;j < missatges.size();j++){
                    if(missatges.get(j).getTipus().equals("NOTE OFF") && missatges.get(j).getNota() == missatges.get(i).getNota()){
                        //notasSong.add(new Nota(missatges.get(j).getNota()+12,missatges.get(i).getTemps(),missatges.get(j).getTemps(),missatges.get(j).getTemps()-missatges.get(i).getTemps()));
                        notasSong.add(new Nota(missatges.get(j).getNota(),missatges.get(i).getTemps(),missatges.get(j).getTemps(),missatges.get(j).getTemps()-missatges.get(i).getTemps()));
                        j = missatges.size();
                    }

                }
            }
        }

        s.setNom(nomMidi);
        s.setNotas(notasSong);
        s.setDurada((float) sequence.getMicrosecondLength()/1000000);
        return s;
    }

    public void saveSong(String nom,String tipus){
        Song s = new Song();
        s.setId(nom);
        s.setNom(nom);
        s.setCreador(user.getCodiAmics());
        s.setProcedencia("ADD");
        if(tipus.equals("Publica")){
            s.setIsPublic(true);
        }else{
            s.setIsPublic(false);
        }
        user.addSong(s);
        mController.actualizeView(user.getSongs());
        nm.sendSong(s);
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    public void deleteSong(){
        songToDelete.setProcedencia("DELETE");
        user.deleteSong(songToDelete);
        checkSelected(songToDelete);
        mController.actualizeView(user.getSongs());
        nm.deleteSong(songToDelete);
    }
    public void checkSelected(Song s){
        if(s == selectedSong){
            selectedSong = null;
            mController.unselecSong();
        }
    }

    public int getNumberSongs(){
        return user.getSongs().size();
    }

    public void getFriendSongs(String nick){
        String codi = "";
        for(User u:user.getAmics()){
            if(u.getNickname().equals(nick)){
                codi = u.getCodiAmics();
                break;
            }
        }
        nm.getFriendSongs(codi);
        System.out.println("el codi es: "+codi);
    }


    public void showFriends(LinkedList<Song> songs){
        for (User friend:user.getAmics()){
            if(!songs.isEmpty()){
                if(friend.getCodiAmics().equals(songs.get(0).getCreador())){
                    friend.setSongs(songs);
                }
            }
        }
        fController.addSongs(songs);
    }

    public void setLiController(LoginController liController) {
        this.liController = liController;
    }

    public void setrController(RegisterController rController) {
        this.rController = rController;
    }

    public void setmController(MainController mController) {
        this.mController = mController;
    }

    public void setfController(FriendController fController) {
        this.fController = fController;
    }

    public void setkController(KeyBindingController kController) {
        this.kController = kController;
    }

    public void setloController(LogoutController loController) {
        this.loController = loController;
    }

    public void setpiController(PianoController piController) {
        this.piController = piController;
    }

    public void hideAllToLogin() {
        loController.hideView();
        if(mController != null){
            mController.hideView();
        }
        if(piController != null){
            piController.hideView();
        }
        if(fController != null){
            fController.hideView();
        }
    }

    public void showLoginView(){
        liController.getLoginView().setVisible(true);
    }

    public Song getSongToDelete() {
        return songToDelete;
    }

    public void setSongToDelete(Song songToDelete) {
        this.songToDelete = songToDelete;
    }

    public DeleteSongController getDsController() {
        return dsController;
    }

    public void setDsController(DeleteSongController dsController) {
        this.dsController = dsController;
    }

    public void showDeleteView(Song s){
        songToDelete = s;
        dsController.showDeleteView(s);
    }
    public void showErrorLogin(){
        liController.showError();
    }
    public void showErrorRegister(int numError){
        rController.showView();
        rController.showError(numError);
    }

    public void sendKeys(String keys){
        InfoKeys ik = new InfoKeys(keys,user.getCodiAmics());
        nm.sendKeys(ik);
    }
    public String getKeys(){
        return user.getKeyBindings();
    }

    public void saveReproduccion(InfoSong infoSong) {
        nm.saveReproduccion(infoSong);
    }

    public void getFriends(String codi){
        nm.getFriends(codi);
    }
}
