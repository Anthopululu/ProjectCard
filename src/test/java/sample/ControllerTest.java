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

    //@Given("^my hand is filled$")

    public void handFilled()
    {

        List<Card> listType = Arrays.asList(new Dryad(),new Gnome(),new Gnome(),new Elf(), new Goblin());
        List<Card> listType2 = Arrays.asList(new Elf(), new Goblin(),new Dryad(),new Gnome(),new Elf());
        try {
            controller.game.DrawMultipleCard(1, 5);
            controller.game.DrawMultipleCard(2, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    @Test
    public void AnimatePutCard() throws InterruptedException {
        handFilled();
        CountDownLatch latch = new CountDownLatch(1);
        controller.game.AnimatePutCard(2,3, 3, latch);
        latch.await();
    }

    @Test
    public void DrawFirstCard() throws InterruptedException {
        CountDownLatch latch = controller.Play();
        latch.await();
    }

}