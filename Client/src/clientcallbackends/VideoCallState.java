package clientcallbackends;
/**
 * @author Abhijeet Biswas
 */
public enum VideoCallState {
    SYNCHRONIZED,NOAUDIO,CLOSED,MUTED;
    // muted : no audio to the speakers
    // NOAUDIO : send no audio to the server
}
