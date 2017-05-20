package de.fh.kiel.roborally.SplashScreen;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class SplashScreen implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML private MediaView mediaView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new TheSplashScreen().start();
        String videoFile = "resources/splash_video.flv";     // For example
        MediaPlayer video = new MediaPlayer(new Media(new File(videoFile).toURI().toString()));
        MediaView mediaPlayer = new MediaView(video);
        rootPane.getChildren().add(mediaPlayer);
        video.play();
    }

    class TheSplashScreen extends Thread {
        @Override
        public void run(){
            try {

                Thread.sleep(5000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            startBackgroundMusic();
                            root = FXMLLoader.load(getClass().getResource("../MainScreen/main_screen.fxml"));
                            Stage primaryStage = new Stage();
                            primaryStage.setTitle("Robo Rally");
                            primaryStage.setScene(new Scene(root));
                            primaryStage.setMaximized(true);
                            primaryStage.show();

                            rootPane.getScene().getWindow().hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBackgroundMusic() {
        final Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                String musicFile = "resources/game_music.mp3";
                int s = INDEFINITE;
                AudioClip audio = new AudioClip(new File(musicFile).toURI().toString());
                audio.setVolume(0.15f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
