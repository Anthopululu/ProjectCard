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
        int[] delayAnimation = { 500, 500};

        boolean blockAnimation = false;

        int NB_CARD = 10;//Number of card in the hand and in the kingdom

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

        /*Animation method*/

        public void AnimatePutCard(int playerTurn, int indexHand, int indexKingdom, CountDownLatch latch)
        {
                blockAnimation = true;
                //Coordinate where the animation trigger and stop
                double handCoordX = 0;
                double handCoordY = 0;
                double kingdomCoordX = 0;
                double kingdomCoordY = 0;

                //Each case
                if(playerTurn == 1)
                {
                        handCoordX = HandPlayer1.getLayoutX()+36 + HandPlayer1.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
                        handCoordY = HandPlayer1.getLayoutY()+60;
                        kingdomCoordX = KingdomPlayer1.getLayoutX()+36 + KingdomPlayer1.getWidth() * 0.1 * indexKingdom;//Pattern created into the fxml
                        kingdomCoordY = KingdomPlayer1.getLayoutY()+50;
                }
                if(playerTurn == 2) {
                        handCoordX = HandPlayer2.getLayoutX() + 36 + HandPlayer2.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
                        handCoordY = HandPlayer2.getLayoutY() + 60;
                        kingdomCoordX = KingdomPlayer2.getLayoutX() + 36 + KingdomPlayer2.getWidth() * 0.1 * indexKingdom;//Pattern created into the fxml
                        kingdomCoordY = KingdomPlayer2.getLayoutY() + 50;
                }

                //Do the linear animation
                PathTransition path = PathAnimationCard(handCoordX, handCoordY, kingdomCoordX, kingdomCoordY);

                FadeTransition fade = FadeAnimationCard();//Start the fade animation
                //Return the class style and reset the card
                ObservableList<String> tmp = resetCard(true, playerTurn, indexHand);

                SequentialTransition seqT = new SequentialTransition (path, fade);
                seqT.play();
                seqT.setOnFinished(e -> putCard(playerTurn, indexKingdom, tmp, latch));//Put the card at the end of fade animation. Delay function included);

                //Display the animated card
                AnimationView.setVisible(true);
        }

        public FadeTransition FadeAnimationCard()
        {
                //Fade animation of the AnimationView
                FadeTransition ft = new FadeTransition(Duration.millis(delayAnimation[0]), AnimationView);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.setCycleCount(1);
                ft.setAutoReverse(false);
                return ft;
        }

        public void ResetAnimationView()
        {
                //Reset card opacity and visibility to none
                AnimationView.setOpacity(1);
                AnimationView.setVisible(false);
                AnimationView.setVisible(false);
        }

        public PathTransition PathAnimationCard(double coordX, double coordY, double toCoordX, double toCoordY)
        {
                //Create the path of the animation
                Path path = new Path();
                path.getElements().add(new MoveTo(coordX,coordY));
                path.getElements().add(new LineTo(toCoordX,toCoordY));
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(delayAnimation[1]));
                pathTransition.setPath(path);
                pathTransition.setNode(AnimationView);
                pathTransition.setCycleCount(1);
                return pathTransition;
        }

        public void putCard(int playerTurn, int indexKingdom, ObservableList<String> card, CountDownLatch latch)
        {
                Scene s = AnimationView.getScene();
                //Change the image of the button in the index
                Button kingdomCard = (Button) s.lookup("#KingdomPlayer"+playerTurn+"_Card" + (indexKingdom));
                kingdomCard.getStyleClass().clear();
                kingdomCard.getStyleClass().addAll(card);
                blockAnimation = false;

                if(latch != null)
                {
                        latch.countDown(); // Release await() in the test thread.
                }
        }

        public ObservableList<String> resetCard(boolean isHand, int playerTurn, int index)
        {
                Scene s = AnimationView.getScene();
                String tmpString = "";
                if(isHand)
                {
                        tmpString = "Hand";
                }
                if(!isHand)
                {
                        tmpString = "Kingdom";
                }
                Button handCard = (Button) s.lookup("#"+tmpString+"Player"+playerTurn+"_Card" + index);

                //Copy the value, not the pointer
                ObservableList<String> tmp = FXCollections.observableArrayList( handCard.getStyleClass());

                //Set to default card
                handCard.getStyleClass().clear();
                DefaultCard d = new DefaultCard();
                handCard.getStyleClass().addAll(d.getClassString());

                //Return the class of the card deleted
                return tmp;
        }

        public void AnimateDrawCard(int playerTurn, int indexHand, CountDownLatch latch)
        {
                blockAnimation = true;

                double handCoordX = 0;
                double handCoordY = 0;

                //Each case
                if(playerTurn == 1)
                {
                        handCoordX = HandPlayer1.getLayoutX()+36 + HandPlayer1.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
                        handCoordY = HandPlayer1.getLayoutY()+60;
                }
                if(playerTurn == 2) {
                        handCoordX = HandPlayer2.getLayoutX() + 36 + HandPlayer2.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
                        handCoordY = HandPlayer2.getLayoutY() + 60;
                }
                //Do the linear animation
                PathTransition path = PathAnimationCard(625, 375, handCoordX, handCoordY);

                FadeTransition fade = FadeAnimationCard();//The fade animation

                SequentialTransition seqT = new SequentialTransition (path, fade);
                seqT.setOnFinished(e -> drawCard(playerTurn, indexHand, latch));//Put the card at the end of fade animation.
                seqT.play();
                //Display the animated card
                AnimationView.setVisible(true);
        }

        public void drawCard(int playerTurn, int indexHand, CountDownLatch latch)
        {
                Scene s = AnimationView.getScene();
                Card c = deck.remove(deck.size()-1);
                if(playerTurn % 2 == 0)
                {
                        handPlayer1.add(c);
                }
                if(playerTurn % 2 == 1)
                {
                        handPlayer2.add(c);
                }

                //Set the card value
                Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
                handCard.getStyleClass().clear();
                handCard.getStyleClass().addAll( c.getClassString());
                blockAnimation = false;

                ResetAnimationView();

                if(latch != null)
                {
                        latch.countDown(); // Release await() in the test thread.
                }

        }

        public void DrawAnimationMultipleCard(int player, int nb) throws InterruptedException {
                for(int i = 0; i < nb;i++)
                {
                        CountDownLatch latch = new CountDownLatch(1);
                        int indexHand = SequenceNumberMiddle10(i);
                        AnimateDrawCard(player,indexHand, latch);
                        latch.await();
                }
        }

        public void DrawMultipleCard(int player, int nb) throws InterruptedException {
                for(int i = 0; i < nb;i++)
                {
                        int indexHand = SequenceNumberMiddle10(i);
                        drawCard(player,indexHand, null);
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

        public void FieldToReverse(String type, int player)
        {
                String name = "";
                if(type == "kingdom")
                {
                        name = "#KingdomPlayer"+player+"_Card";
                }
                if(type == "hand")
                {
                        name = "#HandPlayer"+player+"_Card";
                }

                for(int i = 0; i < NB_CARD; i++)
                {
                        Scene s = KingdomPlayer1.getScene();
                        Button b = (Button) s.lookup(name+i);
                        b.getStyleClass().clear();
                        DefaultCard d = new DefaultCard();
                        b.getStyleClass().addAll(d.getClassString());
                }

        }

        public CountDownLatch Play()
        {
                CountDownLatch latch = new CountDownLatch(1);
                Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        DrawAnimationMultipleCard(1, 5);
                                        DrawAnimationMultipleCard(2, 5);
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

        /*End of animation method*/
}
