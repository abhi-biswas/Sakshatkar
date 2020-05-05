package controllers;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.*;

/**
 * @author kunal
 * Not complete
 */

public class Dashboard {

    private ObjectOutputStream objectOutputStream=Main.getOos();
    private ObjectInputStream objectInputStream = Main.getOis();

    @FXML
    Label username, name;

    @FXML
    public void initialize(){

    }
}
