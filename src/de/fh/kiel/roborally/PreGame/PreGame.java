package de.fh.kiel.roborally.PreGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fh.kiel.roborally.CreateGame.CreateGame;
import de.fh.kiel.roborally.Games.GameDetails;
import de.fh.kiel.roborally.Games.Games;
import de.fh.kiel.roborally.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
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
import javafx.util.Duration;


import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

public class PreGame implements Initializable {

    Parent root;
    ObservableList<GameDetails> gamesObservableList;
    GameDetails gameDetails[];

    @FXML private Text txtStartGame;
    @FXML private TableView<GameDetails> tblPlayerList;
    @FXML private TableColumn<GameDetails, String> colPlayerName;
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
        URL url = null;
        try {
            url = new URL("http://localhost:8080/gamedetails/game/" + Main.game.getId());
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
            http.setRequestMethod("PUT"); // PUT is another valid option
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
        try {
            InputStream inputStream = http.getInputStream();
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
            gameDetails = gson.fromJson(stringOfGames, GameDetails[].class);
            populatePlayerNames();
//            String jsonOutput = gson.toJson(gameDetails);
//            System.out.println(jsonOutput);
//            System.out.println(Main.gameDetails.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene("MainScreen", "main_screen.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        getGameDetails();

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

    private void getGameDetails(){
        URL url = null;
        try {
            url = new URL("http://localhost:8080/gamedetails/game/" + Main.game.getId());
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
        try {
            InputStream inputStream = http.getInputStream();
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
            gameDetails = gson.fromJson(stringOfGames, GameDetails[].class);
            populatePlayerNames();
//            String jsonOutput = gson.toJson(gameDetails);
//            System.out.println(jsonOutput);
//            System.out.println(Main.gameDetails.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
