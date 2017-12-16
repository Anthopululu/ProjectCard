package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.CountDownLatch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Card game");
        Scene scene = new Scene(root, 1280, 720);
        Controller controller = loader.getController();

        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                (ActionEvent ae) -> {
                    controller.Play();
                }));
        timeline.play();

    }




    public static void main(String[] args) {
        launch(args);
        Game g = new Game();
        System.out.println(g.toString()+ "HAHAHA");
    }
}
