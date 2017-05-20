package de.fh.kiel.roborally;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fh.kiel.roborally.Games.GameDetails;
import de.fh.kiel.roborally.Games.Games;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main extends Application {

    /**
     * Storing the name of the Player as a global variable
     */
    public static final String playerName = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * Instead of loading the main screen we'll load the splash screen for a better UI
         * Commenting out the splash screen so it's easier to test as the application will be run again and again
         */
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen/main_screen.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen/splash_screen.fxml"));

//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Robo Rally");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Play the sound when the mouse hovers on a text item
     */
    public static void playHoverSound(){
        String musicFile = "resources/menu_item_hover.wav";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Creates a new game with a random ID and 1 Robot (the player who created it)
     *
     * @param out the JSON to be sent
     * @param URL the URL to post. FOR EXAMPLE /games/create
     * @return the game that was created
     */
    public static void postJSON(byte[] out, String URL){
        URL url = null;
        try {
            url = new URL("http://localhost:8080" + URL);
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

        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try(OutputStream outputStream = http.getOutputStream()) {
            outputStream.write(out);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try(InputStream inputStream = http.getInputStream()){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line+"\n");
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
