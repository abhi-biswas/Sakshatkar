package clientcallbackends;

import callpackets.AudioPacket;
import callpackets.VideoPacket;
import javafx.concurrent.Task;
import javafx.util.Pair;
import java.sql.Timestamp;
import java.util.Queue;

import static java.lang.Thread.sleep;

/**
 * @author Abhijeet Biswas, Kunal Anand
 */
public class VideoCallReceiverBackend implements Runnable {
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
    public void run(){
            try
            {
                while (true)
                {
                    System.out.println("Backend thread started " + currentstate);
                    switch (currentstate)
                    {
                        case CLOSED:
                            //return null;
                        case NOAUDIO:
                            noAudio();
                            break;
                        case SYNCHRONIZED:
                            synchronize();
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public void noAudio() throws InterruptedException {
        while(currentstate==VideoCallState.NOAUDIO)
        {
          //  System.out.println("NO audio Synchonizer videoPacket queue size : " + videoPacketQueue.size() + " " + "audioQueueSize " + audioPacketQueue.size());
           // System.out.println("videoPacket queue size : " + videoPacketQueue.size() + " " + "audioQueueSize " + audioPacketQueue.size());
            while(!videoPacketQueue.isEmpty())
            {
                if(!videoPacketQueue.isEmpty())
                {
                    synchronizedPacketQueue.add(new Pair<>(videoPacketQueue.remove(),null));
                }

            }
            while(!audioPacketQueue.isEmpty())
            {
                if(!audioPacketQueue.isEmpty())
                    audioPacketQueue.remove();
            }
            sleep(100);
        }
    }

    public void synchronize() //based on merge algorithm
    {
        long offset = 90; // in milis
        System.out.println("in synchronize method" + currentstate);
        while (currentstate==VideoCallState.SYNCHRONIZED)
        {
           // System.out.println("Synchonizer videoPacket queue size : " + videoPacketQueue.size() + " " + "audioQueueSize " + audioPacketQueue.size());
            while(!videoPacketQueue.isEmpty() && !audioPacketQueue.isEmpty())
            {
                VideoPacket videoPacket = videoPacketQueue.peek();
                AudioPacket audioPacket = audioPacketQueue.peek();
                Timestamp vidtime = videoPacket.getTimestamp();
                Timestamp audtime = audioPacket.getTimestamp();
                long diff =  Math.abs(audtime.getTime()-vidtime.getTime());
                //System.out.println("Difference : " + diff );
                if(diff<=offset)
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
            long currTime = System.currentTimeMillis();
            while(!videoPacketQueue.isEmpty() )
            {
                if(Math.abs(videoPacketQueue.peek().getTimestamp().getTime()-currTime)>offset)
                    videoPacketQueue.remove();
                else
                    break;
            }
            while(!audioPacketQueue.isEmpty() )
            {
                if(Math.abs(audioPacketQueue.peek().getTimestamp().getTime()-currTime)>offset)
                    audioPacketQueue.remove();
                else break;
            }
           // System.out.println("Synchronize : SynchronisedQueueSize : " + synchronizedPacketQueue.size());
            try {
                sleep(45);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("Packets synchronized");
        }
    }


}
