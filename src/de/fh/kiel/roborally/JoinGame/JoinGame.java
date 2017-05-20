package de.fh.kiel.roborally.JoinGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXTextField;
import de.fh.kiel.roborally.Games.GameDetails;
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
import java.nio.charset.StandardCharsets;
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
    private Games games[];
    Parent root;

    @FXML private void txtJoinGameMouseEnter(){
        txtJoinGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void txtJoinGameMouseExit(){
        txtJoinGame.setStrokeWidth(0);
    }

    @FXML private void txtJoinGameMouseClick() throws IOException {
       changeScene("PreGame","pre_game.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getListOfGames();
        populateTableView(games);
    }

    private void getListOfGames() {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/games/list");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        URLConnection con = null;
        try {
            con = url.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection http = (HttpURLConnection)con;
        try {
            http.setRequestMethod("GET"); // PUT is another valid option
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        }
        http.setDoOutput(true);

        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try(InputStream inputStream = http.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line+"\n");
            }
            bufferedReader.close();
            String stringOfGames = stringBuilder.toString();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            games = gson.fromJson(stringOfGames, Games[].class);
            String jsonOutput = gson.toJson(games);
            System.out.println(jsonOutput);
            populateTableView(games);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void populateTableView(Games[] games) {
        //Fill in the table list
        tblGameList.getColumns().clear();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMaxRobotCount.setCellValueFactory(new PropertyValueFactory<>("maxRobotCount"));
        colCurrentRobotCount.setCellValueFactory(new PropertyValueFactory<>("currentRobotCount"));
        tblGameList.getColumns().addAll(colName,colMaxRobotCount,colCurrentRobotCount);
        ObservableList<Games> gamesObservableList = FXCollections.observableArrayList(games);
        tblGameList.setItems(gamesObservableList);
    }

    private void changeScene(String path, String FXMLFile ) throws IOException {
        Stage stage = (Stage) txtJoinGame.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(String.format("../%s/%s",path, FXMLFile)));

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
