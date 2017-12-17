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
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class Controller {

    boolean blockAnimation = false;

    ButtonPressInformation[] buttonInfo = { null, null };

    Game game;

    @FXML
    ImageView AnimationView;

    @FXML
    GridPane KingdomPlayer0;

    @FXML
    GridPane KingdomPlayer1;

    @FXML
    GridPane HandPlayer0;

    @FXML
    GridPane HandPlayer1;

    @FXML
    Text textDeckCardLeft;

    Animation animation;

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
                    if(game.IsCorrectCard(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId()))
                    {
                        PlayTurn(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId(), null);
                        //game.ChangeTurn();
                    }
                }
                //Reset the button
                buttonInfo[0] = null;
                buttonInfo[1] = null;
            }
        }
    }

    /*Hard check
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
    }*/

    public void DrawCard(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = game.playerList.get(player).getHand().DrawCard(game.deck, indexHand);
        UpdateMessageDeckCardLeft();
        animation.AnimateDrawCard(player,indexHand, card, latch);
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            if(game.ShouldDrawCard()) {
                CountDownLatch latch = new CountDownLatch(1);
                int indexHand = ListCard.NextEmptyIndex(game.playerList.get(player).getCardHand());
                DrawCard(player, indexHand, latch);
                //Wait the end of the animation
                latch.await();
            }
        }
    }

    public void Power(Card card, CountDownLatch latch) throws InterruptedException {
        if(card.equals(new Gnome()))
        {
            DrawMultipleCard(game.playerTurn, 2);
            latch.countDown();
        }
        else
        {
            latch.countDown();
        }
    }

    public void UpdateMessageDeckCardLeft()
    {
        textDeckCardLeft.setText("Card Left " + game.deck.size());
    }

    public void PlayGame()
    {
        try {
            DrawMultipleCard(0, 5);
            DrawMultipleCard(1, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch Play()
    {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                PlayGame();
                latch.countDown();
            }
        });

        //Need time to initialize the interface. Don't know how to do otherwise
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                (ActionEvent ae) -> {
                    t.start();
                }));
        timeline.play();

        return latch;
    }

    public void PlayRandomTurn(int nb, CountDownLatch latch)
    {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = game.NextCardHand();
            int indexKingdom = game.NextEmptyPlaceKingdom();
            PlayTurn(game.playerTurn, indexHand, indexKingdom, latch);
        }
    }

    public void PlayRandomTurnWithoutAnimation(int nb)
    {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = game.NextCardHand();
            int indexKingdom = game.NextEmptyPlaceKingdom();
            PlayTurnWithoutAnimation(game.playerTurn, indexHand, indexKingdom);
        }
    }

    // Qui sert  a rien
    // Index de la carte
    public void PlayTurnWithoutAnimation(int player, int indexHand, int indexKingdom)
    {
        animation.resetCard(true, player, indexHand);
        Card card = game.putCard(player, indexHand, indexKingdom);
        card.power(game,indexHand);
        animation.putCard(player, indexKingdom, card, null);
        //Do a draw card in
        game.ChangeTurn();
        if(game.ShouldDrawCard())
        {
            try {
                int indexHandNextEmpty = game.NextEmptyIndex();
                DrawCardWithoutAnimationInterface(game.playerTurn,indexHandNextEmpty);
                //game.DrawCardWithoutInterface(game.playerTurn, indexHand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(game.ShouldResetKingdom())
        {
            ResetKingdom(game.playerTurn);
        }
    }

    // Index de la carte
    public void PlayTurn(int player, int indexHand, int indexKingdom, CountDownLatch latch)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch latchPutCard = new CountDownLatch(1);
                Card card = game.putCard(player, indexHand, indexKingdom);
                animation.AnimatePutCard(player, indexHand, indexKingdom, card, latchPutCard);
                try {
                    //Wait the end of the animation
                    latchPutCard.await();
                    CountDownLatch latchPower = new CountDownLatch(1);
                    Power(card, latchPower);
                    //Do a draw card in
                    game.ChangeTurn();
                    if(game.ShouldResetKingdom())
                    {
                        ResetKingdom(game.playerTurn);
                    }
                    if(game.ShouldDrawCard())
                    {
                        try {
                            int indexHandNextEmpty = game.NextEmptyIndex();
                            DrawCard(game.playerTurn,indexHandNextEmpty, latch);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        if(latch != null)
                        {
                            latch.countDown();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void ResetKingdom(int indexKingdom)
    {
        animation.FieldToReverse(game.playerTurn);
        game.ResetKingdom();
    }

    /*Junit interface test method*/
    public void DrawCardWithoutAnimationInterface(int player, int indexHand) throws InterruptedException {
        Card card = game.playerList.get(player).getHand().DrawCard(game.deck, indexHand);
        animation.EndDrawCardAnimation(player,indexHand, card, null);
        UpdateMessageDeckCardLeft();
    }

    public void DrawMultipleCardWithoutAnimationInterface(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = ListCard.FindIndex(i);
            DrawCardWithoutAnimationInterface(player,indexHand);
        }
        UpdateMessageDeckCardLeft();
    }
    /*End of junit interface test method*/

    @FXML
    public void initialize() {
        game = new Game();
        animation = new Animation(AnimationView, KingdomPlayer0, KingdomPlayer1, HandPlayer0, HandPlayer1, blockAnimation);
    }
}
