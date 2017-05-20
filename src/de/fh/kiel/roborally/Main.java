package de.fh.kiel.roborally;

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

import java.io.File;
import java.util.List;

public class Main extends Application {

    /*
    Store the created or joined game in a global variable so it is easily accessible every where
     */
    public static Games game;

    /*
    Store the name of the player for this client
     */
    public static GameDetails gameDetails;

    public static List<GameDetails> gameDetailsList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Instead of Loading the Main page, load the Splash Screen
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

    public static void playHoverSound(){
        String musicFile = "resources/menu_item_hover.wav";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
