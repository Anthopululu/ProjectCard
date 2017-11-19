package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import javax.swing.text.html.ListView;

public class Controller {
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

    public void test(int id)
    {
        System.out.println(id);
    }


}
