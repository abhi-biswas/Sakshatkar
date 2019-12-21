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
 * @author Abhijeet Biswas
 */
public class VideoDataReceiver extends Task<Void> {
    private Queue<VideoPacket> packetQueue;
    DatagramSocket socket;
    private Boolean done;

    public VideoDataReceiver(Queue<VideoPacket> packetQueue, DatagramSocket socket, Boolean done) {
        this.packetQueue = packetQueue;
        this.socket = socket;
        this.done = done;
    }

    @Override
    protected Void call() throws Exception {
        while(!done)
        {
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            ObjectInputStream is = new ObjectInputStream(in);
            VideoPacket packet = (VideoPacket) is.readObject();
            packetQueue.add(packet);
        }
        return null;
    }
}
