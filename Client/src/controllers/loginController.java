package controllers;

import gui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import requests.LoginRequest;
import results.LoginResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class loginController {

    private ObjectInputStream objectInputStream = Main.getOis();
    private ObjectOutputStream objectOutputStream = Main.getOos();
    private LoginResult loginResult;

    @FXML
    private Button loginButton,backButton;

    @FXML
    private TextField Username;

    @FXML
    private PasswordField password;

    @FXML
    private Label signal;

    @FXML
    public void initialize()
    {

    }


    public void backButtonClicked(ActionEvent actionEvent) {
        System.out.println("Index window requested");
        Stage stage = (Stage) loginButton.getScene().getWindow();

        Parent root=null;
        try {
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
            root = FXMLLoader.load(getClass().getResource("../gui/index.fxml"));

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Sakshatkar");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loginButtonClicked(ActionEvent actionEvent) {
        LoginRequest loginRequest = new LoginRequest(Username.getText(), password.getText());

        try {
            objectOutputStream.writeObject(loginRequest);
            objectOutputStream.flush();

            loginResult = (LoginResult) objectInputStream.readObject();

            switch (loginResult.getLoginstatus())
            {
                case SUCCESS:               System.out.println("Homepage window requested");
                                            Main.setLoginResult(loginResult);
                                            Stage stage = (Stage) loginButton.getScene().getWindow();
                                            Parent root = FXMLLoader.load
                                                    (getClass().getResource("../gui/dashboard.fxml"));
                                            stage.setScene(new Scene(root, 800, 600));
                                            stage.setTitle("HomePage");


                case PASSWORDNOTMATCHED:     signal.setText("Password Incorrect");
                                            break;

                case USERNOTEXIST:         signal.setText("USER DOES NOT EXIST");
                                            break;
                default:                    signal.setText("Some Error Occured");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
