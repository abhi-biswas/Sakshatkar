package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {
    private static Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static int audioDatagramPort = 6678, videoDatagramPort = 6679;
    public static Socket getSocket() {
        return socket;
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

    public static ObjectInputStream getOis() {
        return ois;
    }


    public static void main(String[] args) {

        //Tester code for audio calling by Kunal Anand
        Thread thread = new Thread(new AudioCall("anandkunal241"));
        thread.start();

        try
        {
            socket = new Socket("127.0.0.1",6963);
            System.out.println("Connection Created");
            //Reversing the order causes Deadlock and the project freezes
            //https://stackoverflow.com/questions/54095782/the-program-stops-when-the-objectinputstream-object-is-created
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());


        }
        catch(Exception e)
        {
            //do nothing
            System.out.println(""+e);
        }
        launch(args);

    }

    public static int getAudioDatagramPort() {
        return audioDatagramPort;
    }

    public static int getVideoDatagramPort() {
        return videoDatagramPort;
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root=null;
        try {
            root= FXMLLoader.load(getClass().getResource("index.fxml"));
            primaryStage.setTitle("Secure Quiz");
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
