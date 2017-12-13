package sample;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    Game game;


    /*Scenario 1 */
    @Given("^My game is created$")
    public void CreateGame()
    {
        this.game = new Game();
        assertEquals(game.playerTurn > 0, true);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK);
        assertEquals(game.playerList.get(0).getHand().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(1).getHand().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(0).getKingdom().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(1).getKingdom().size(), Game.NB_CARD);
    }

    @Then("^Player 1 draw 5 card$")
    public void drawCardPlayer1() throws Exception {
        game.DrawMultipleCard(1,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-5);
    }

    @And("^Player 2 draw 5 card$")
    public void drawCardPlayer2() throws Exception {
        game.DrawMultipleCard(2,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-10);
    }

    /*Scenario 2 */
    @Given("^My game is started$")
    public void StartGame() throws Exception {
        this.game = new Game();
        game.DrawMultipleCard(1,5);
        game.DrawMultipleCard(2,5);
    }

    @Then("^Random play turn")
    public void playTurn()
    {
        int nb_card_deck = game.deck.size();
        int playerTurn = game.playerTurn;

        //List of empty index before
        int emptyCardKingdom = ListCard.EmptyIndex(game.playerList.get(playerTurn-1).getKingdom().getKingdom()).size();
        int emptyCardHand= ListCard.EmptyIndex(game.playerList.get(playerTurn-1).getHand().getListOfCards()).size();
        //Play turn
        game.PlayTurnRandom();

        //List of empty index now
        int emptyCardKingdom2 = ListCard.EmptyIndex(game.playerList.get(playerTurn-1).getKingdom().getKingdom()).size();
        int emptyCardHand2 = ListCard.EmptyIndex(game.playerList.get(playerTurn-1).getHand().getListOfCards()).size();

        //Test if true
        assertEquals(nb_card_deck-1, game.deck.size());
        assertEquals(emptyCardHand, emptyCardHand2-1);
        assertEquals(emptyCardKingdom, emptyCardKingdom2+1);
    }

    /*Scenario 3 */
    @Given("^My game will reset kingdom$")
    public void MidGame() throws Exception {
        this.game = new Game();
        game.DrawMultipleCard(1,5);
        game.DrawMultipleCard(2,5);
        //Just before a reset kingdom
        game.PlayMultipleTurnRandom((Game.NB_CARD*2)-1);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK - (Game.NB_CARD*2) - 9);
    }

    @Then("^Play a turn and reset$")
    public void TurnToResetGame()
    {
        int player = game.playerTurn;
        if(player == 1)//Change to opponent
        {
            player = 2;
        }
        else
        {
            player = 1;
        }
        player--;
        //Number of empty card in kingdom for the first player
        int nbEmptyCardKingdom = ListCard.EmptyIndex(game.playerList.get(player).getCardKingdom()).size();
        assertEquals(nbEmptyCardKingdom, 0);//Should be empty of default card
        game.PlayMultipleTurnRandom(1);
        //Number of empty card in kingdom for the first player, after the reset
        int nbEmptyCardKingdom2 = ListCard.EmptyIndex(game.playerList.get(player).getCardKingdom()).size();
        assertEquals(nbEmptyCardKingdom2, Game.NB_CARD);//Should be full
    }
}