package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

    public void handFilled()
    {
        List<String> listType = Arrays.asList("dryad","gnome","gnome","elf","goblin");
        List<String> listType2 = Arrays.asList("elf","gnome","dryad","goblin");
        try {
            controller.DrawMultipleCard(1, listType);
            controller.DrawMultipleCard(2, listType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AnimateDrawCard() throws InterruptedException {
        handFilled();
        CountDownLatch latch = new CountDownLatch(1);
        controller.AnimateDrawCard(2,9, "elf", latch);
        latch.await();
    }

    @Test
    public void AnimatePutCard() throws InterruptedException {
        handFilled();
        CountDownLatch latch = new CountDownLatch(1);
        controller.AnimatePutCard(2,3, 3, latch);
        latch.await();
    }

    @Test
    public void FieldToReverse() throws InterruptedException {
        handFilled();
        controller.FieldToReverse("hand",1);
        controller.FieldToReverse("kingdom",2);
        Thread.sleep(1000);
    }

    @Test
    public void DrawFirstCard() throws InterruptedException {
        CountDownLatch latch = controller.Play();
        latch.await();
    }

}