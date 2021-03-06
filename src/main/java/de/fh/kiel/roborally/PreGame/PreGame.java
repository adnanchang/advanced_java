package de.fh.kiel.roborally.PreGame;

import de.fh.kiel.roborally.Games.Player;
import de.fh.kiel.roborally.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class PreGame implements Initializable {

    Parent root;
    ObservableList<Player> gamesObservableList;
    Player gameDetails[];

    @FXML private Text txtStartGame;
    @FXML private TableView<Player> tblPlayerList;
    @FXML private TableColumn<Player, String> colPlayerName;
    @FXML private Text txtBack;

    @FXML private void txtStartGameMouseEnter(){
        txtStartGame.setStrokeWidth(1.5);
        playHoverSound();
    }

    @FXML private void txtStartGameMouseExit(){
        txtStartGame.setStrokeWidth(0);
    }

    @FXML private void backMouseEnter(){
        txtBack.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void backMouseExit(){
        txtBack.setStrokeWidth(0);
    }

    @FXML private void backMouseClick() throws IOException {
       changeScene("MainScreen", "main_screen.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void changeScene(String path, String FXMLFile ) throws IOException {
        Stage stage = (Stage) txtStartGame.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(String.format("../%s/%s",path, FXMLFile)));

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }

    public void playHoverSound(){
        String musicFile = "resources/menu_item_hover.wav";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void populatePlayerNames() {
        //Fill in the table list
        tblPlayerList.getColumns().clear();
        colPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tblPlayerList.getColumns().addAll(colPlayerName);
        gamesObservableList = FXCollections.observableArrayList(gameDetails);
        tblPlayerList.setItems(gamesObservableList);
    }
}
