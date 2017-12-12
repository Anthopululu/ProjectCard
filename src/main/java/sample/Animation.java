package sample;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.concurrent.CountDownLatch;

public class Animation {

    ImageView AnimationView;

    GridPane KingdomPlayer1;

    GridPane KingdomPlayer2;

    GridPane HandPlayer1;

    GridPane HandPlayer2;

    boolean blockAnimation;

    // 0 : Fade animation delay, 1 : Path Animation Duration
    final int[] DELAY_ANIMATION = { 500, 500};

    final double[] DECK_COORD = {625, 375};

    public Animation(ImageView AnimationView,GridPane KingdomPlayer1,GridPane KingdomPlayer2,GridPane HandPlayer1,GridPane HandPlayer2, boolean blockAnimation)
    {
        this.AnimationView = AnimationView;
        this.KingdomPlayer1 = KingdomPlayer1;
        this.KingdomPlayer2 = KingdomPlayer2;
        this.HandPlayer1 = HandPlayer1;
        this.HandPlayer2 = HandPlayer2;
        this.blockAnimation = blockAnimation;
    }

    public void AnimatePutCard(int playerTurn, int indexHand, int indexKingdom, CountDownLatch latch)
    {
        blockAnimation = true;
        //Coordinate where the animation trigger and stop
        double[] handCoord = PatternIndexHandToCoord(playerTurn, indexHand);
        double[] kingdomCoord = PatternindexKingdomToCoord(playerTurn, indexKingdom);

        //Do the linear animation
        PathTransition path = PathAnimationCard(handCoord[0], handCoord[1], kingdomCoord[0], kingdomCoord[1]);

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
        FadeTransition ft = new FadeTransition(Duration.millis(DELAY_ANIMATION[0]), AnimationView);
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
        pathTransition.setDuration(Duration.millis(DELAY_ANIMATION[1]));
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

    public void AnimateDrawCard(int playerTurn, int indexHand, Card card, CountDownLatch latch)
    {
        blockAnimation = true;

        double[] handCoord = PatternIndexHandToCoord(playerTurn, indexHand);
        //Do the linear animation
        PathTransition path = PathAnimationCard(DECK_COORD[0], DECK_COORD[1], handCoord[0], handCoord[1]);

        FadeTransition fade = FadeAnimationCard();//The fade animation

        SequentialTransition seqT = new SequentialTransition (path, fade);
        seqT.setOnFinished(e -> EndDrawCardAnimation(playerTurn, indexHand, card,  latch));//Put the card at the end of fade animation.
        seqT.play();
        //Display the animated card
        AnimationView.setVisible(true);
    }

    public double[] PatternIndexHandToCoord(int playerTurn, int indexHand)
    {
        double[] handCoord = new double[2];

        //Each case
        if(playerTurn == 1)
        {
            handCoord[0] = HandPlayer1.getLayoutX()+36 + HandPlayer1.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
            handCoord[1] = HandPlayer1.getLayoutY()+60;
        }
        if(playerTurn == 2) {
            handCoord[0] = HandPlayer2.getLayoutX() + 36 + HandPlayer2.getWidth() * 0.1 * indexHand;//Pattern created into the fxml
            handCoord[1] = HandPlayer2.getLayoutY() + 60;
        }
        return handCoord;
    }

    public double[] PatternindexKingdomToCoord(int playerTurn, int indexKingdom)
    {
        double[] kingdomCoord = new double[2];
        //Each case
        if(playerTurn == 1)
        {
            kingdomCoord[0] = KingdomPlayer1.getLayoutX()+36 + KingdomPlayer1.getWidth() * 0.1 * indexKingdom;//Pattern created into the fxml
            kingdomCoord[1] = KingdomPlayer1.getLayoutY()+50;
        }
        if(playerTurn == 2) {
            kingdomCoord[0] = KingdomPlayer2.getLayoutX() + 36 + KingdomPlayer2.getWidth() * 0.1 * indexKingdom;//Pattern created into the fxml
            kingdomCoord[1] = KingdomPlayer2.getLayoutY() + 50;
        }
        return kingdomCoord;
    }

    public void EndDrawCardAnimation(int playerTurn, int indexHand, Card card, CountDownLatch latch)
    {
        Scene s = AnimationView.getScene();
        //Set the card value
        Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
        handCard.getStyleClass().clear();
        handCard.getStyleClass().addAll( card.getClassString());
        blockAnimation = false;

        ResetAnimationView();

        if(latch != null)
        {
            latch.countDown(); // Release await() in the test thread.
        }
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

        for(int i = 0; i < Game.NB_CARD; i++)
        {
            Scene s = KingdomPlayer1.getScene();
            Button b = (Button) s.lookup(name+i);
            b.getStyleClass().clear();
            DefaultCard d = new DefaultCard();
            b.getStyleClass().addAll(d.getClassString());
        }

    }
}
