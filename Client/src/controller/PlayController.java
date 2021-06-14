/**
 * Controlador de la reproducció de cançons (Utilitzem un MidiChannel, una PlayView, diferents Timers i Sequencers, i un Manager)
 * @version 4.6 28/06/2020
 * @author Pau Guirao, Curial Iglesias, Albert Armengol, Aleix Noguera, David Ventura
 */

package controller;

import model.InfoSong;
import model.Manager;
import model.Nota;
import model.Song;
import view.*;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class PlayController implements KeyListener, MouseListener,ActionListener {
    private Synthesizer synth;
    private MidiChannel channel;


    private Sequencer sequencer;
    private Sequence seq;
    private MidiEvent me;
    private Track currentTrack;
    private LinkedList<MidiEvent> events;

    private Sequence sequence;
    private boolean isMuted = false;

    private Timer songTime;
    private int seconds;

    private Manager model;
    private PlayView playView;

    private LinkedList<RectangleFigure> rectangleFigures;
    private boolean isPlaying = false;
    private boolean isStarted = false;
    private Timer timeView;

    private Timer timer;

    private boolean[] isPlayingWhites = new boolean[20];
    private boolean[] isPlayingBlacks = new boolean[10];




    public PlayController(PlayView playView,Manager model){
        try {
            this.playView = playView;
            this.model = model;
            rectangleFigures = new LinkedList<>();
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
            sequence = MidiSystem.getSequence(new File("Midis/"+model.getSelectedSong().getNom()+".mid"));
            //InputStream is = new BufferedInputStream(new FileInputStream(new File("Midis/"+model.getSelectedSong().getNom()+".mid")));
            //sequencer.setSequence(is);
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(220);

        } catch (InvalidMidiDataException | MidiUnavailableException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        songTime.scheduleAtFixedRate(task,100,100);

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
        if(actionEvent.getActionCommand().equals("MUTE")){
            if(isMuted){
                sequencer.setTrackMute(0,false);
                isMuted = false;
            }else {
                sequencer.setTrackMute(0,true);
                isMuted = true;
            }

        }
        if(actionEvent.getActionCommand().equals("START")){

            isStarted = true;

            TimerTask timerTask = new TimerTask()
            {
                public void run()
                {
                    try {
                        animate();
                    } catch (MidiUnavailableException e) {
                        e.printStackTrace();
                    } catch (InvalidMidiDataException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 1000, 100);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    seconds++;
                }
            };
            timeView = new Timer();
            timeView.scheduleAtFixedRate(task,1000,1000);
        }
        if(actionEvent.getActionCommand().equals("CLOSE")){
            //PASSA ELS SEGONS I SUMA UNA REPRODUCCIO
            if(isStarted){
                timeView.cancel();
                timer.cancel();
                timer.purge();
            }
            sequencer.stop();
            sequencer.close();
            InfoSong infoSong = new InfoSong(model.getSelectedSong().getNom(),seconds);
            model.saveReproduccion(infoSong);
            seconds = 0;
            playView.setVisible(false);

        }
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

    public LinkedList<RectangleFigure> getRectangleFigures() {
        return rectangleFigures;

    }

    public void createRectangles(){
        Song s = model.getSelectedSong();
        int[] posBlack = {49,51,54,56,58,61,63,66,68,70};
        for (Nota n:s.getNotas()){
            boolean isBlack = false;
            System.out.println("NOTA: "+n.getNota()+" de:"+n.getInici()+" a:"+n.getFinali()+" te tamany de:"+n.getTamany());
            for (int i:posBlack){
                if(i == n.getNota()){
                    isBlack = true;
                }
            }
            int xPos = calculaXPos(n.getNota());
            int yPos = calculaYpos(n) + 300;
            if(!isBlack){
                rectangleFigures.add(new RectangleFigure(xPos,yPos,(int)n.getTamany(),80,0));
                System.out.println("blanca a:"+yPos);
            }else {
                rectangleFigures.add(new RectangleFigure(xPos,yPos,(int)n.getTamany(),50,1));
                System.out.println("negra a:"+yPos);
            }
        }

    }
    int calculaYpos(Nota n){
        int yPos = 0;
        yPos = (int) (n.getInici());
        return -yPos;
    }

    int calculaXPos(int nota){
        int pos = playView.getPosNote(nota);
        return pos;
    }



    void animate() throws MidiUnavailableException, InvalidMidiDataException, IOException {
        //sizeY += 10;
        for (int i = 0; i < rectangleFigures.size(); i++){
            rectangleFigures.get(i).setyPos(10);
        }
        if(rectangleFigures.get(0).getyPos() > 300 && !isPlaying){
            sequencer.open();
            sequencer.start();
            isPlaying = true;
        }
        if(rectangleFigures.getLast().getyPos() > 1080){
            timeView.cancel();
            sequencer.stop();
            sequencer.close();
            InfoSong infoSong = new InfoSong(model.getSelectedSong().getNom(),seconds);
            model.saveReproduccion(infoSong);
            seconds = 0;
            playView.setVisible(false);
            isPlaying = false;
            timer.cancel();
            timer.purge();
        }
        playView.repaint();
    }

}

