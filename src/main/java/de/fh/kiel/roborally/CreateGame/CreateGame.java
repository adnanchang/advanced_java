package de.fh.kiel.roborally.CreateGame;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.fh.kiel.roborally.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class CreateGame implements Initializable {

    @FXML private JFXComboBox<Integer> comboMaxRobots;
    @FXML private Text txtCreateGame;
    @FXML private JFXTextField txtFName;
    @FXML private JFXTextField txtPlayerName;
    @FXML private Text txtBack;
    Main main = new Main();
    Parent root;
    Stage stage;


    @FXML private void createGameMouseEnter(){
        txtCreateGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void createGameMouseExit(){
        txtCreateGame.setStrokeWidth(0);
    }

    @FXML private void createGameMouseClick() throws IOException {
        String gameName = txtFName.getText().substring(0,1).toUpperCase() + txtFName.getText().substring(1);
        byte[] out = (String.format(
                "{" +
                        "\"name\" : \"%s\"," +
                        "\"maxRobotCount\" : \"%d\"," +
                        "\"playerName\" : \"$s\"" +
                        "}", gameName, comboMaxRobots.getValue(), txtPlayerName.getText())).getBytes(StandardCharsets.UTF_8);
        /**
         * Post Byte array to create a game
         */
        Main.postJSON(out, "/games/create");
        stage = (Stage) txtCreateGame.getScene().getWindow();
        main.changeScene(root, stage, "pre_game.fxml");
    }

    @FXML private void backMouseEnter(){
        txtBack.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void backMouseExit(){
        txtBack.setStrokeWidth(0);
    }

    @FXML private void backMouseClick() throws IOException {
        stage = (Stage) txtCreateGame.getScene().getWindow();
        main.changeScene(root, stage, "main_screen.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboMaxRobots.getItems().removeAll(comboMaxRobots.getItems());
        comboMaxRobots.getItems().addAll(1,2,3,4,5,6);
    }

}
