package gui;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/*
Go to the main Class for tester code
@author :  Kunal Anand
 */


public class AudioCall implements Runnable{

    private String username;

    public AudioCall(String username){
        this.username = username;
    }

    @Override
    public void run(){

        try {

            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
            TargetDataLine microphone;
            microphone = AudioSystem.getTargetDataLine(format);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();


            Thread thread = new Thread(new SoundReceiver());
            thread.start();

            // Configure the ip and port

            String hostname = "localhost";
            int port = 3000;


            InetAddress address = InetAddress.getByName(hostname);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramSocket socket = new DatagramSocket();

                //send the receiver username to server
                out.write(username.getBytes(), 0, username.length());
                DatagramPacket packet = new DatagramPacket(username.getBytes(), username.length(), address, port);
                socket.send(packet);

                //get the audio message from microphone
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                out.write(data, 0, numBytesRead);

                //send the message to server
                DatagramPacket request = new DatagramPacket(data, numBytesRead, address, port);
                socket.send(request);

                socket.close();

            }


        }catch (Exception e){e.printStackTrace();}
    }
}