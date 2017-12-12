package sample;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class Game {
    static int NB_CARD = 10;//Number of card in the hand and in the kingdom
    static int NB_CARD_DECK = 45;
    List<Card> deck;
    List<List<Card>> handPlayer;
    List<List<Card>> kingdomPlayer;
    boolean blockAnimation = false;
    int playerTurn;

    Animation animation;

    Text textDeckCardLeft;

    public Game()
    {
            //playerTurn = 1;
            deck = new ArrayList();
            kingdomPlayer = Arrays.asList(InitialiseListCard(10), InitialiseListCard(10));
            handPlayer = Arrays.asList(InitialiseListCard(10), InitialiseListCard(10));
            playerTurn = 1;
    }

    public Game(ImageView AnimationView,GridPane KingdomPlayer1,GridPane KingdomPlayer2,GridPane HandPlayer1,GridPane HandPlayer2, Text textDeckCardLeft)
    {
        deck = new ArrayList();
        this.CreateDeck();
        kingdomPlayer = Arrays.asList(InitialiseListCard(10), InitialiseListCard(10));
        handPlayer = Arrays.asList(InitialiseListCard(10), InitialiseListCard(10));
        animation = new Animation(AnimationView, KingdomPlayer1, KingdomPlayer2, HandPlayer1, HandPlayer2, blockAnimation);
        this.textDeckCardLeft = textDeckCardLeft;

        playerTurn = 1;
    }

    public boolean IsCorrectCard(int player, int indexHand, int indexKingdom)
    {
        boolean result = true;
        if(player == playerTurn)
        {
            if(handPlayer.get(player-1).get(indexHand).equals(Card.DEFAULT_CARD))
            {
                result = false;
            }
            if(!kingdomPlayer.get(player-1).get(indexKingdom).equals(Card.DEFAULT_CARD))
            {
                result = false;
            }
        }
        else
        {
            result = false;
        }
        return result;
    }

    public List<Card> InitialiseListCard(int number)
    {
            List<Card> result = new ArrayList<>();
            for(int i = 0; i < number;i++)
            {
                    result.add(new DefaultCard());
            }
            return result;
    }

    public List<Card> CreateDeck(){
            for (int i = 0; i < NB_CARD_DECK; i++) {
                    Random rand = new Random();
                    int n = rand.nextInt(6);
                    if (n == 0) {
                            deck.add(new Dryad());
                    }
                    if (n == 1) {
                            deck.add(new Elf());
                    }
                    if (n == 2) {
                            deck.add(new Gnome());
                    }
                    if (n == 3) {
                            deck.add(new Goblin());
                    }
                    if (n == 4) {
                            deck.add(new Troll());
                    }
                    if (n == 5) {
                            deck.add(new Korrigan());
                    }

            }
            return deck;
    }

    public void DrawCard(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = deck.remove(deck.size()-1);
        UpdateMessageDeckCardLeft();
        card.toHand();
        handPlayer.get(player-1).set(indexHand, card);
        animation.AnimateDrawCard(player,indexHand, card, latch);
    }

    void UpdateMessageDeckCardLeft()
    {
        textDeckCardLeft.setText("Card Left " + deck.size());
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = SequenceNumberMiddle10(i);
            DrawCard(player,indexHand, latch);
            latch.await();
        }
    }

    public void putCard(int player, int indexHand, int indexKingdom, CountDownLatch latch)
    {
        animation.AnimatePutCard(player, indexHand, indexKingdom, latch);
        Card card =  handPlayer.get(player-1).set(indexHand, new DefaultCard());
        kingdomPlayer.get(player-1).set(indexKingdom, card);
        card.toKingdom();
    }

    /*Junit test method*/
    public void DrawCardWithoutAnimation(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = deck.remove(deck.size()-1);
        animation.EndDrawCardAnimation(player,indexHand, card, latch);
        handPlayer.get(player-1).set(indexHand, card);
    }

    public void DrawMultipleCardWithoutAnimation(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = SequenceNumberMiddle10(i);
            DrawCardWithoutAnimation(player,indexHand, latch);
            latch.await();
        }
        UpdateMessageDeckCardLeft();
    }
    /*End of junit test method*/
    public int SequenceNumberMiddle10(int index)
    {
        int u0 = 5;
        double t = Math.pow(-1, index);
        double i = Math.ceil((double)index/2);
        int result = (int)(u0 + t * i);
        return result;
    }

    public CountDownLatch Play()
    {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DrawMultipleCardWithoutAnimation(1, 5);
                    DrawMultipleCardWithoutAnimation(2, 5);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                (ActionEvent ae) -> {
                    t.start();
                }));
        timeline.play();

        return latch;
    }

    public void ChangeTurn()
    {
        if(playerTurn == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
        int indexHand = nextEmptyIndexSequenceNumberMiddle10(playerTurn);
        indexHand = SequenceNumberMiddle10(indexHand);
        //Draw card if we have some place in the list card
        if(indexHand > -1)
        {
            try {
                DrawCard(playerTurn,indexHand,null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //DrawCard(playerTurn,);
    }

    public void PlayTurn(int player, int indexHand, int indexKingdom)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch latch = new CountDownLatch(1);
                putCard(player, indexHand, indexKingdom, latch);
                try {
                    latch.await();
                    ChangeTurn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public int nextEmptyIndexSequenceNumberMiddle10(int player)
    {
        int result = 0;
        //Check if exists
        if(handPlayer.get(player-1).contains(Card.DEFAULT_CARD))
        {
            int i = 0;
            Card card = handPlayer.get(player-1).get(SequenceNumberMiddle10(i));
            while(!card.equals(Card.DEFAULT_CARD))
            {
                i++;
                card = handPlayer.get(player-1).get(SequenceNumberMiddle10(i));
            }
            result = i;
        }
        else
        {
            result = -1;
        }
        return result;
    }

    @Override
    public String toString() {
        return "handPlayer1=" + handPlayer.get(0) +
                "\nkingdomPlayer1=" + kingdomPlayer.get(0) +
                "\nhandPlayer2=" + handPlayer.get(1) +
                "\nkingdomPlayer2=" + kingdomPlayer.get(1);
    }
}
