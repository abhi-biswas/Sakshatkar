package controllers;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author kunal
 * Not complete Menu Button to be added
 */

public class Homepage {

    @FXML
    Label username, name;

    @FXML
    public void initialize(){
        username.setText(username.getText() + Main.getLoginResult().getUsername());
        name.setText(name.getText() + Main.getLoginResult().getFname() + " " + Main.getLoginResult().getLname());
    }
}
