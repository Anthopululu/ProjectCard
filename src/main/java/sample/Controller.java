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
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
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

    @FXML
    Text textScorePlayer0;

    @FXML
    Text textScorePlayer1;

    @FXML
    Polygon FramePlayer0;

    @FXML
    Polygon FramePlayer1;

    @FXML
    Text Player0TextEndGame;

    @FXML
    Text Player1TextEndGame;

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
                    if(!game.IsTheEnd())
                    {
                        //Check if it's a reverse card for the hand, the opposite for the kingdom
                        if(game.IsCorrectCard(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId()))
                        {
                            PlayTurn(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId(), null);
                            //game.ChangeTurn();
                        }
                    }
                }
                //Reset the button
                buttonInfo[0] = null;
                buttonInfo[1] = null;
            }
        }
    }

    public void DrawCard(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = game.playerList.get(player).getHand().DrawCard(game.deck, indexHand);
        UpdateMessageTurn();
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

    public void Power(Card card, int indexKingdom, CountDownLatch latch) throws InterruptedException {
        //Draw 2 cards
        if(card instanceof Gnome)
        {
            DrawMultipleCard(game.playerTurn, 2);
            latch.countDown();
        }
        //draw 2 random cards within your opponent hand
        else if (card instanceof Korrigan)
        {
            //latch.countDown();
            CountDownLatch latchFirstDraw = new CountDownLatch(1);
            animation.DrawCardOpponent(game.playerTurn, game.playerList.get(Game.Opponent(game.playerTurn)).hand, latchFirstDraw);
            latchFirstDraw.await();
            animation.DrawCardOpponent(game.playerTurn, game.playerList.get(Game.Opponent(game.playerTurn)).hand, latch);
        }
        //switch your hand with you opponent
        else if(card instanceof Goblin)
        {
            int opponent = Game.Opponent(game.playerTurn);
            List<Card> playerCardHand = game.playerList.get(game.playerTurn).getCardHand();
            List<Card> opponentCardHand = game.playerList.get(opponent).getCardHand();
            animation.AnimateSwitchHand(game.playerTurn, opponent, playerCardHand, opponentCardHand, latch);
        }
        //copy and use the power of one of the card in front of you
        else if(card instanceof Elf)
        {
            int opponentTurn = Game.Opponent(game.playerTurn);
            Card advCard = game.playerList.get(opponentTurn).getCardKingdom().get(indexKingdom);
            if(advCard instanceof Elf | advCard instanceof DefaultCard)
            {
                Power(advCard, indexKingdom,latch);
            }
            else
            {
                latch.countDown();
            }
        }
        //stole a card in front of your opponent and add it in front of you without activating its power.
        else if(card instanceof Dryad)
        {
            int opponentTurn = Game.Opponent(game.playerTurn);
            Kingdom kingdomPlayer = game.playerList.get(game.playerTurn).kingdom;
            Kingdom kingdomOpponent = game.playerList.get(opponentTurn).kingdom;
            animation.PutCardOpponent(game.playerTurn, kingdomPlayer, kingdomOpponent, latch);
        }
        //swap the cards in front of you with the cards in front of your opponent
        else if(card instanceof Troll)
        {
            int opponent = Game.Opponent(game.playerTurn);
            List<Card> playerCardKingdom = game.playerList.get(game.playerTurn).getCardKingdom();
            List<Card> opponentCardKingdom = game.playerList.get(opponent).getCardKingdom();
            animation.AnimateSwitchKingdom(game.playerTurn, opponent, playerCardKingdom, opponentCardKingdom, latch);
        }
        else
        {
            latch.countDown();
        }
    }

    public void UpdateMessageTurn()
    {
        int scorePlayer0 = game.Score(0);
        int scorePlayer1 = game.Score(1);
        if(game.playerTurn == 0)
        {
            FramePlayer0.setVisible(true);
            FramePlayer1.setVisible(false);
        }
        if(game.playerTurn == 1)
        {
            FramePlayer1.setVisible(true);
            FramePlayer0.setVisible(false);
        }
        textScorePlayer0.setText("Score : " + scorePlayer0);
        textScorePlayer1.setText("Score : " + scorePlayer1);
        textDeckCardLeft.setText("Card Left " + game.deck.size());
    }

    public void CheckEndGameMessage()
    {
        if(game.endGame)
        {
            int scorePlayer0 = game.Score(0);
            int scorePlayer1 = game.Score(1);
            FramePlayer0.setVisible(true);
            FramePlayer1.setVisible(true);
            if(scorePlayer0 > scorePlayer1)
            {
                FramePlayer0.setStroke(Color.GREEN);
                Player0TextEndGame.setText("Winner !");
                Player0TextEndGame.setFill(Color.GREEN);

                Player1TextEndGame.setText("Loser !");
                Player1TextEndGame.setFill(Color.RED);
                FramePlayer1.setStroke(Color.RED);
            }
            if(scorePlayer0 < scorePlayer1)
            {
                FramePlayer0.setStroke(Color.RED);
                Player0TextEndGame.setText("Loser !");
                Player0TextEndGame.setFill(Color.RED);

                Player1TextEndGame.setText("Winner !");
                Player1TextEndGame.setFill(Color.GREEN);
                FramePlayer1.setStroke(Color.GREEN);
            }
            if(scorePlayer0 == scorePlayer1)
            {
                FramePlayer0.setStroke(Color.YELLOW);
                Player0TextEndGame.setText("Draw !");
                Player0TextEndGame.setFill(Color.YELLOW);

                FramePlayer1.setStroke(Color.YELLOW);
                Player1TextEndGame.setText("Draw !");
                Player1TextEndGame.setFill(Color.YELLOW);
            }
            Player0TextEndGame.setVisible(true);
            Player1TextEndGame.setVisible(true);
            textScorePlayer0.setText("Score : " + scorePlayer0);
            textScorePlayer1.setText("Score : " + scorePlayer1);
            textDeckCardLeft.setText("Card Left " + game.deck.size());
        }
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
                    Power(card, indexKingdom, latchPower);
                    latchPower.await();
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
                    game.CheckEndGame();
                    CheckEndGameMessage();
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
        animation.SetCard(true, player,indexHand, card, null);
        UpdateMessageTurn();
    }

    public void DrawMultipleCardWithoutAnimationInterface(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = ListCard.FindIndex(i);
            DrawCardWithoutAnimationInterface(player,indexHand);
        }
        UpdateMessageTurn();
    }
    /*End of junit interface test method*/

    @FXML
    public void initialize() {
        FramePlayer0.toBack();
        FramePlayer1.toBack();
        game = new Game();
        animation = new Animation(AnimationView, KingdomPlayer0, KingdomPlayer1, HandPlayer0, HandPlayer1, blockAnimation);
    }
}