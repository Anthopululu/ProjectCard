package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("Card game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                ae -> controller.Play()));
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
        Game g = new Game();
    }
}
