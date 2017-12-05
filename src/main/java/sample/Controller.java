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


public class Controller {
    // 0 : Fade animation delay, 1 : Path Animation Duration
    int[] delayAnimation = { 500, 500};

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
        PathTransition path = PathAnimationCard(handCoordX, handCoordY, kingdomCoordX, kingdomCoordY);

        FadeTransition fade = FadeAnimationCard();//Start the fade animation
        //Return the class style and reset the card
        ObservableList<String> tmp = resetCard(true, playerTurn, indexHand);

        SequentialTransition seqT = new SequentialTransition (path, fade);
        seqT.play();
        seqT.setOnFinished(e -> putCard(playerTurn, indexKingdom, tmp));//Put the card at the end of fade animation. Delay function included);

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

    public void putCard(int playerTurn, int indexKingdom, ObservableList<String> card)
    {
        Scene s = AnimationView.getScene();
        //Change the image of the button in the index
        Button kingdomCard = (Button) s.lookup("#KingdomPlayer"+playerTurn+"_Card" + (indexKingdom));
        kingdomCard.getStyleClass().clear();
        kingdomCard.getStyleClass().addAll(card);
        blockAnimation = false;
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
        PathTransition path = PathAnimationCard(625, 375, handCoordX, handCoordY);

        FadeTransition fade = FadeAnimationCard();//The fade animation

        SequentialTransition seqT = new SequentialTransition (path, fade);
        seqT.play();
        seqT.setOnFinished(e -> drawCard(playerTurn, indexHand, type));//Put the card at the end of fade animation.
        //Display the animated card
        AnimationView.setVisible(true);
    }

    public void test()
    {
        System.out.println("test");
    }

    public void drawCard(int playerTurn, int indexHand, String cardType)
    {
        Scene s = AnimationView.getScene();

        //Set the card value
        Button handCard = (Button) s.lookup("#HandPlayer"+playerTurn+"_Card" + indexHand);
        handCard.getStyleClass().clear();
        handCard.getStyleClass().addAll( "card",cardType);
        blockAnimation = false;

        ResetAnimationView();
    }

    @FXML
    public void initialize() {
    }
}
