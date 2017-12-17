package sample;

import com.sun.javafx.application.PlatformImpl;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

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

    public void handFilledInterface()
    {
        try {
            controller.DrawMultipleCardWithoutAnimationInterface(0, 5);
            controller.DrawMultipleCardWithoutAnimationInterface(1, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void AnimatePutCard() throws InterruptedException {
        handFilledInterface();
        CountDownLatch latch = new CountDownLatch(1);
        Card card = Card.RandomCard();
        controller.animation.AnimatePutCard(1,3, 3, card, latch);
        latch.await();
    }

    @Test
    public void DrawFirstCard() throws InterruptedException {
        controller.DrawMultipleCard(0, 5);
        controller.DrawMultipleCard(1, 5);
    }

    @Test
    public void EndGameDraw() throws InterruptedException {
        controller.DrawMultipleCardWithoutAnimationInterface(0,5);
        controller.DrawMultipleCardWithoutAnimationInterface(1,5);
        controller.game.endGame = true;
        controller.CheckEndGameMessage();
        Thread.sleep(500);
    }

    @Test
    public void EndGameWin() throws InterruptedException {
        controller.DrawMultipleCardWithoutAnimationInterface(0,5);
        controller.DrawMultipleCardWithoutAnimationInterface(1,5);
        controller.game.playerList.get(1).kingdom.getKingdom().set(0, new Gnome());
        controller.game.endGame = true;
        controller.CheckEndGameMessage();
        Thread.sleep(500);
    }
}