package clientcallbackends;

import callpackets.VideoPacket;
import com.github.sarxos.webcam.Webcam;
import gui.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;

/**
  @author Kunal Anand
 */
public class VideoSenderBackend implements Runnable{

    @Override
    public void run() {

        Webcam webcam;
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640 , 480));
        webcam.open();
        BufferedImage ima;
        while(true) {
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                ima = webcam.getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(200000);

                ImageIO.write(ima, "jpg", baos);
                baos.flush();
                byte[] data = baos.toByteArray();

                VideoPacket videoPacket = new VideoPacket(data, timestamp,
                        Main.getSendername(), Main.getRecievername(), false);
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();

                ObjectOutputStream oos = new ObjectOutputStream(baos2);
                oos.writeObject(videoPacket);
                byte[] pack = baos2.toByteArray();

                DatagramSocket ds = new DatagramSocket();

                InetAddress ip = InetAddress.getByName("localhost");
                DatagramPacket dp = new DatagramPacket(pack, pack.length, ip, 6679);
                ds.send(dp);
                ds.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
