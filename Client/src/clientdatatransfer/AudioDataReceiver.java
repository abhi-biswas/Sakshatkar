package clientdatatransfer;

import callpackets.AudioPacket;
import gui.Main;
import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Queue;
/**
 * @author Abhijeet Biswas
 */
public class AudioDataReceiver implements Runnable {
    private Queue<AudioPacket> packetQueue;
    DatagramSocket socket;
    private Boolean done;

    public AudioDataReceiver(Queue<AudioPacket> packetQueue, DatagramSocket socket, Boolean done) {
        this.packetQueue = packetQueue;
        this.socket = socket;
        this.done = done;
    }

    @Override
    public void run() {

        try {


            while (!done) {

               try{
                   byte[] buffer = new byte[10400];
                   DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                   socket.receive(response);
                   ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
                   ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                   AudioPacket audioPacket = (AudioPacket) objectInputStream.readObject();
                   packetQueue.add(audioPacket);
                   //System.out.println("Audio "+audioPacket.getTimestamp());
                   //System.out.println(packetQueue.size());

               }
               catch (Exception e)
               {

               }
                //speakers.write(audioPacket.getData(), 0, audioPacket.getData().length);

                //Thread.sleep(50);
            }



        } catch (Exception ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        }


    }
}
