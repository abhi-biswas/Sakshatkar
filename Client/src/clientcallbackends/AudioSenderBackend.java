package clientcallbackends;

import callpackets.AudioPacket;
import gui.Main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;


public class AudioSenderBackend implements Runnable{

    @Override
    public void run(){

        try {

            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100, 16, 2, 4, 44100, true);
            TargetDataLine microphone;
            microphone = AudioSystem.getTargetDataLine(format);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            int numBytesRead;
            int CHUNK_SIZE = 1024*8;
            byte[] data = new byte[CHUNK_SIZE];
            microphone.start();

            String hostname = "localhost";
            int port = 6678;


            InetAddress address = InetAddress.getByName(hostname);

            while (true) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println(timestamp);
                DatagramSocket socket = new DatagramSocket();
                long time = System.nanoTime();
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                time = System.nanoTime() - time;
                //System.out.println("Time " + time);
                AudioPacket audioPacket = new AudioPacket(data,timestamp,
                        Main.getSendername(), Main.getRecievername(), false);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(audioPacket);
                objectOutputStream.flush();
                byte []packs = out.toByteArray();
               // System.out.println(packs.length);
                DatagramPacket request = new DatagramPacket(packs, packs.length, address, port);
                socket.send(request);
                socket.close();
               // sleep(50);
            }


        }catch (Exception e){e.printStackTrace();}
    }
}