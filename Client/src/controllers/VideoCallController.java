package controllers;

import callpackets.AudioPacket;
import callpackets.VideoPacket;
import clientcallbackends.AudioSenderBackend;
import clientcallbackends.VideoCallReceiverBackend;
import clientcallbackends.VideoCallState;
import clientcallbackends.VideoSenderBackend;
import clientdatatransfer.AudioDataReceiver;
import clientdatatransfer.VideoDataReceiver;
import gui.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

import static javafx.application.Application.launch;

/**
 * @author Abhijeet Biswas, Kunal Anand
 *
 */
public class VideoCallController {

    private Queue<VideoPacket> videoPacketQueue;
    private Queue<AudioPacket> audioPacketQueue;
    private Queue<Pair<VideoPacket,AudioPacket>> synchronizedPacketQueue;
    private VideoCallState currentstate;
    private VideoDataReceiver videoDataReceiver;
    private AudioDataReceiver audioDataReceiver;
    private DatagramSocket videosocket,audiosocket;
    private Boolean isDone;
    private Timeline timeline;
    private AudioSenderBackend audioSenderBackend;
    private VideoSenderBackend videoSenderBackend;

    private AudioPacket audioPacket;
    private VideoPacket videoPacket;
    @FXML
    Button closeButton,muteButton,cutAudioButton;
    @FXML
    Label receivedImageContainer,myImageContainer;
    @FXML
    public void initialize()
    {

        try
        {
          //  System.out.println("Reached controller");
            receivedImageContainer.setGraphic(new ImageView(Main.getCard()));
            videosocket = new DatagramSocket(Main.getVideoDatagramPort());
            audiosocket = new DatagramSocket(Main.getAudioDatagramPort());
            currentstate = VideoCallState.SYNCHRONIZED;
            isDone = false;


            videoPacketQueue = new LinkedList<>();
            audioPacketQueue = new LinkedList<>();
            synchronizedPacketQueue = new LinkedList<>();
            VideoCallReceiverBackend receiverBackend = new VideoCallReceiverBackend(videoPacketQueue,audioPacketQueue,synchronizedPacketQueue,currentstate);
            videoDataReceiver= new VideoDataReceiver(videoPacketQueue,videosocket,isDone);
            audioDataReceiver = new AudioDataReceiver(audioPacketQueue,audiosocket,isDone);
            System.out.println("thread to be started");

            new Thread(videoSenderBackend).start();
            new Thread(audioSenderBackend).start();
            new Thread(audioDataReceiver).start();
            new Thread(receiverBackend).start();
            new Thread(videoDataReceiver).start();

            //Speaker start
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
            SourceDataLine speakers;
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();


         //   System.out.println("Thread started");
            timeline = new Timeline(new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // add code to show the video
                    int t = 1;
                    while(t-->0) {
                        BufferedImage buf = null;
                        try {
                            //System.out.println("Synchronized queue size: " + synchronizedPacketQueue.size());
                                Pair<VideoPacket, AudioPacket> packetPair = synchronizedPacketQueue.remove();
                                // System.out.println("Played");
                                videoPacket = packetPair.getKey();
                                audioPacket = packetPair.getValue();
                                speakers.write(audioPacket.getData(), 0, audioPacket.getData().length);
                                //System.out.println(videoPacket.getReceivername() + " " + videoPacketQueue.size());
                                buf = ImageIO.read(new ByteArrayInputStream(videoPacket.getData()));

                        } catch (Exception e) {
                            System.out.println("Ab aayega maza");
                        }
                        Main.setCard(SwingFXUtils.toFXImage(buf, null));
                        receivedImageContainer.setGraphic(new ImageView(Main.getCard()));

                    }
                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.playFromStart();


        }
        catch (Exception e)
        {

        }
    }

    public Queue<VideoPacket> getVideoPacketQueue() {
        return videoPacketQueue;
    }

    public Queue<AudioPacket> getAudioPacketQueue() {
        return audioPacketQueue;
    }
    @FXML
    public void closeButtonClicked()
    {
        currentstate = VideoCallState.CLOSED;
        isDone = true;
        timeline.stop();
        /* add code to close the window */
    }
    @FXML
    public void muteButtonClicked()
    {

    }
    @FXML
    public void cutAudioButtonClicked()
    {
        currentstate = VideoCallState.NOAUDIO;
    }
}
