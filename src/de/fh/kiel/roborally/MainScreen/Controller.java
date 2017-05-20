package de.fh.kiel.roborally.MainScreen;

import com.sun.org.omg.CORBA.Initializer;
import de.fh.kiel.roborally.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformManagedObject;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private Text txtCreateGame;
    @FXML private Text txtJoinGame;
    @FXML private Text txtExit;
    Stage stage;
    Parent root;

    @FXML private void createGameMouseEnter(){
        txtCreateGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void createGameMouseExit(){
        txtCreateGame.setStrokeWidth(0);
    }

    @FXML private void createGameMouseClick() throws IOException {
        changeScene("CreateGame", "create_game.fxml");
    }

    @FXML private void joinGameMouseClick() throws IOException {
        changeScene("JoinGame", "join_game.fxml");
    }

    @FXML private void joinGameMouseEnter(){
        txtJoinGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void joinGameMouseExit(){
        txtJoinGame.setStrokeWidth(0);
    }

    @FXML private void exitMouseEnter(){
        txtExit.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void exitMouseExit(){
        txtExit.setStrokeWidth(0);
    }

    @FXML private void exitMouseClick(){
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void changeScene(String path, String FXMLFile ) throws IOException {
        Stage stage = (Stage) txtCreateGame.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(String.format("../%s/%s",path, FXMLFile)));

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
