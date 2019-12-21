package gui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundReceiver implements Runnable{

    @Override
    public void run(){
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
            SourceDataLine speakers;
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();


            DatagramSocket serverSocket = new DatagramSocket(5555);



            while (true) {

                //receive the audio message in byte format

                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(response);

                // output the audio message to speaker


                out.write(response.getData(), 0, response.getData().length);
                speakers.write(response.getData(), 0, response.getData().length);
            }



        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }catch(LineUnavailableException ex){
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}