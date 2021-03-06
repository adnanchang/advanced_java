package de.fh.kiel.roborally.JoinGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXTextField;
import de.fh.kiel.roborally.Games.Games;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class JoinGame implements Initializable {

    @FXML private TableView<Games> tblGameList;
    @FXML private TableColumn<Games, String> colName;
    @FXML private TableColumn<Games, String> colMaxRobotCount;
    @FXML private TableColumn<Games, String> colCurrentRobotCount;
    @FXML private Text txtJoinGame;
    @FXML private JFXTextField txtPlayerName;
    Parent root;
    Stage stage;
    Main main = new Main();
    Games[] games;

    @FXML private void txtJoinGameMouseEnter(){
        txtJoinGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void txtJoinGameMouseExit(){
        txtJoinGame.setStrokeWidth(0);
    }

    @FXML private void txtJoinGameMouseClick() throws IOException {


        stage = (Stage) txtJoinGame.getScene().getWindow();
        main.changeScene(root, stage,"pre_game.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String listOfGames = Main.getJSON("/games/list");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        games = gson.fromJson(listOfGames, Games[].class);
        populateTableView(Arrays.asList(games));
    }

    private void populateTableView(List<Games> games) {
        //Fill in the table list
        tblGameList.getColumns().clear();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMaxRobotCount.setCellValueFactory(new PropertyValueFactory<>("maxRobotCount"));
        colCurrentRobotCount.setCellValueFactory(new PropertyValueFactory<>("currentRobotCount"));
        tblGameList.getColumns().addAll(colName,colMaxRobotCount,colCurrentRobotCount);
        ObservableList<Games> gamesObservableList = FXCollections.observableArrayList(games);
        tblGameList.setItems(gamesObservableList);
    }
}
