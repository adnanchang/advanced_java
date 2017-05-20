package de.fh.kiel.roborally.CreateGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.fh.kiel.roborally.Games.GameDetails;
import de.fh.kiel.roborally.Games.Games;
import de.fh.kiel.roborally.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

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
    Parent root;


    @FXML private void createGameMouseEnter(){
        txtCreateGame.setStrokeWidth(1.5);
        Main.playHoverSound();
    }

    @FXML private void createGameMouseExit(){
        txtCreateGame.setStrokeWidth(0);
    }

    @FXML private void createGameMouseClick() throws IOException {
        sendJSONData();
        sendJSONDataGameDetail();
        changeScene("PreGame","pre_game.fxml");
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
        comboMaxRobots.getItems().removeAll(comboMaxRobots.getItems());
        comboMaxRobots.getItems().addAll(1,2,3,4,5,6);
    }

    private void sendJSONData() {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/games/create");
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
            http.setRequestMethod("POST"); // PUT is another valid option
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        }
        http.setDoOutput(true);
        String gameName = txtFName.getText().substring(0,1).toUpperCase() + txtFName.getText().substring(1);
        byte[] out = (String.format(
                "{" +
                        "\"name\" : \"%s\"," +
                        "\"maxRobotCount\" : \"%d\"" +
                "}", gameName, comboMaxRobots.getValue())).getBytes(StandardCharsets.UTF_8);

        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
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
            Main.game = gson.fromJson(stringOfGames, Games.class);
//            String jsonOutput = gson.toJson(Main.game);
//            System.out.println(jsonOutput);
//            System.out.println(Main.game.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJSONDataGameDetail(){
        Main.gameDetails = new GameDetails(txtPlayerName.getText(), "", "PUT URL HERE", Main.game);
//        List<GameDetails> gameDetailsList = Main.game.getGameDetails();
//        gameDetailsList.add(gameDetails);
//        Main.game.setGameDetails(gameDetailsList);
        URL url = null;
        try {
            url = new URL("http://localhost:8080/gamedetails/create");
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
            http.setRequestMethod("POST"); // PUT is another valid option
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        }
        http.setDoOutput(true);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String jsonOutput = gson.toJson(Main.gameDetails);
        byte[] out = jsonOutput.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length); // THIS IS IMPORTANT. DOESN'T WORK WITHOUT THIS
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
            System.out.println(jsonOutput);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void changeScene(String path, String FXMLFile ) throws IOException {
        Stage stage = (Stage) txtCreateGame.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(String.format("../%s/%s",path, FXMLFile)));

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }

}
