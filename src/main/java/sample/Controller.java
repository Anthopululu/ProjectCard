package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import org.json.*;

import javax.swing.text.html.ListView;



public class Controller {

    ButtonPressInformation[] buttonInfo = { null, null };

    @FXML
    GridPane KingdomPlayer1;

    @FXML
    GridPane KingdomPlayer2;

    @FXML
    GridPane HandPlayer1;

    @FXML
    GridPane HandPlayer2;

    @FXML
    ListView deck;

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
            AnimatePutCard();
            buttonInfo[0] = null;
            buttonInfo[1] = null;
        }
    }

    private void AnimatePutCard()
    {
        System.out.println("test");
    }

}
