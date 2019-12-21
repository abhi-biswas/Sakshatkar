package clientcallbackends;

import callpackets.AudioPacket;
import callpackets.VideoPacket;
import javafx.concurrent.Task;
import javafx.util.Pair;
import java.sql.Timestamp;
import java.util.Queue;
/**
 * @author Abhijeet Biswas
 */
public class VideoCallReceiverBackend extends Task<Void> {
    private Queue<VideoPacket> videoPacketQueue;
    private Queue<AudioPacket> audioPacketQueue;
    private Queue<Pair<VideoPacket,AudioPacket>> synchronizedPacketQueue;
    private VideoCallState currentstate;


    public VideoCallReceiverBackend(Queue<VideoPacket> videoPacketQueue, Queue<AudioPacket> audioPacketQueue, Queue<Pair<VideoPacket, AudioPacket>> synchronizedPacketQueue, VideoCallState currentstate) {
        this.videoPacketQueue = videoPacketQueue;
        this.audioPacketQueue = audioPacketQueue;
        this.synchronizedPacketQueue = synchronizedPacketQueue;
        this.currentstate = currentstate;
    }

    @Override
    protected Void call() throws Exception {
            while (true)
            {
                switch (currentstate)
                {
                    case CLOSED:
                        return null;
                    case NOAUDIO:
                        noAudio();
                    case SYNCHRONIZED:
                        synchronize();
                }
            }
    }

    public void noAudio()
    {
        while(currentstate==VideoCallState.NOAUDIO)
        {
            if(!videoPacketQueue.isEmpty())
            {
                synchronizedPacketQueue.add(new Pair<>(videoPacketQueue.remove(),null));
            }
            if(!audioPacketQueue.isEmpty())
                audioPacketQueue.remove();
        }
    }

    public void synchronize() //based on merge algorithm
    {
        long offset = 50; // in milis
        while (currentstate==VideoCallState.SYNCHRONIZED)
        {
            while(!videoPacketQueue.isEmpty() && !audioPacketQueue.isEmpty())
            {
                VideoPacket videoPacket = videoPacketQueue.peek();
                AudioPacket audioPacket = audioPacketQueue.peek();
                Timestamp vidtime = videoPacket.getTimestamp();
                Timestamp audtime = audioPacket.getTimestamp();
                long diff = (int) Math.abs(audtime.getTime()-vidtime.getTime());
                if(diff<=50)
                {
                    synchronizedPacketQueue.add(new Pair<>(videoPacketQueue.remove(),audioPacketQueue.remove()));
                }
                else if(vidtime.getTime()<audtime.getTime())
                {
                    audioPacketQueue.remove();
                }
                else
                {
                    videoPacketQueue.remove();
                }
            }
        }
    }
}
