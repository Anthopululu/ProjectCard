package sample;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.json.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class Controller {
    // 0 : Fade animation delay, 1 : Path Animation Duration
    int[] delayAnimation = { 500, 500};

    boolean blockAnimation = false;

    int NB_CARD = 10;//Number of card in the hand and in the kingdom

    ButtonPressInformation[] buttonInfo = { null, null };

    Game game;

    @FXML
    ImageView AnimationView;

    @FXML
    GridPane KingdomPlayer1;

    @FXML
    GridPane KingdomPlayer2;

    @FXML
    GridPane HandPlayer1;

    @FXML
    GridPane HandPlayer2;

    @FXML
    private void numChange(ActionEvent event) {
        if(blockAnimation)
        {
            System.out.println("Wait the end of the animation");
        }
        else
        {
            //Get the userData element. A pattern is created to know which button are being pressed
            Node node = (Node) event.getSource() ;
            String data = (String) node.getUserData();
            JSONObject json = new JSONObject(data);
            ButtonPressInformation u = new ButtonPressInformation(json.getInt("id"), json.getInt("player"), json.getBoolean("hand"));

            //Keep the last 2 button of the player turn. One from the hand, the other from the kingdom
            if(u.isHand())
            {
                buttonInfo[0] = u;
            }
            if(!u.isHand())
            {
                buttonInfo[1] = u;
            }
            if(buttonInfo[0] != null && buttonInfo[1] != null)
            {
                if(buttonInfo[0].getPlayer() == buttonInfo[1].getPlayer())//Same player card
                {
                    //Check if it's a reverse card for the hand, the opposite for the kingdom
                    if(IsCorrectCardType(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId()))
                    {
                        //System.out.println(s.lo);
                        game.AnimatePutCard(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId(), null);
                    }
                }
                buttonInfo[0] = null;
                buttonInfo[1] = null;
            }
        }
    }

    //Check if it is a correct card or only the reverse default card
    public boolean IsCorrectCardType(int playerTurn, int indexHand, int indexKingdom)
    {
        boolean result = false;
        Scene s = AnimationView.getScene();

        //Get the image
        Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
        Button kingdomCard = (Button) s.lookup("#KingdomPlayer"+playerTurn+"_Card" + indexKingdom);

        //Check their style class to know if it's a reverse or not
        if(!handCard.getStyleClass().contains("Reverse"))
        {
            if(kingdomCard.getStyleClass().contains("Reverse"))
            {
                result = true;
            }
        }
        return result;
    }

    public CountDownLatch Play()
    {
        return game.Play();
    }

    @FXML
    public void initialize() {
        game = new Game(AnimationView, KingdomPlayer1, KingdomPlayer2, HandPlayer1, HandPlayer2);

    }
}
