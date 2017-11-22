package sample;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class Controller {
    // 0 : Fade animation delay, 1 : Fade Animation Duration, 2 : Reset Animation View Delay,
    // 3 : Path Animation Duration, 4 : Put Card Delay
    int[] delayAnimation = {1500, 1500, 1500, 2000, 1000};

    boolean blockAnimation = false;

    ButtonPressInformation[] buttonInfo = { null, null };

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
                        AnimatePutCard(u.getPlayer(), buttonInfo[0].getId(), buttonInfo[1].getId());
                    }
                }
                buttonInfo[0] = null;
                buttonInfo[1] = null;
            }
        }
    }

    public boolean IsCorrectCardType(int playerTurn, int indexHand, int indexKingdom)
    {
        boolean result = false;
        Scene s = AnimationView.getScene();

        //Get the image
        Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
        Button kingdomCard = (Button) s.lookup("#KingdomPlayer"+playerTurn+"_Card" + indexKingdom);

        //Check their style class to know if it's a reverse or not
        if(!handCard.getStyleClass().contains("reverse"))
        {
            if(kingdomCard.getStyleClass().contains("reverse"))
            {
                result = true;
            }
        }
        return result;
    }

    public void AnimatePutCard(int playerTurn, int indexHand, int indexKingdom)
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
        PathAnimationCard(handCoordX, handCoordY, kingdomCoordX, kingdomCoordY);

        //Return the class style and reset the card
        ObservableList<String> tmp = resetCard(true, playerTurn, indexHand);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(delayAnimation[0]),
                (ActionEvent ae) -> {
                    FadeAnimationCard();//Start the fade animation
                    putCardDelay(playerTurn, indexKingdom, tmp);//Put the card at the end of fade animation
                }));
        timeline.play();

        //Display the animated card
        AnimationView.setVisible(true);
    }

    public void FadeAnimationCard()
    {
        //Fade animation of the AnimationView
        FadeTransition ft = new FadeTransition(Duration.millis(delayAnimation[1]), AnimationView);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

        ResetAnimationView();
    }

    public void ResetAnimationView()
    {
        //Reset card opacity and visibility to none
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(delayAnimation[2]),
                (ActionEvent ae) -> {
                    AnimationView.setOpacity(1);
                    AnimationView.setVisible(false);
                    //putCard(playerTurn,index);
                }));
        timeline.play();
    }

    public void PathAnimationCard(double coordX, double coordY, double toCoordX, double toCoordY)
    {
        //Create the path of the animation
        Path path = new Path();
        path.getElements().add(new MoveTo(coordX,coordY));
        path.getElements().add(new LineTo(toCoordX,toCoordY));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(delayAnimation[3]));
        pathTransition.setPath(path);
        pathTransition.setNode(AnimationView);
        pathTransition.setCycleCount(1);
        pathTransition.play();
    }

    public void putCardDelay(int playerTurn, int indexKingdom, ObservableList<String> card)
    {
        //Function with launch after the duration.
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(delayAnimation[4]),
                (ActionEvent ae) -> {
                    Scene s = AnimationView.getScene();
                    //Change the image of the button in the index
                    Button kingdomCard = (Button) s.lookup("#KingdomPlayer"+playerTurn+"_Card" + (indexKingdom));
                    kingdomCard.getStyleClass().clear();
                    kingdomCard.getStyleClass().addAll(card);
                    blockAnimation = false;
                }));
        timeline.play();
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
        handCard.getStyleClass().addAll("card", "reverse");

        //Return the class of the card deleted
        return tmp;
    }

    public void AnimateDrawCard(int playerTurn, int indexHand, String type)
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
        PathAnimationCard(625, 375, handCoordX, handCoordY);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(delayAnimation[0]),
                (ActionEvent ae) -> {
                    FadeAnimationCard();//Start the fade animation
                    drawCardDelay(playerTurn, indexHand, type);//Put the card at the end of fade animation. Delay function included
                }));
        timeline.play();

        //Display the animated card
        AnimationView.setVisible(true);
    }

    public void drawCardDelay(int playerTurn, int indexHand, String cardType)
    {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(delayAnimation[4]),
                (ActionEvent ae) -> {
                    Scene s = AnimationView.getScene();

                    //Set the card value
                    Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
                    handCard.getStyleClass().clear();
                    handCard.getStyleClass().addAll( "card",cardType);
                    blockAnimation = false;
                }));
        timeline.play();
    }

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                (ActionEvent ae) -> {
                    AnimateDrawCard(2,9, "elf");
                }));
        timeline.play();
    }
}
