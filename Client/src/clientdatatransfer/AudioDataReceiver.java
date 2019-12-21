package clientdatatransfer;

import callpackets.AudioPacket;
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
public class AudioDataReceiver extends Task<Void> {
    private Queue<AudioPacket> packetQueue;
    DatagramSocket socket;
    private Boolean done;

    public AudioDataReceiver(Queue<AudioPacket> packetQueue, DatagramSocket socket, Boolean done) {
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
            AudioPacket packet = (AudioPacket) is.readObject();
            packetQueue.add(packet);
        }
        return null;
    }
}
