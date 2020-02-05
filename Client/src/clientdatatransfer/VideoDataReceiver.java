package clientdatatransfer;

import callpackets.Packet;
import callpackets.VideoPacket;
import javafx.concurrent.Task;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Queue;
/**
 * @author Abhijeet Biswas, Kunal Anand
 */
public class VideoDataReceiver implements Runnable {
    private Queue<VideoPacket> packetQueue;
    DatagramSocket socket;
    private Boolean done;

    public VideoDataReceiver(Queue<VideoPacket> packetQueue, DatagramSocket socket, Boolean done) {
        this.packetQueue = packetQueue;
        this.socket = socket;
        this.done = done;
    }

    @Override
    public void run()  {
        while(!done)
        {
            try {
                byte[] buffer = new byte[200000];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                // System.out.println("video Data recieved by Kunal");
                ByteArrayInputStream in = new ByteArrayInputStream(buffer);
                ObjectInputStream is = new ObjectInputStream(in);
                VideoPacket packet = (VideoPacket) is.readObject();
                //System.out.println("video "+packet.getTimestamp());
                packetQueue.add(packet);
                //System.out.println("Packets added");
                //System.out.println("Video queue size : " + packetQueue.size());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //return null;
    }

    public Queue<VideoPacket> getPacketQueue() {
        return packetQueue;
    }
}
