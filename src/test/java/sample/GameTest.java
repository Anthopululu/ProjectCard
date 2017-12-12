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

    @Given("^My game is created$")
    public void CreateGame()
    {
        this.game = new Game();
        assertEquals(game.playerTurn > 0, true);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK);
        assertEquals(game.handPlayer.get(0).size(), Game.NB_CARD);
        assertEquals(game.handPlayer.get(1).size(), Game.NB_CARD);
        assertEquals(game.kingdomPlayer.get(0).size(), Game.NB_CARD);
        assertEquals(game.kingdomPlayer.get(1).size(), Game.NB_CARD);
    }

    @Given("^My game is started$")
    public void StartGame() throws Exception {
        this.game = new Game();
        game.DrawMultipleCardWithoutInterface(1,5);
        game.DrawMultipleCardWithoutInterface(2,5);
    }

    @Then("^Player 1 draw 5 card$")
    public void drawCardPlayer1() throws Exception {
        game.DrawMultipleCardWithoutInterface(1,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-5);
    }

    @And("^Player 2 draw 5 card$")
    public void drawCardPlayer2() throws Exception {
        game.DrawMultipleCardWithoutInterface(2,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-10);
    }

    @Then("^Random play turn player$")
    public void playTurn1()
    {
        int nb_card_deck = game.deck.size();
        int playerTurn = game.playerTurn;

        //List of empty index before
        int emptyCardKingdom = ListCard.EmptyIndex(game.kingdomPlayer.get(playerTurn-1).getKingdom()).size();
        int emptyCardHand= ListCard.EmptyIndex(game.handPlayer.get(playerTurn-1).getListOfCards()).size();
        //Play turn
        game.PlayTurnWithoutInterface();

        //List of empty index now
        int emptyCardKingdom2 = ListCard.EmptyIndex(game.kingdomPlayer.get(playerTurn-1).getKingdom()).size();
        int emptyCardHand2 = ListCard.EmptyIndex(game.handPlayer.get(playerTurn-1).getListOfCards()).size();

        //Test if true
        assertEquals(nb_card_deck-1, game.deck.size());
        assertEquals(emptyCardHand, emptyCardHand2-1);
        assertEquals(emptyCardKingdom, emptyCardKingdom2+1);
    }
}