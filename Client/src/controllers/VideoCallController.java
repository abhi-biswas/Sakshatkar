package controllers;

import callpackets.AudioPacket;
import callpackets.VideoPacket;
import clientcallbackends.VideoCallReceiverBackend;
import clientcallbackends.VideoCallSenderBackend;
import clientcallbackends.VideoCallState;
import clientdatatransfer.AudioDataReceiver;
import clientdatatransfer.VideoDataReceiver;
import gui.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.DatagramSocket;
import java.util.Queue;
/**
 * @author Abhijeet Biswas
 * incomplete
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
    @FXML
    Button closeButton,muteButton,cutAudioButton;
    @FXML
    Label receivedImageContainer,myImageContainer;
    @FXML
    public void initialize()
    {

        try
        {
            videosocket = new DatagramSocket(Main.getVideoDatagramPort());
            audiosocket = new DatagramSocket(Main.getAudioDatagramPort());
            currentstate = VideoCallState.SYNCHRONIZED;
            isDone = false;
            VideoCallReceiverBackend receiverBackend = new VideoCallReceiverBackend(videoPacketQueue,audioPacketQueue,synchronizedPacketQueue,currentstate);
            // to be added VideoCallSenderBackend senderBackend =
            videoDataReceiver= new VideoDataReceiver(videoPacketQueue,videosocket,isDone);
            audioDataReceiver = new AudioDataReceiver(audioPacketQueue,audiosocket,isDone);
            new Thread(receiverBackend).start();
            new Thread(videoDataReceiver).start();
            new Thread(audioDataReceiver).start();
            timeline = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // add code to show the video
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
