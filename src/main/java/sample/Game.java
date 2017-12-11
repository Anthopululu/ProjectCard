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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class Game {
        List<Card> deck;

        List<Card> handPlayer1;

        List<Card> handPlayer2;

        List<Card> kingdomPlayer1;

        List<Card> kingdomPlayer2;

        // 0 : Fade animation delay, 1 : Path Animation Duration
        static int[] delayAnimation = { 500, 500};

        boolean blockAnimation = false;

        static int NB_CARD = 10;//Number of card in the hand and in the kingdom

        Animation animation;

        ImageView AnimationView;

        GridPane KingdomPlayer1;

        GridPane KingdomPlayer2;

        GridPane HandPlayer1;

        GridPane HandPlayer2;

        int playerTurn;

        public Game()
        {
                playerTurn = 1;
                deck = new ArrayList();
                kingdomPlayer1 = InitialiseListCard(10);
                kingdomPlayer2 = InitialiseListCard(10);
                handPlayer1 = InitialiseListCard(10);
                handPlayer2 = InitialiseListCard(10);
        }


        public Game(ImageView AnimationView,GridPane KingdomPlayer1,GridPane KingdomPlayer2,GridPane HandPlayer1,GridPane HandPlayer2)
        {
            deck = new ArrayList();
            this.CreateDeck();
            playerTurn = 1;
            kingdomPlayer1 = InitialiseListCard(10);
            kingdomPlayer2 = InitialiseListCard(10);
            handPlayer1 = InitialiseListCard(10);
            handPlayer2 = InitialiseListCard(10);
            this.AnimationView = AnimationView;
            this.KingdomPlayer1 = KingdomPlayer1;
            this.KingdomPlayer2 = KingdomPlayer2;
            this.HandPlayer1 = HandPlayer1;
            this.HandPlayer2 = HandPlayer2;
            animation = new Animation(AnimationView, KingdomPlayer1, KingdomPlayer2, HandPlayer1, HandPlayer2, blockAnimation);
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
                for (int i = 0; i < 60; i++) {
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
        animation.AnimateDrawCard(player,indexHand, card, latch);
        if(playerTurn % 2 == 0)
        {
            handPlayer1.set(indexHand, card);
        }
        if(playerTurn % 2 == 1)
        {
            handPlayer2.set(indexHand, card);
        }
        animation.AnimateDrawCard(player,indexHand, card, latch);
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
                    DrawMultipleCard(1, 5);
                    DrawMultipleCard(2, 5);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1500),
                (ActionEvent ae) -> {
                    t.start();
                }));
        timeline.play();

        return latch;
    }
}
