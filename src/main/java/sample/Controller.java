package sample;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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
            AnimatePutCard(1, 0);
            buttonInfo[0] = null;
            buttonInfo[1] = null;
        }
    }

    public void AnimatePutCard(int playerTurn, int index)
    {
        /*GridPane startAnimationGridPane;
        GridPane EndAnimationGridPane;
        if(playerTurn == 1)
        {
            startAnimationGridPane = KingdomPlayer1;
            EndAnimationGridPane = HandPlayer1;
        }
        else
        {
            startAnimationGridPane = KingdomPlayer2;
            EndAnimationGridPane = HandPlayer2;
        }*/

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                (ActionEvent ae) -> {
                    FadeAnimationCard();
                    //putCard(playerTurn,index);
                }));
        timeline.play();


        //Display the animated card
        AnimationView.setVisible(true);

        /*Node n = getNodeByRowColumnIndex(0,0, KingdomPlayer1);
        Bounds boundsInScreen = n.localToScreen(n.getBoundsInLocal());
        Bounds boundsInScene = n.localToScene(n.getBoundsInLocal());
        System.out.println("scene : " + boundsInScene);
        System.out.println("screen : " + boundsInScreen);
        System.out.println(AnimationView.getScene().getX()+n.getLayoutX());
        System.out.println("true X : " + (KingdomPlayer1.getLayoutX()+36) + " true Y : " + (KingdomPlayer1.getLayoutY()+50));
        //Path of rect*/
        Path path = new Path();
        path.getElements().add(new MoveTo(HandPlayer1.getLayoutX()+36,HandPlayer1.getLayoutY()+60));
        path.getElements().add(new LineTo(KingdomPlayer1.getLayoutX()+36,KingdomPlayer1.getLayoutY()+50));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(AnimationView);
        pathTransition.setCycleCount(1);
        pathTransition.play();
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        boolean rowFind = false;
        boolean columnFind = false;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == null)
            {
                if(row == 0)
                {
                    rowFind = true;
                }
            }
            else if(gridPane.getRowIndex(node) == row)
            {
                rowFind = true;
            }
            if(gridPane.getColumnIndex(node) == null)
            {
                if(column == 0)
                {
                    columnFind = true;
                }
            }
            else if(gridPane.getColumnIndex(node) == row)
            {
                columnFind = true;
            }
            if(rowFind && columnFind)
            {
                result = node;
                break;
            }
        }
        return result;
    }

    public void FadeAnimationCard()
    {
        //Fade animation of the rect
        FadeTransition ft = new FadeTransition(Duration.millis(2500), AnimationView);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setDuration(Duration.millis(1500));
        ft.setAutoReverse(false);
        ft.play();
    }

    public void putCard(int playerTurn, int index)
    {
       Scene s = AnimationView.getScene();
       Button kingdomCard = (Button) s.lookup("#KingdomPlayer1_Card0");
       Button handCard = (Button) s.lookup("#HandPlayer1_Card0");
       handCard.getStyleClass().clear();
       handCard.getStyleClass().addAll(kingdomCard.getStyleClass());
       kingdomCard.getStyleClass().clear();
       kingdomCard.getStyleClass().addAll("card", "reverse");

    }


}
