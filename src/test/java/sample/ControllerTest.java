package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.input.KeyCode.ENTER;
import static org.junit.Assert.*;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;

public class ControllerTest extends ApplicationTest {

    Controller controller;
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setTitle("Card game");
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void DrawCard() throws InterruptedException {
        controller.AnimateDrawCard(2,9, "elf");
        Thread.sleep(2000);
    }

    @Test
    public void PutCard() throws InterruptedException {
        controller.AnimatePutCard(2,3, 3);
        Thread.sleep(2000);
    }

}