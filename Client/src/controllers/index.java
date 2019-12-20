package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.midi.ControllerEventListener;
import java.io.IOException;

public  class index  {
    @FXML
    Button LoginButton;
    @FXML
    Button SignUpButton;
    @FXML
    public void loginButtonClicked()  {
        System.out.println("Login Window Requested...");
        Stage stage = (Stage) LoginButton.getScene().getWindow();

        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("../GUI/login.fxml"));

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Login");
        } catch (IOException e) {
            //System.out.println("Hre");
            e.printStackTrace();
        }

    }
    public void  signUpButtnClicked()
    {
        System.out.println("SignUp Window requested...");
        Stage stage = (Stage) LoginButton.getScene().getWindow();

        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("../GUI/signup.fxml"));

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("SignUp");
        } catch (IOException e) {
            //System.out.println("Hre");
            e.printStackTrace();
        }
    }
}
