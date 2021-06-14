/**
 * Controlador de la pantalla Tocar (Necesitem una PianoView, un Manager, diferents Timers, MidiEvents i Sequencers)
 * @version 4.1 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.Manager;
import view.*;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class PianoController implements KeyListener, MouseListener, ActionListener {
    private Synthesizer synth;
    private MidiChannel channel;


    private Sequencer sequencer;
    private Sequence seq;
    private MidiEvent me;
    private Track currentTrack;
    private LinkedList<MidiEvent> events;

    private Timer songTime;
    private int seconds;

    private Timer timeView;
    private int timeSong;
    private int minsSong;

    private Manager model;
    private PianoView pianoView;

    private boolean[] isPlayingWhites = new boolean[20];
    private boolean[] isPlayingBlacks = new boolean[10];

    public PianoController(PianoView pianoView,Manager model){
        try {
            this.pianoView = pianoView;
            this.model = model;
            load_midi_library();

        } catch (MidiUnavailableException ex) {
            ex.printStackTrace ();
        }
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            seq = new Sequence(Sequence.PPQ, 24);

            currentTrack = seq.createTrack();
            events = new LinkedList<>();
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);

        } catch (InvalidMidiDataException | MidiUnavailableException e) {
            e.printStackTrace();
        }



    }

    public void start_timer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                seconds++;
            }
        };
        songTime = new Timer();
        songTime.scheduleAtFixedRate(task,10,10);
    }

    public void stop_timer(){
        songTime.cancel();
        seconds = 0;
    }

    public void load_midi_library() throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer ();
        synth.open ();
        synth.loadAllInstruments (synth.getDefaultSoundbank ());
        Instrument[] insts = synth.getLoadedInstruments ();
        MidiChannel channels[] = synth.getChannels ();

        //for (int i = 0; i < channels.length; i++) {
        //if (channels [i] != null) {
        channel = channels [1];
        //break;
        //}
        //}
        for (int i = 0; i < insts.length; i++) {
            if (insts [i].toString ()
                    .startsWith ("Instrument MidiPiano")) {
                channel.programChange (i);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("RECORD")){
            try {
                startSongTimer();
                sequencer = MidiSystem.getSequencer();
                sequencer.open();
                seq = new Sequence(Sequence.PPQ, 24);

                currentTrack = seq.createTrack();
                events = new LinkedList<>();
                sequencer.setSequence(seq);
                sequencer.setTempoInBPM(220);

            } catch (InvalidMidiDataException | MidiUnavailableException e) {
                e.printStackTrace();
            }
            sequencer.setTickPosition(sequencer.getTickPosition());
            sequencer.startRecording();
            start_timer();
        }

        if(actionEvent.getActionCommand().equals("SAVE")){
            stopSongTimer();
            sequencer.stopRecording();
            stop_timer();
            for(MidiEvent event:events){
                currentTrack.add(event);
            }
            pianoView.activateConfirmationView();

        }

        if(actionEvent.getActionCommand().equals("GUARDARSONG")){

            String nom = pianoView.getNameSong();
            String tipus = pianoView.getSongType();
            try {
                MidiSystem.write(seq, 0, new File("Midis/"+nom+".mid"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.saveSong(nom,tipus);
            pianoView.deactivateConfirmationView();
        }

        if(actionEvent.getActionCommand().equals("CANCEL")){
            pianoView.deactivateConfirmationView();
            seq.deleteTrack(currentTrack);
            currentTrack = seq.createTrack();
        }

        if(actionEvent.getActionCommand().equals("TIPUS")){
            JComboBox cb = (JComboBox)actionEvent.getSource();
            String tipus = (String)cb.getSelectedItem();
            System.out.println(tipus);
        }

        if(actionEvent.getActionCommand().equals("HOME")){
            showMainView();
        }

        if(actionEvent.getActionCommand().equals("AMICS")){
            showFriendView();
        }
        if(actionEvent.getActionCommand().equals("EDIT")){
            showEditView();
        }

    }

    public void hideView() { pianoView.setVisible(false); }

    public void showMainView() {
        MainView mainWindow = new MainView();
        MainController mainController = new MainController(mainWindow,model);
        mainController.addPublicSongs();
        mainController.getPublicSongs();
        mainWindow.registerController(mainController);
        pianoView.setVisible(false);
        mainWindow.setVisible(true);
        model.setmController(mainController);
    }

    public void showFriendView(){
        FriendView fv = new FriendView();
        FriendController fc = new FriendController(fv, model);
        model.setfController(fc);
        fc.addFriends();
        fv.registerController(fc);
        fv.setVisible(true);
        pianoView.setVisible(false);
    }

    public void showEditView(){
        KeyBindingView kv = new KeyBindingView();
        KeyBindingController kc = new KeyBindingController(kv, model);
        model.setkController(kc);
        kv.registerController(kc);
        kc.setKeys();
        kv.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Key key = (Key) e.getSource();
        channel.noteOn(key.getNote(), 127);
        //currentTrack.add(makeEvent(144, 0, key.getNote(), 127, (int) sequencer.getTickPosition()));
        events.add(makeEvent(144, 0, key.getNote(), 127,seconds));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Key key = (Key) e.getSource ();
        channel.noteOff (key.getNote (), 127);
        //currentTrack.add(makeEvent(128, 0, key.getNote(), 127, (int) sequencer.getTickPosition()));
        events.add(makeEvent(128, 0, key.getNote(), 127,seconds));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyChar();

        if(keyCode == model.getUser().getKeyBindings().charAt(0)) {
            play_white_note(0);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(1)){

            play_black_note(0);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(2)){
            play_white_note(1);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(3)){
            play_black_note(1);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(4)){
            play_white_note(2);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(5)){
            play_white_note(3);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(6)){
            play_black_note(2);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(7)){
            play_white_note(4);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(8)){
            play_black_note(3);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(9)){
            play_white_note(5);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(10)){
            play_black_note(4);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(11)){
            play_white_note(6);
        }


        if(keyCode == model.getUser().getKeyBindings().charAt(12)) {
            play_white_note(7);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(13)){

            play_black_note(5);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(14)){
            play_white_note(8);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(15)){
            play_black_note(6);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(16)){
            play_white_note(9);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(17)){
            play_white_note(10);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(18)){
            play_black_note(7);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(19)){
            play_white_note(11);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(20)){
            play_black_note(8);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(21)){
            play_white_note(12);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(22)){
            play_black_note(9);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(23)){
            play_white_note(13);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        first_octave_controller(e);
        second_octave_controller(e);
    }


    public void first_octave_controller(KeyEvent e){
        int keyCode = e.getKeyChar();

        if(keyCode == model.getUser().getKeyBindings().charAt(0)){
            stop_white_note(0);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(1)){
            stop_black_note(0);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(2)){
            stop_white_note(1);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(3)){
            stop_black_note(1);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(4)){
            stop_white_note(2);
        }


        if(keyCode == model.getUser().getKeyBindings().charAt(5)){
            stop_white_note(3);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(6)){
            stop_black_note(2);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(7)){
            stop_white_note(4);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(8)){
            stop_black_note(3);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(9)){
            stop_white_note(5);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(10)){
            stop_black_note(4);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(11)){
            stop_white_note(6);
        }
    }

    public void second_octave_controller(KeyEvent e){
        int keyCode = e.getKeyChar();

        if(keyCode == model.getUser().getKeyBindings().charAt(12)){
            stop_white_note(7);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(13)){
            stop_black_note(5);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(14)){
            stop_white_note(8);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(15)){
            stop_black_note(6);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(16)){
            stop_white_note(9);
        }


        if(keyCode == model.getUser().getKeyBindings().charAt(17)){
            stop_white_note(10);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(18)){
            stop_black_note(7);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(19)){
            stop_white_note(11);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(20)){
            stop_black_note(8);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(21)){
            stop_white_note(12);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(22)){
            stop_black_note(9);
        }
        if(keyCode == model.getUser().getKeyBindings().charAt(23)){
            stop_white_note(13);
        }
    }

    public void play_white_note(int posicio){
        Key key = new WhiteKey(posicio);
        if(!isPlayingWhites[posicio]) {
            System.out.println(key.getNote()+"  "+posicio+" "+ isPlayingWhites[posicio]);
            channel.noteOn(key.getNote(), 127);
            events.add(makeEvent(144, 0, key.getNote(), 127, seconds));
            isPlayingWhites[posicio] = true;
        }
    }

    public void stop_white_note(int posicio){
        Key key = new WhiteKey(posicio);
        isPlayingWhites[posicio] = false;
        channel.noteOff (key.getNote (), 127);
        events.add(makeEvent(128, 0, key.getNote(), 127,seconds));
    }

    public void play_black_note(int posicio){
        Key key = new BlackKey(posicio);
        if(!isPlayingBlacks[posicio]) {
            channel.noteOn(key.getNote(), 127);
            events.add(makeEvent(144, 0, key.getNote(), 127, seconds));
            isPlayingBlacks[posicio] = true;
        }
    }

    public void stop_black_note(int posicio){
        Key key = new BlackKey(posicio);
        isPlayingBlacks[posicio] = false;
        channel.noteOff (key.getNote (), 127);
        events.add(makeEvent(128, 0, key.getNote(), 127,seconds));
    }

    public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
        MidiEvent event = null;
        try {
            // ShortMessage stores a note as command type, channel,
            // instrument it has to be played on and its speed.
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);

            // A midi event is comprised of a short message(representing
            // a note) and the tick at which that note has to be played
            event = new MidiEvent(a, tick);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return event;
    }

    public void startSongTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeSong++;
                if(timeSong >= 60){
                    timeSong = 0;
                    minsSong++;
                }
                pianoView.timerText(timeSong,minsSong);
            }
        };
        timeView = new Timer();
        timeView.scheduleAtFixedRate(task,1000,1000);
    }

    public void stopSongTimer(){
        timeView.cancel();
        timeSong = 0;
    }

}
